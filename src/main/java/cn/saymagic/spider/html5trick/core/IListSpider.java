package cn.saymagic.spider.html5trick.core;

import cn.saymagic.spider.html5trick.entity.Demo;

import java.util.List;

/**
 * Created by saymagic on 15/10/13.
 */
public interface IListSpider<T>{

    public List<T> getOnePageList(String url) ;

    public int getAllPageNum(String url);

    public List<T> getAllList(String url);
}
