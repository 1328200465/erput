package com.erupt.model;

import com.erupt.annotation.Erupt;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by liyuepeng on 9/29/18.
 */
public class EruptModel implements Serializable{

    private transient Class<?> clazz;

    private transient Erupt erupt;

    private JsonObject eruptJson;

    private JsonArray eruptFieldViews;

    private List<EruptFieldModel> eruptFieldModels;



    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Erupt getErupt() {
        return erupt;
    }

    public void setErupt(Erupt erupt) {
        this.erupt = erupt;
    }

    public JsonObject getEruptJson() {
        return eruptJson;
    }

    public void setEruptJson(JsonObject eruptJson) {
        this.eruptJson = eruptJson;
    }

    public JsonArray getEruptFieldViews() {
        return eruptFieldViews;
    }

    public void setEruptFieldViews(JsonArray eruptFieldViews) {
        this.eruptFieldViews = eruptFieldViews;
    }

    public List<EruptFieldModel> getEruptFieldModels() {
        return eruptFieldModels;
    }

    public void setEruptFieldModels(List<EruptFieldModel> eruptFieldModels) {
        this.eruptFieldModels = eruptFieldModels;
    }
}