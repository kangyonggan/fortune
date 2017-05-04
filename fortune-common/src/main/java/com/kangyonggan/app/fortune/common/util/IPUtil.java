package com.kangyonggan.app.fortune.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 4/10/17
 */
public class IPUtil {

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取ip信息
     *
     * @param ip
     * @return
     */
    public static Map<String, String> getIpInfo(String ip) {
        Map<String, String> resultMap = new HashMap();
        try {
            String result = HttpUtil.sendGet("http://ip.taobao.com/service/getIpInfo.php", "ip=" + ip);

            JSONObject resultJSON = JSON.parseObject(result);
            String code = resultJSON.getString("code");
            String data = resultJSON.getString("data");

            resultMap.put("code", code);

            if ("0".equals(code)) {
                JSONObject dataJSON = JSON.parseObject(data);
                for (String key : dataJSON.keySet()) {
                    resultMap.put(key, dataJSON.getString(key));
                }
            } else {
                resultMap.put("data", data);
            }
        } catch (Exception e) {
            resultMap.put("code", "-9");
            resultMap.put("data", "网络异常，请稍后重试！");
        }
        return resultMap;
    }

}
