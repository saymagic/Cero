package cn.saymagic.spider.html5trick.model.record;

/**
 * Created by saymagic on 15/10/11.
 */
public interface IRecorder {

    public void record(String s);

    public String getResult();

    public void end();

    public void print();

}
