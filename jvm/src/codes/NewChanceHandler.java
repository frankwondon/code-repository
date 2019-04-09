package com.duia.unit.chance.allot;

import com.duia.common.annotation.StopWatchAtn;
import com.duia.common.constant.Constant;
import com.duia.common.util.YunBaMessageFactory;
import com.duia.constant.ChanceEnum;
import com.duia.constant.ErrorEnum;
import com.duia.dto.ChanceBean;
import com.duia.dto.ChanceUserBean;
import com.duia.dto.CrmAllotFlow;
import com.duia.mongodb.service.InputChanceRecordService;
import com.duia.service.ChanceRedisService;
import com.duia.unit.redis.model.UserLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;

@Component
public class NewChanceHandler extends ChanceHandler {
    @Resource
    private InputChanceRecordService inputChanceRecordService;

    @Resource
    private ChanceRedisService redisService;

    @Override
    public void ChanceHandle(ChanceBean chanceBean) throws Exception {
        newChanceAllot(chanceBean);
    }

    // 分配成功
    private final int ALLOT_SUCCESS = 1;
    // 分配失败
    private final int ALLOT_FAIL = 2;
    // 待循环分配
    private final int ALLOT_REALLOT = 3;
    // 类型下无人员
    private final int ALLOT_NO_SALE = 4;

    /**
     * 按新机会分配
     *
     * @param
     * @throws
     * @since 2017/10/27 15:05 wangdong
     */
    @StopWatchAtn("新机会分配")
    private void newChanceAllot(ChanceBean chanceBean) throws Exception {
        Integer typeId = chanceBean.getTypeId();
        Integer[] integers = chanceRedisService.currentAllotUser(typeId);
        Integer uid = integers[1];
        Integer size = integers[2];
        Integer start = integers[0];
        logger.debug("===>方法：{} uid：{}", "newChanceAllot", uid); // test
        logger.info("新机会分配，用户id:{},机会id:{},mongodbId:{}", uid, chanceBean.getId(), chanceBean.getMongodbId());

        AllotResult allotResult;
        do {

            try {
                allotResult = execAllot(chanceBean, uid, size, start);
                // 分配后状态
                int status = allotResult.getAllotStatus();
                if (status == ALLOT_SUCCESS || status == ALLOT_NO_SALE) {
                    logger.info("分配成功或类型下无销售：状态 " + status + "，机会ID:" + chanceBean.getId() + "，用户:" + uid);
                    break;
                } else if (status == ALLOT_FAIL) {
                    chanceMainService.repeatAllot(chanceBean);
                    logger.info("分配失败，机会ID:" + chanceBean.getId() + "，用户:" + uid);
                    break;
                } else if (status == ALLOT_REALLOT) {
                    logger.info("重新分配，机会ID:" + chanceBean.getId() + "，上一个人:" + uid + "，当前分配人:" + allotResult.getUid());
                    uid = allotResult.getUid();
                    size = allotResult.getSize();
                    start = allotResult.getStart();
                } else {
                    logger.info("分配执行过程中逻辑错误，机会ID:" + chanceBean.getId() + "，用户:" + uid);
                    break;
                }
            } catch (Exception e) {
                logger.info("分配异常，机会ID:" + chanceBean.getId() + "，用户:" + uid);
                break;
            }

        } while (true);

    }


    public AllotResult execAllot(ChanceBean chanceBean, Integer uid, Integer size, Integer start) throws Exception {
        AllotResult allotResult = new AllotResult();
        Integer typeId = chanceBean.getTypeId();
        if (uid == -2) {
            logger.error("新机会分配时,TypeID:" + typeId + "类型列表中未取到用户");
            inputChanceRecordService.convertAllotFlow(chanceBean.getMongodbId(),
                    new CrmAllotFlow(ErrorEnum.ChanceRecord.C_6005, "typeId:" + typeId));
            allotResult.setAllotStatus(ALLOT_NO_SALE);
            return allotResult;
        } else if (uid == -3) {
            inputChanceRecordService.convertAllotFlow(chanceBean.getMongodbId(),
                    new CrmAllotFlow(ErrorEnum.ChanceRecord.C_6006, "typeId:" + typeId));
            allotResult.setAllotStatus(ALLOT_FAIL);
            return allotResult;
        } else {
            ChanceAllotFlow flow = new ChanceAllotFlow();
            ChanceUserBean user = chanceRedisService.oldAllotUser(uid);
            if (user.getExecuteAllot() == Constant.EXECUTE_STOP_YES.intValue()) {
                // 获取当前停止分配的天数
                int days = redisService.checkUserExcuteAllot(user.getType());
                logger.error("chance bean mongo id : {} and userId is {}", chanceBean.getMongodbId(), user.getUserId());

                //如果当前名单中只有一个人
                chanceRedisService.oneSaleCanBeUsed(typeId, chanceBean, user, days);
                allotResult.setAllotStatus(ALLOT_FAIL);
                return allotResult;
            }

            if (user.getAllot()) {
                this.oneSaleLogic(chanceBean, uid, size, start, typeId, allotResult);
                return allotResult;
            }

            UserLock userLock = chanceRedisService.getUserLock(uid);
            if (userLock.lock()) {
                logger.debug("===>方法：{}，加锁UserLock成功:{}，机会id：{}", "execAllot", uid, chanceBean.getId()); // test
                logger.info("1-新机会分配,用户加锁后进行分配，用户id：{},机会id:{},mongodbId:{}", uid, chanceBean.getId(), chanceBean.getMongodbId());
                user = chanceRedisService.oldAllotUser(uid);
                try {
                    boolean isok = flow.peoSwitchAndExistes(user).
                            peoMaxArrive(user).
                            peoAllreadyAllot(user).
                            isok;
                    if (isok) {
                        //把机会分配给销售
                        logger.info("2-新机会分配,用户加锁后进行分配，用户id：{},机会id:{},mongodbId:{}", uid, chanceBean.getId(), chanceBean.getMongodbId());
                        allotChance(chanceBean, user, typeId);
                        logger.debug("===>方法：{}，分配成功:{}，机会id：{}", "execAllot", uid, chanceBean.getId()); // test
                        allotResult.setAllotStatus(ALLOT_SUCCESS);
                        return allotResult;
                    }
                } catch (Exception e) {
                    allotResult.setAllotStatus(ALLOT_FAIL);
                    return allotResult;
                } finally {
                    userLock.unLock();
                    logger.debug("===>方法：{}，解锁UserLock:{}，机会id：{}", "execAllot", uid, chanceBean.getId()); // test
                }
                // 如果isok为false调用方面进入循环调用，调用前现将lock进行解锁操作
                logger.debug("===>方法：{}，当前用户不满足分配条件:{}，机会id：{}", "execAllot", uid, chanceBean.getId()); // test
                //如果当前名单中只有一个人,并且没分出去则重入rabbit
                this.oneSaleLogic(chanceBean, uid, size, start, typeId, allotResult);
                return allotResult;
            } else {
                logger.debug("===>方法：{}，加锁UserLock失败:{}，机会id：{}", "execAllot", uid, chanceBean.getId()); // test
                userLock.error("分配新机会", "执行分配", uid);
                inputChanceRecordService.convertAllotFlow(chanceBean.getMongodbId(),
                        new CrmAllotFlow(ErrorEnum.ChanceRecord.C_6007, "typeId:" + typeId + ";userId:" + uid));
                allotResult.setAllotStatus(ALLOT_FAIL);
                return allotResult;
            }

        }
    }

    private void oneSaleLogic(ChanceBean chanceBean, Integer uid, Integer size, Integer start, Integer typeId, AllotResult allotResult) throws Exception {
        if (size == 1) {
            chanceRedisService.checkNotAllot(typeId);
            logger.info("机会分配时,当前只有一个销售在线，机会ID：{}，size ：{}", chanceBean.getId(), size);
            inputChanceRecordService.convertAllotFlow(chanceBean.getMongodbId(),
                    new CrmAllotFlow(ErrorEnum.ChanceRecord.C_6009, "typeId:" + typeId + ";userId:" + uid));
            allotResult.setAllotStatus(ALLOT_FAIL);
        } else {
            Integer[] integers = chanceRedisService.currentAllotUser(typeId);
            //可能为null
            Integer rstart = integers[0];
            Integer theuid = integers[1];
            Integer newsize = integers[2];
            //从当前名单中获取用户时 转了一圈都没有拿到用户又回到当前用户，表示没有打开开关的人,则重入rabbit
            if (start.equals(rstart)) {
                chanceRedisService.checkNotAllot(typeId);
                logger.info("机会分配时,轮转一圈无可分配对象，机会ID：{},start：{},rstart: {}", chanceBean.getId(), start, rstart);
                inputChanceRecordService.convertAllotFlow(chanceBean.getMongodbId(),
                        new CrmAllotFlow(ErrorEnum.ChanceRecord.C_6010, "typeId:" + typeId + ";userId:" + uid));
                allotResult.setAllotStatus(ALLOT_FAIL);
            } else {
                logger.info("准备分配给下一个用户:{} --> {}", uid, theuid);
                allotResult.setAllotStatus(ALLOT_REALLOT);
                allotResult.setUid(theuid);
                allotResult.setSize(newsize);
                allotResult.setStart(start);
            }
        }
    }


    private void allotChance(ChanceBean chanceBean, ChanceUserBean user, Integer type) throws Exception {
        //更新db分配时间
        Integer upCount = chanceMapper.updateAllotTime(chanceBean.getId());
        if (upCount <= 0) {
            logger.error("机会id:{},分配给用户id:{}时,可能已经被分配或查看", chanceBean.getId(), user.getUserId());
            inputChanceRecordService.convertAllotFlow(chanceBean.getMongodbId(),
                    new CrmAllotFlow(ErrorEnum.ChanceRecord.C_6008, "用户id:" + user.getUserId() + ";用户名称:" + user.getUserName()));
            return;
        }
        inputChanceRecordService.convertAllotFlow(chanceBean.getMongodbId(),
                new CrmAllotFlow(ErrorEnum.ChanceRecord.C_6002, "用户id:" + user.getUserId() + ";用户名称:" + user.getUserName()));
        //更新当前类型属性分配过
        chanceRedisService.updateTypeIsAllot(type);
        //分配机会
        chanceBean.setAllotTime(new Date());
        chanceRedisService.putChanceToPeople(chanceBean, user.getUserId());
        //推送到客户端新机会给销售
        imService.sendToAlias(user.getUserId().toString(), YunBaMessageFactory.newChance());
        //放入延时队列处理
        String uuid = chanceCountScedule.putDelayChance(user.getUserId(), chanceBean);
        //分配给销售后记录mongo
        trunRoundService.saveTrunRoundRecord(user, ChanceEnum.TurnRoundLog.CHANCE_ALLOT, null, uuid);
        logService.updateLog(chanceBean.getId(), MessageFormat.format("机会分配给用户,ID:{0,number,#}", user.getUserId()));
        logger.debug("机会分配成功，用户,ID:{}", user.getUserId(), chanceBean.getId());
        redisSystemService.allotIncrement();
    }


    private class ChanceAllotFlow {
        private boolean isok = false;

        /**
         * 存在用户并且开关打开
         */
        private ChanceAllotFlow peoSwitchAndExistes(ChanceUserBean user) {
            if (user != null && Constant.SWITCH_OPEN.equals(user.getSwitchStatus())) {//个人接单状态开关
                isok = true;
                return this;
            } else {
                trunRoundService.saveTrunRoundRecord(user, ChanceEnum.TurnRoundLog.CHANCE_UNALLOT, ChanceEnum.UnAllotReason.SWITCH_CLOSE, null);
            }
            isok = false;
            logger.debug("===>方法：{}，当前用户不存在或者未打开开关:{} ", "peoSwitchAndExistes", user.getUserId()); // test
            return this;
        }


        /**
         * 达到最大上限
         */
        private ChanceAllotFlow peoMaxArrive(ChanceUserBean user) {
            if (isok) {
                if (user.getMax() > user.getAlready()) {//上限未满
                    isok = true;
                    return this;
                } else {
                    trunRoundService.saveTrunRoundRecord(user, ChanceEnum.TurnRoundLog.CHANCE_UNALLOT, ChanceEnum.UnAllotReason.SWITCH_CLOSE, null);
                }
            }
            isok = false;
            logger.debug("===>方法：{}，当前用户分配名额已满:{} ", "peoMaxArrive", user.getUserId()); // test
            return this;
        }

        /**
         * 用户已经被分配
         */
        private ChanceAllotFlow peoAllreadyAllot(ChanceUserBean user) {
            if (isok) {
                if (!user.getAllot()) {//未分配
                    isok = true;
                    return this;
                } else {
                    trunRoundService.saveTrunRoundRecord(user, ChanceEnum.TurnRoundLog.CHANCE_UNALLOT, ChanceEnum.UnAllotReason.ON_ALLOT, null);
                }
            }
            isok = false;
            logger.debug("===>方法：{}，当前用户含有待查看信息:{} ", "peoAllreadyAllot", user.getUserId()); // test
            return this;
        }

    }
}
