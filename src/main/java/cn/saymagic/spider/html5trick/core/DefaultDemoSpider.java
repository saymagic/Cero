package cn.saymagic.spider.html5trick.core;

import cn.saymagic.spider.html5trick.entity.Demo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by saymagic on 15/10/13.
 */
public class DefaultDemoSpider implements IDemoSpider{

    @Override
    public Demo doSearch(String url) {
        Demo demo = new Demo();
        demo.setDemoPageUrl(url);
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        demo.setDemoName(document.select("h1").first().text());
        Element content = document.select(".entry-content").first();
        System.out.println(content.html());
        demo.setDemoDes(content.select("p").first().text());
        demo.setDemoImgUrl(content.select("img").first().attr("src"));
        demo.setDemoShowUrl(content.select(".demo").first().attr("href"));
        demo.setDemoDownloadUrl(content.select(".download").first().attr("href"));
        return demo;
    }


}
