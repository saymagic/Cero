package cn.saymagic.spider.html5trick.core;

import cn.saymagic.spider.html5trick.download.Downloader;
import cn.saymagic.spider.html5trick.entity.Demo;
import cn.saymagic.spider.html5trick.model.record.StringBuilderRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saymagic on 15/10/13.
 */
public class DemoListSpider implements IListSpider {

    private IDemoSpider demoSpider = new DefaultDemoSpider();

    @Override
    public List<Demo> getOnePageList(String url) {
        int i = 0;
        List<Demo> demoList = new ArrayList<Demo>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements articles = document.select("article");
            for (Element article : articles) {
                System.out.println("第" + i++);
                Elements element =article.select(".download");
                if (null != element && null != element.first()) {
                    String downloadItem = element.first().attr("href");
                    if (downloadItem.endsWith(".rar")) {
                        Demo demo = new Demo();
                        String idValue = article.id();
                        String[] idItem = idValue.split("-");
                        if (idItem.length == 2) {
                            try {
                                demo.setDemoId(Integer.valueOf(idItem[1]));
                            } catch (NumberFormatException e) {
                                demo.setDemoId(-1);
                            }
                        }
                        demo.setDemoDownloadUrl(downloadItem);
                        demo.setDemoShowUrl(article.select(".demo").first().attr("href"));
                        Element titleElement = article.select(".entry-title").first();
                        demo.setDemoName(titleElement.text());
                        demo.setDemoPageUrl(titleElement.select("a").first().attr("href"));
                        Elements contentElement = article.select(".entry-content").first().select("p");
                        if (null != contentElement) {
                            if (contentElement.size() == 3) {
                                demo.setDemoDes(contentElement.get(0).text());
                                demo.setDemoImgUrl(contentElement.get(1).select("img").first().attr("src"));
                            }
                        }
                        demoList.add(demo);
                    } else {
                        System.out.println(downloadItem);

                        Demo demo = demoSpider.doSearch(downloadItem);
                        if (null != demo) {
                            demoList.add(demo);
                        }

                    }
                }
            }
        } catch (IOException e) {
            return null;
        }
        return demoList;
    }

    @Override
    public int getAllPageNum(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            String lastPageUrl = document.select(".last").last().attr("href");
            String[] parts = lastPageUrl.split("/");
            if (parts.length > 0) {
                try {
                    return Integer.valueOf(parts[parts.length - 1]);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    @Override
    public List<Demo> getAllList(String url) {
        int allPage = getAllPageNum(url);
        System.out.println("共计" + allPage + "页");
        if (allPage == 0) {
            return null;
        }
        List<Demo> demoList = new ArrayList<Demo>();
        for (int i = 45; i <= allPage; i++) {
            System.out.println("第" + i + "页");
            String itemUrl = "http://www.html5tricks.com/page/" + i;
            List<Demo> demos = getOnePageList(itemUrl);
            if (null != demos) {
                for (Demo demo : demos) {
                    Downloader.getInstance().download(demo, new StringBuilderRecord());
                }
            }
        }
        return demoList;
    }
}
