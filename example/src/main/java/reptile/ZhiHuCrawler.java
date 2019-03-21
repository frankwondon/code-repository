package reptile;

import cn.edu.hfut.dmic.webcollector.example.DemoCookieCrawler;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.Request;

import java.util.concurrent.atomic.AtomicInteger;

public class ZhiHuCrawler extends BreadthCrawler {
    AtomicInteger pageNum=new AtomicInteger(1);
    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    public ZhiHuCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        addSeed("https://www.zhihu.com/api/v3/feed/topstory/recommend?session_token=a074b25b71b0dadd39ec79da41ace2b0&desktop=true&page_number="+pageNum.get()+"&limit=10&action=down");
        setRequester(new MyRequester());
    }

    // 自定义的请求插件
    // 可以自定义User-Agent和Cookie
    public static class MyRequester extends OkHttpRequester {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36";
        String cookie = "_xsrf=Erbtb6brYYaQvm9tGCzeqPkMayM3B5D3; _zap=d00fd9f0-56b6-4bc8-8915-a842cd128526; d_c0=\"ANBkzsC_EQ-PTgD9_6SZPuqilDB4wo8yxPA=|1551669155\"; capsion_ticket=\"2|1:0|10:1551669163|14:capsion_ticket|44:NmE1OTJkNTUwZmI0NDM4OGJjMDc5NmFkNzhiMGQyNmQ=|49f1f7c767cb8f26d74f315e160ecb056a8f76dec96cea0d0f4d7739f1f56f18\"; z_c0=\"2|1:0|10:1551669169|4:z_c0|92:Mi4xaUtUMkJBQUFBQUFBMEdUT3dMOFJEeVlBQUFCZ0FsVk5zZVZwWFFDSm4xX1ZEUWFQbktJQ3Y5U0dZOVJ2NHlBYlVn|6b098e7c6e9b9019aeb5fe7a5baedbd4a1902af15ec7cf402f6ecfb903320086\"; tst=r; q_c1=8a7466b4d5094e65997749327eea9af2|1551669170000|1551669170000; __utmv=51854390.100--|2=registration_date=20170515=1^3=entry_date=20170515=1; __utma=51854390.700249145.1551938926.1552972193.1552988448.3; __utmz=51854390.1552988448.3.2.utmcsr=zhihu.com|utmccn=(referral)|utmcmd=referral|utmcct=/question/66724505; tgw_l7_route=a37704a413efa26cf3f23813004f1a3b";
        String x_ab_param="se_page_limit_20=1;se_webrs=0;se_new_market_search=on;top_bill=0;top_new_user_rec=0;qa_test=0;se_likebutton=1;top_ebook=0;top_root=0;zr_km_material_buy=2;li_filter_ttl=2;qa_video_answer_list=1;se_config=1;se_consulting_switch=off;zr_search_material=1;se_sensitive=0;se_webtimebox=1;top_nucc=0;pf_newguide_vertical=0;se_wannasearch=0;top_billupdate1=3;top_vipconsume=1;top_zh_newuser=7;tp_m_intro_re_topic=1;qa_web_answerlist_ad=0;se_major_onebox=major;se_websearch=3;top_quality=0;tp_sft=a;zr_video_rec=zr_video_rec:base;se_consulting_price=n;se_minor_onebox=d;se_site_onebox=0;se_spb309=0;top_source=1;tp_related_topics=e;zr_rel_search=base;se_search_feed=N;tp_header_style=1;zr_article_rec_rank=truncate;pf_foltopic_usernum=0;top_recall_exp_v1=7;top_vipoffice=1;top_zh_tailuser=2;li_es_new=new;top_user_cluster=1;se_roundtable=1;top_new_feed=5;tp_qa_metacard=1;se_auto_syn=0;top_gr_ab=4;top_test_4_liguangyi=1;tp_sticky_android=0;se_domain_onebox=0;se_ios_spb309=1;top_ntr=1;top_recall_deep_user=1;se_entity=on;se_ltr_0318=0;se_zu_onebox=4;top_video_rerank=1;li_se_highlight=1;se_km_ad_locate=1;se_premium_member=0;ug_zero_follow_0=0;li_gbdt=2;se_lottery=0;top_tabvideo=1;top_v_album=1;gw_guide=0;soc_bigone=0;tp_related_tps_movie=b;se_backsearch=0;se_p_slideshow=0;li_ts_sample=old;ls_fmp4=0;se_zu_recommend=0;top_hotcommerce=1;ug_follow_answerer=0;se_terminate=1;se_billboardsearch=0;top_rank=6;top_sess_diversity=-1;tsp_lastread=0;zr_ans_rec=gbrank;pf_feed=1;top_collect=1;top_creator_level=1;top_recall_exp_v2=6;top_wonderful=1;zr_infinity=zr_infinity_open;se_ad_index=10;ug_zero_follow=0;li_album_liutongab=0;se_preset_tech=0;top_hkc_test=1;top_reason=1;pf_creator_card=1;top_re_sametag=0;top_round_table=0;top_ydyq=A;tp_discussion_feed_type_android=2;zr_art_rec=reduce_word_num;se_topicseed=1;soc_special=0;top_universalebook=1;zr_feed_cf=1;se_click3=3;top_brand=1;top_native_answer=1;se_decoupling=0;se_expired_ob=0;se_qanchor=1;se_webmajorob=0;li_lt_tp_score=1;top_core_session=-1;ug_follow_topic_1=0;qa_answerlist_ad=0;se_ios_spb309bugfix=0;se_threshold=4;tp_qa_metacard_top=top";
        String x_api_version="3.0.53";
        String x_requested_with="fetch";
        // 每次发送请求前都会执行这个方法来构建请求
        @Override
        public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
            // 这里使用的是OkHttp中的Request.Builder
            // 可以参考OkHttp的文档来修改请求头
            return super.createRequestBuilder(crawlDatum)
                    .addHeader("User-Agent", userAgent)
                    .addHeader("Cookie", cookie)
                    .addHeader("x-ab-param",x_ab_param)
                    .addHeader("x-api-version",x_api_version)
                    .addHeader("x-requested-with",x_requested_with);
        }

    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        System.out.println(page.jsonObject());
        next.add("https://www.zhihu.com/api/v3/feed/topstory/recommend?session_token=a074b25b71b0dadd39ec79da41ace2b0&desktop=true&page_number="+pageNum.incrementAndGet()+"&limit=10&action=down");
    }

    public void pars(JsonObject jsonObject){
        JsonArray data = jsonObject.getAsJsonArray("data");

    }

    public static void main(String[] args) throws Exception {
        ZhiHuCrawler crawler = new ZhiHuCrawler("crawl",true);
        crawler.start(100);
    }
}
