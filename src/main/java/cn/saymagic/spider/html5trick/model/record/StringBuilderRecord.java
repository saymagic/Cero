package cn.saymagic.spider.html5trick.model.record;

/**
 * Created by saymagic on 15/10/11.
 */
public class StringBuilderRecord implements IRecorder{

    StringBuilder builder = new StringBuilder();

    public void record(String s) {
        builder.append(s).append("\n");
        System.out.println(s);
    }

    public String getResult() {
        return builder.toString();
    }

    public void end() {
        // todo: sendmail
    }

    public void print() {
        System.out.print(builder.toString());
    }
}
