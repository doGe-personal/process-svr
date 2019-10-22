package com.process.common.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

/**
 * @author Danfeng
 * @since 2018/12/11
 */
public class FeignUtil {

    /**
     * 解析feign异常返回
     *
     * @param message
     * @return
     */
    public static JSONObject parseResponseContent(String message) {
        JSONObject json = new JSONObject();
        if (!StringUtils.isEmpty(message)) {
            int index = message.indexOf("\n");
            if (index > 0) {
                String string = message.substring(index);
                if (!StringUtils.isEmpty(string)) {
                    json = JSONObject.parseObject(string.trim());
                }
            }
        }
        return json;
    }

}
