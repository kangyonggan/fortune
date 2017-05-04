package com.kangyonggan.app.fortune.common.util;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author kangyonggan
 * @since 2017/2/11
 */
@Log4j2
public class HtmlUtil {

    /**
     * 失败重试次数
     */
    private static int TRY_COUNT = 5;

    /**
     * 解析url
     *
     * @param url
     * @return
     */
    public static Document parseUrl(String url) {
        int cnt = 0;
        while (cnt < TRY_COUNT) {
            try {
                Document doc = Jsoup.connect(url).get();
                log.info("抓取url成功, {}", url);
                return doc;
            } catch (Exception e) {
                cnt++;
                log.warn("抓取url失败, 这是第" + cnt + "次, 共" + TRY_COUNT + "次", e);
                try {
                    Thread.sleep(cnt * 500);
                } catch (InterruptedException e1) {
                    log.error(e1);
                }
            }
        }

        log.warn("{}此抓取机会已用完，任然没有成功抓取网页", TRY_COUNT);
        return null;
    }

}
