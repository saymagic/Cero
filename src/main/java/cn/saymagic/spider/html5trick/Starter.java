package cn.saymagic.spider.html5trick;

import cn.saymagic.spider.html5trick.core.DemoListSpider;
import cn.saymagic.spider.html5trick.download.Downloader;
import cn.saymagic.spider.html5trick.entity.Demo;
import cn.saymagic.spider.html5trick.model.record.StringBuilderRecord;

import java.util.List;

/**
 * Created by saymagic on 15/10/13.
 */
public class Starter {


    public static void main(String args[]) {

        DemoListSpider spider = new DemoListSpider();
        List<Demo> demos = spider.getAllList(Constants.BASE_URL);
        for (Demo demo : demos) {
            Downloader.getInstance().download(demo, new StringBuilderRecord());
        }

    }
}
