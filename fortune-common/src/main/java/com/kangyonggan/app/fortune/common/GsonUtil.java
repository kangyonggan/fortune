package com.kangyonggan.app.fortune.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.StringWriter;

/**
 * @author kangyonggan
 * @since 2017/4/22 0022
 */
public class GsonUtil {

    /**
     * 格式化json
     *
     * @param data
     * @return
     */
    public static String format(String data) {
        try {
            Gson gson = new Gson();
            StringWriter stringWriter = new StringWriter();
            JsonWriter writer = gson.newJsonWriter(stringWriter);
            writer.setIndent("\t");

            JsonElement jsonElement = new JsonParser().parse(data);
            gson.toJson(jsonElement, writer);

            return stringWriter.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
