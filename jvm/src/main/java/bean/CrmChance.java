package bean;

import java.time.LocalDate;
import java.util.Date;

public class CrmChance {
    private Integer id;

    /**
     * 类型id
     */
    private Integer typeId;

    /**
     * 类型名[冗余字段]
     */
    private String typeName;

    /**
     * 来源id
     */
    private Integer sourceId;

    /**
     * 来源名称[冗余字段]
     */
    private String sourceName;

    /**
     * qq号码
     */
    private Long qqNum;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 手机号码
     */
    private Long mobile;

    /**
     * qq验证答案
     */
    private String verifyAnswer;

    /**
     * 验证答案状态是否更新过
     */
    private Integer answerStatus;

    /**
     * 学员需求
     */
    private String studentDemand;

    /**
     * 录入人id
     */
    private Integer writeUserId;

    /**
     * 录入人邮箱
     */
    private String writeUserEmail;

    /**
     * 录入时间
     */
    private Date writeTime;

    /**
     * 是否重复[0：没有；1：重复]
     */
    private Integer isRepeat;

    /**
     * 重复的机会id
     */
    private Integer repeatChanceid;

    /**
     * 上次分配给的销售
     */
    private Integer lastUserId;

    /**
     * 验证状态 [0：无误 1：号码有误  2：验证失败]
     */
    private Integer isWrong;

    /**
     * mongodb的id
     */
    private String mongodbId;

    /**
     * 优惠码
     */
    private String disCode;

    /**
     * 录入人姓名
     */
    private String writeUserName;

    /**
     * 分配时间
     */
    private Date allotTime;

    /**
     * 机会的状态:0新建 1被分配 2被查看
     */
    private Integer status;

    /**
     * 查看时间
     */
    private Date viewTime;


    private Integer occupyStatus;

    private LocalDate expireDate;

    private Integer occupyRemark;

    private Date bindDate;

    private Long allotUserDeptId;

    private Long allotUserDepGrpId;

    private String siteId;

    public Integer getOccupyStatus() {
        if (this.occupyStatus == null) {
            this.occupyStatus = 3;
        }
        return this.occupyStatus;
    }

    public void setOccupyStatus(Integer occupyStatus) {
        if (occupyStatus == null) {
            this.occupyStatus = 3;
            return;
        }
        this.occupyStatus = occupyStatus;
    }
    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getOccupyRemark() {
        return occupyRemark;
    }

    public void setOccupyRemark(Integer occupyRemark) {
        this.occupyRemark = occupyRemark;
    }

    public Date getBindDate() {
        return bindDate;
    }

    public void setBindDate(Date bindDate) {
        this.bindDate = bindDate;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取类型id
     *
     * @return type_id - 类型id
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 设置类型id
     *
     * @param typeId 类型id
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取类型名[冗余字段]
     *
     * @return type_name - 类型名[冗余字段]
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置类型名[冗余字段]
     *
     * @param typeName 类型名[冗余字段]
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取来源id
     *
     * @return source_id - 来源id
     */
    public Integer getSourceId() {
        return sourceId;
    }

    /**
     * 设置来源id
     *
     * @param sourceId 来源id
     */
    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 获取来源名称[冗余字段]
     *
     * @return source_name - 来源名称[冗余字段]
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * 设置来源名称[冗余字段]
     *
     * @param sourceName 来源名称[冗余字段]
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * 获取qq号码
     *
     * @return qq_num - qq号码
     */
    public Long getQqNum() {
        return qqNum;
    }

    /**
     * 设置qq号码
     *
     * @param qqNum qq号码
     */
    public void setQqNum(Long qqNum) {
        this.qqNum = qqNum;
    }

    /**
     * 获取微信号
     *
     * @return wechat - 微信号
     */
    public String getWechat() {
        return wechat;
    }

    /**
     * 设置微信号
     *
     * @param wechat 微信号
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public Long getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取qq验证答案
     *
     * @return verify_answer - qq验证答案
     */
    public String getVerifyAnswer() {
        return verifyAnswer;
    }

    /**
     * 设置qq验证答案
     *
     * @param verifyAnswer qq验证答案
     */
    public void setVerifyAnswer(String verifyAnswer) {
        this.verifyAnswer = verifyAnswer;
    }

    /**
     * 获取学员需求
     *
     * @return student_demand - 学员需求
     */
    public String getStudentDemand() {
        return studentDemand;
    }

    /**
     * 设置学员需求
     *
     * @param studentDemand 学员需求
     */
    public void setStudentDemand(String studentDemand) {
        this.studentDemand = studentDemand;
    }

    /**
     * 获取录入人id
     *
     * @return write_user_id - 录入人id
     */
    public Integer getWriteUserId() {
        return writeUserId;
    }

    /**
     * 设置录入人id
     *
     * @param writeUserId 录入人id
     */
    public void setWriteUserId(Integer writeUserId) {
        this.writeUserId = writeUserId;
    }

    /**
     * 获取录入人邮箱
     *
     * @return write_user_email - 录入人邮箱
     */
    public String getWriteUserEmail() {
        return writeUserEmail;
    }

    /**
     * 设置录入人邮箱
     *
     * @param writeUserEmail 录入人邮箱
     */
    public void setWriteUserEmail(String writeUserEmail) {
        this.writeUserEmail = writeUserEmail;
    }

    /**
     * 获取录入时间
     *
     * @return write_time - 录入时间
     */
    public Date getWriteTime() {
        return writeTime;
    }

    /**
     * 设置录入时间
     *
     * @param writeTime 录入时间
     */
    public void setWriteTime(Date writeTime) {
        this.writeTime = writeTime;
    }

    /**
     * 获取是否重复[0：没有；1：重复]
     *
     * @return is_repeat - 是否重复[0：没有；1：重复]
     */
    public Integer getIsRepeat() {
        return isRepeat;
    }

    /**
     * 设置是否重复[0：没有；1：重复]
     *
     * @param isRepeat 是否重复[0：没有；1：重复]
     */
    public void setIsRepeat(Integer isRepeat) {
        this.isRepeat = isRepeat;
    }

    /**
     * 获取重复的机会id
     *
     * @return repeat_chanceid - 重复的机会id
     */
    public Integer getRepeatChanceid() {
        return repeatChanceid;
    }

    /**
     * 设置重复的机会id
     *
     * @param repeatChanceid 重复的机会id
     */
    public void setRepeatChanceid(Integer repeatChanceid) {
        this.repeatChanceid = repeatChanceid;
    }

    /**
     * 获取上次分配给的销售
     *
     * @return last_user_id - 上次分配给的销售
     */
    public Integer getLastUserId() {
        return lastUserId;
    }

    /**
     * 设置上次分配给的销售
     *
     * @param lastUserId 上次分配给的销售
     */
    public void setLastUserId(Integer lastUserId) {
        this.lastUserId = lastUserId;
    }

    /**
     * 获取验证状态 [0：无误 1：号码有误  2：验证失败]
     *
     * @return is_wrong - 验证状态 [0：无误 1：号码有误  2：验证失败]
     */
    public Integer getIsWrong() {
        return isWrong;
    }

    /**
     * 设置验证状态 [0：无误 1：号码有误  2：验证失败]
     *
     * @param isWrong 验证状态 [0：无误 1：号码有误  2：验证失败]
     */
    public void setIsWrong(Integer isWrong) {
        this.isWrong = isWrong;
    }

    /**
     * 获取mongodb的id
     *
     * @return mongodb_id - mongodb的id
     */
    public String getMongodbId() {
        return mongodbId;
    }

    /**
     * 设置mongodb的id
     *
     * @param mongodbId mongodb的id
     */
    public void setMongodbId(String mongodbId) {
        this.mongodbId = mongodbId;
    }

    /**
     * 获取优惠码
     *
     * @return dis_code - 优惠码
     */
    public String getDisCode() {
        return disCode;
    }

    /**
     * 设置优惠码
     *
     * @param disCode 优惠码
     */
    public void setDisCode(String disCode) {
        this.disCode = disCode;
    }

    /**
     * 获取录入人姓名
     *
     * @return write_user_name - 录入人姓名
     */
    public String getWriteUserName() {
        return writeUserName;
    }

    /**
     * 设置录入人姓名
     *
     * @param writeUserName 录入人姓名
     */
    public void setWriteUserName(String writeUserName) {
        this.writeUserName = writeUserName;
    }

    public Integer getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(Integer answerStatus) {
        this.answerStatus = answerStatus;
    }

    /**
     * 获取匹配时间
     *
     * @return allot_time - 匹配时间
     */
    public Date getAllotTime() {
        return allotTime;
    }

    /**
     * 设置匹配时间
     *
     * @param allotTime 匹配时间
     */
    public void setAllotTime(Date allotTime) {
        this.allotTime = allotTime;
    }

    public Date getViewTime() {
        return viewTime;
    }

    public void setViewTime(Date viewTime) {
        this.viewTime = viewTime;
    }

    public Long getAllotUserDeptId() {
        return allotUserDeptId;
    }

    public void setAllotUserDeptId(Long allotUserDeptId) {
        this.allotUserDeptId = allotUserDeptId;
    }

    public Long getAllotUserDepGrpId() {
        return allotUserDepGrpId;
    }

    public void setAllotUserDepGrpId(Long allotUserDepGrpId) {
        this.allotUserDepGrpId = allotUserDepGrpId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}