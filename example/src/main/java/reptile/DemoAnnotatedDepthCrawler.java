package reptile;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

import java.util.Iterator;

public class DemoAnnotatedDepthCrawler extends BreadthCrawler {
    public DemoAnnotatedDepthCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        this.addSeed("https://archive.cloudera.com/cdh6/6.1.1/redhat7/yum/RPMS/noarch/");
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        page.select("table");
        Links links = page.links();
        RegexRule regexRule=new RegexRule();
        regexRule.addNegative("/*.rpm&/");
        links.filterByRegex(regexRule);
        Iterator<String> iterator = links.iterator();
        iterator.forEachRemaining(str -> {
            System.out.println(str);
        });
    }

    @AfterParse
    public void afterParse(Page page, CrawlDatums next) {

    }


    public static void main(String[] args) throws Exception {
        DemoAnnotatedDepthCrawler crawler = new DemoAnnotatedDepthCrawler("crawler",true);
        crawler.start(3);
    }
}
