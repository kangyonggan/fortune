package com.kangyonggan.app.fortune.common.util;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;


/**
 * @author kangyonggan
 * @since 2017/4/22 0022
 */
public class XmlUtil {

    /**
     * xml格式化
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String format(String data) throws Exception {
        SAXReader reader = new SAXReader();
        StringReader in = new StringReader(data);

        XMLWriter writer = null;
        try {
            Document doc = reader.read(in);

            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            format.setIndentSize(4);
            format.setExpandEmptyElements(true);
            StringWriter out = new StringWriter();
            writer = new XMLWriter(out, format);

            writer.write(doc);
            writer.flush();

            return out.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }
}
