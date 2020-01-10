package com.virus.pt.common.util;

import com.virus.pt.model.vo.IPEndpointVo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author intent
 * @date 2019/7/24 14:31
 * @about <link href='http://zzyitj.xyz/'/>
 */
public class RequestUtils {
    private static final String SPLIT = "\\?";
    private static final String REGEX_KEY = "&";
    private static final String REGEX_VALUE = "=";

    public static String getQueryString(String src, String key) {
        String[] array = getQueryArray(src, key);
        if (array.length > 0) {
            return getQueryArray(src, key)[0];
        }
        return null;
    }

    /***
     * 获取指定的值
     *
     * @param src 源字符串
     * @param key key
     * @return null则源字符串中没有key
     */
    public static String[] getQueryArray(String src, String key) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(src) && StringUtils.isNotBlank(key)) {
//            String[] splitArray = src.split(SPLIT);
//            if (splitArray.length > 1 && StringUtils.isNotBlank(splitArray[splitArray.length - 1])) {
//                src = splitArray[1];
            String[] keys = src.split(REGEX_KEY);
            for (String sk : keys) {
                String[] values = sk.split(REGEX_VALUE);
                if (values[0].equals(key)) {
                    list.add(values[1]);
                }
//                }
            }
        }
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static IPEndpointVo getIPEndpoint(String str) {
        if (StringUtils.isNotBlank(str)) {
            String[] array = str.split("]:");
            if (array.length > 1) {
                return new IPEndpointVo(array[0].replace("[", ""), array[1]);
            }
        }
        return null;
    }
}
