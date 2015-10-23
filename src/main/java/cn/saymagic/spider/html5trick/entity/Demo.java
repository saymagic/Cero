package cn.saymagic.spider.html5trick.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saymagic on 15/10/13.
 * todo : add to db
 */
@DatabaseTable(tableName = "webdemo")
public class Demo {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    private int demoId;

    @DatabaseField
    private String demoPageUrl;

    @DatabaseField(width = 3000)
    private String demoName;

    @DatabaseField(width = 3000)
    private String demoDes;

    @DatabaseField(width = 3000)
    private String demoImgUrl;

    @DatabaseField(width = 3000)
    private String demoShowUrl;

    @DatabaseField(width = 3000)
    private String demoDownloadUrl;

    @DatabaseField(width = 3000)
    private String demoJson;

    public int getDemoId() {
        return demoId;
    }

    public void setDemoId(int demoId) {
        this.demoId = demoId;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    public String getDemoDes() {
        return demoDes;
    }

    public void setDemoDes(String demoDes) {
        this.demoDes = demoDes;
    }

    public String getDemoImgUrl() {
        return demoImgUrl;
    }

    public void setDemoImgUrl(String demoImgUrl) {
        this.demoImgUrl = demoImgUrl;
    }

    public String getDemoPageUrl() {
        return demoPageUrl;
    }

    public void setDemoPageUrl(String demoPageUrl) {
        this.demoPageUrl = demoPageUrl;
    }

    public String getDemoShowUrl() {
        return demoShowUrl;
    }

    public void setDemoShowUrl(String demoShowUrl) {
        this.demoShowUrl = demoShowUrl;
    }

    public String getDemoDownloadUrl() {
        return demoDownloadUrl;
    }

    public void setDemoDownloadUrl(String demoDownloadUrl) {
        this.demoDownloadUrl = demoDownloadUrl;
    }

    public String getDemoJson() {
        return toString();
    }

    public void setDemoJson(String demoJson) {
        this.demoJson = demoJson;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("demoId", demoId);
            object.put("demoPageUrl", demoPageUrl);
            object.put("demoDes", demoDes);
            object.put("demoImgUrl", demoImgUrl);
            object.put("demoShowUrl", demoShowUrl);
            object.put("demoDownloadUrl", demoDownloadUrl);
            object.put("demoName", demoName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
