package reptile;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.Request;

import java.util.concurrent.atomic.AtomicInteger;

public class CSDNCrawler extends RamCrawler {

    public CSDNCrawler() {
        addSeed("https://blog.csdn.net/api/articles?type=more&category=home&shown_offset=0");
        setRequester(new MyRequester());
    }

    // 自定义的请求插件
    // 可以自定义User-Agent和Cookie
    public static class MyRequester extends OkHttpRequester {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36";
        String cookie = "uuid_tt_dd=10_17809711400-1551262906161-810270; dc_session_id=10_1551262906161.188091; __yadk_uid=mC1nDJr8SoRpDGQZoEWgIXbnADQOSkq1; UM_distinctid=16932e6edb5386-09795c8b0f87ee-1333062-1fa400-16932e6edb6568; _ga=GA1.2.288327294.1551771993; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_17809711400-1551262906161-810270; hasSub=true; SESSION=92ed4049-4882-45c9-9ab7-2996be949bc3; TY_SESSION_ID=168d4f53-6ee6-4a56-9104-ae84a6c01c3d; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1554295663,1554297991,1554298424,1554356935; ADHOC_MEMBERSHIP_CLIENT_ID1.0=070cccea-5db2-b3a0-d05a-ebfc2dd60f78; dc_tos=ppf9j0; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1554356989; c-login-auto=18";
        String x_requested_with="XMLHttpRequest";
        String x_tingyun_id="wl4EtIR_7Is;r=357124303";
        // 每次发送请求前都会执行这个方法来构建请求
        @Override
        public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
            // 这里使用的是OkHttp中的Request.Builder
            // 可以参考OkHttp的文档来修改请求头
            return super.createRequestBuilder(crawlDatum)
                    .addHeader("User-Agent", userAgent)
                    .addHeader("Cookie", cookie)
                    .addHeader("x_tingyun_id",x_tingyun_id)
                    .addHeader("x-requested-with",x_requested_with);
        }

    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        pars(page.jsonObject());
        next.add("https://blog.csdn.net/api/articles?type=more&category=home&shown_offset=0");
    }

    public void pars(JsonObject jsonObject){
        JsonArray data = jsonObject.getAsJsonArray("articles");
        data.forEach(jsonElement -> {
            JsonObject obj = jsonElement.getAsJsonObject();
            System.out.println(obj.get("title").getAsString());
        });
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i <100 ; i++) {
            CSDNCrawler crawler = new CSDNCrawler();
            crawler.start(10);
        }
    }
}
