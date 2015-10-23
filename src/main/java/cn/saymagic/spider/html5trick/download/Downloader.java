package cn.saymagic.spider.html5trick.download;

import cn.saymagic.spider.html5trick.Constants;
import cn.saymagic.spider.html5trick.entity.Demo;
import cn.saymagic.spider.html5trick.util.Compress;
import cn.saymagic.spider.html5trick.model.record.IRecorder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by saymagic on 15/10/21.
 */
public class Downloader {



    private static Downloader ourInstance = new Downloader();

    private static int time = 1;

    public static Downloader getInstance() {
        return ourInstance;
    }

    private Downloader() {
    }

    public void download(Demo demo, IRecorder recorder) {
        recorder.record("开始处理: " + demo.getDemoName() + " : " + demo.toString());
        File aimFolder = new File((Constants.BASE_PATH + File.separator + demo.getDemoName()).replace(" ", "_"));
        try {
            FileUtils.writeStringToFile(new File(aimFolder.getAbsolutePath() + File.separator + "demo.txt"), demo.toString());
        } catch (IOException e) {
            recorder.record(demo.toString() + " demo.toString写入失败:" + e.toString());
        }

        recorder.record("下载Demo: " + demo.getDemoName() + "， 地址:" + demo.getDemoDownloadUrl());
        try {
            File rarFile = new File(aimFolder.getAbsolutePath() + File.separator + getUrlFileName(demo.getDemoDownloadUrl()));
            FileUtils.copyURLToFile(new URL(demo.getDemoDownloadUrl()), rarFile);
            recorder.record("下载Demo: " + demo.getDemoName() + "完成");
            recorder.record("下载Demo图片: " + demo.getDemoName() + "， 地址:" + demo.getDemoImgUrl());

            FileUtils.copyURLToFile(new URL(demo.getDemoImgUrl()), new File(aimFolder.getAbsolutePath() + File.separator + getUrlFileName(demo.getDemoImgUrl())));
            recorder.record("下载Demo图片: " + demo.getDemoImgUrl() + "完成");
            recorder.record("解压Demo: " + demo.getDemoName());

            Compress.unrar(rarFile.getAbsolutePath(), Constants.GIT_PATH);
            recorder.record("解压Demo: " + demo.getDemoName() + "完成");
        } catch (Exception e) {
            e.printStackTrace();
            if ((time++) % 4 == 0) {
                recorder.record("download demo error: " + e.toString() + "重试第" + time % 4 + "次");
                try {
                    FileUtils.deleteDirectory(aimFolder);
                    download(demo, recorder);
                    return;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        try {
            FileUtils.writeStringToFile(new File(aimFolder.getAbsolutePath() + File.separator + "result.txt"), recorder.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        System.out.println();

    }

    private String getUrlFileName(String url) {
        if (null == url || "".equals(url)) {
            return "tmp";
        }
        String[] splits = url.split("/");
        if (splits.length > 1) {
            return splits[splits.length - 1];
        }
        return "tmp";
    }

}
