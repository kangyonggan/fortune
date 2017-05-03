package com.kangyonggan.app.fortune.common;

import org.pegdown.PegDownProcessor;

/**
 * @author kangyonggan
 * @since 2016/10/18
 */
public class MarkdownUtil {

    /**
     * markdown语法转html语法
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        try {
            return new PegDownProcessor(Integer.MAX_VALUE).markdownToHtml(markdown);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
