package com.tony.utils.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.util.Iterator;
import java.util.Map;

public class GsonUtils {
    //gson 忽略字段
    public static Gson skipBaseId() {
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {

            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                // TODO Auto-generated method stub
                return f.getName().equals("baseObjId") | f.getName().equals("associatedModelsMapForJoinTable") | f.getName().equals("associatedModelsMapWithFK")
                        | f.getName().equals("associatedModelsMapWithoutFK")
                        | f.getName().equals("listToClearAssociatedFK") | f.getName().equals("listToClearSelfFK")| f.getName().equals("isupload");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                // TODO Auto-generated method stub
                return false;
            }
        }).create();
        return gson;
    }
   //name替换
    public static JsonElement replaceKey(JsonElement source, Map<String, String> rep) {
        if (source == null || source.isJsonNull()) {
            return JsonNull.INSTANCE;
        }
        if (source.isJsonPrimitive()) {
            return source;
        }
        if (source.isJsonArray()) {
            JsonArray jsonArr = source.getAsJsonArray();
            JsonArray jsonArray = new JsonArray();
           for(int i=0;i<jsonArr.size();i++) {
               jsonArray.add(replaceKey(jsonArr.get(i), rep));
           };
            return jsonArray;
        }
        if (source.isJsonObject()) {
            JsonObject jsonObj = source.getAsJsonObject();
            Iterator<Map.Entry<String, JsonElement>> iterator = jsonObj.entrySet().iterator();
            JsonObject newJsonObj = new JsonObject();
            while(iterator.hasNext()){
                Map.Entry<String, JsonElement> item=iterator.next();
                String key = item.getKey();
                JsonElement value = item.getValue();
                if (rep.containsKey(key)) {
                    String newKey = rep.get(key);
                    key = newKey;
                }
                newJsonObj.add(key, replaceKey(value, rep));
            };

            return newJsonObj;
        }
        return JsonNull.INSTANCE;
    }
}
