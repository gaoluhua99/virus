package com.virus.pt.model.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author intent
 * @date 2019/7/28 14:41
 * @about <link href='http://zzyitj.xyz/'/>
 */
public class BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 将srcObj对象字段的值拷贝到destObj字段（前提：两个对象中的字段名相同时）
     * 其实就是通过反射将值先存在map中，在遍历set进目标对象
     *
     * @param srcObj  源对象
     * @param destObj 目标对象
     */
    public static void copyFieldToBean(Object srcObj, Object destObj) {
        Map<String, Object> srcMap = new HashMap<String, Object>();
        Field[] srcFields = srcObj.getClass().getDeclaredFields();
        for (Field srcField : srcFields) {
            try {
                srcField.setAccessible(true);
                srcMap.put(srcField.getName(), srcField.get(srcObj)); //获取属性值
            } catch (Exception e) {
                logger.error("copyFieldToBean错误", e);
            }
        }
        Field[] destFields = destObj.getClass().getDeclaredFields();
        for (Field destField : destFields) {
            destField.setAccessible(true);
            if (srcMap.get(destField.getName()) == null) {
                continue;
            }
            try {
                if (!destField.getName().equals("serialVersionUID")) {
                    destField.set(destObj, srcMap.get(destField.getName())); //给属性赋值
                }
            } catch (Exception e) {
                logger.error("copyFieldToBean错误", e);
            }
        }
    }
}
