package com.kangyonggan.app.fortune.common.util;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 压缩工具
 *
 * @author kangyonggan
 * @since 4/24/17
 */
public class CompressorUtil {

    /**
     * 压缩CSS
     *
     * @param data
     * @return
     */
    public static String compressCSS(String data) {
        if (StringUtils.isEmpty(data)) {
            return "";
        }
        StringReader reader = new StringReader(data);

        try {
            CssCompressor cssCompressor = new CssCompressor(reader);
            StringWriter writer = new StringWriter();
            cssCompressor.compress(writer, -1);

            return writer.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 压缩JS
     *
     * @param data
     * @return
     */
    public static Map<String, String> compressJS(String data) {
        Map<String, String> resultMap = new HashMap();

        if (StringUtils.isEmpty(data)) {
            return resultMap;
        }
        StringReader reader = new StringReader(data);

        try {
            MyErrorReporter errorReporter = new MyErrorReporter(data);

            JavaScriptCompressor jsCompressor = new JavaScriptCompressor(reader, errorReporter);
            StringWriter writer = new StringWriter();
            jsCompressor.compress(writer, -1, true, true, false, false);

            resultMap.put("result", writer.toString());
            resultMap.put("warningMsg", errorReporter.getWarningMsg());
            resultMap.put("errorMsg", errorReporter.getErrorMsg());
        } catch (Exception e) {
            resultMap.put("errorMsg", e.getMessage());
        }

        return resultMap;
    }

    @Data
    private static class MyErrorReporter implements ErrorReporter {

        private String data;
        private String errorMsg;
        private String warningMsg;

        public MyErrorReporter(String data) {
            this.data = data;
        }

        public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
            if (line < 0) {
                warningMsg = message;
            } else {
                warningMsg = line + ':' + lineOffset + ':' + message;
            }

        }

        public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
            if (line < 0) {
                errorMsg = message;
            } else {
                errorMsg = line + ':' + lineOffset + ':' + message;
            }

        }

        public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
            this.error(message, sourceName, line, lineSource, lineOffset);
            return new EvaluatorException(message);
        }

    }

}
