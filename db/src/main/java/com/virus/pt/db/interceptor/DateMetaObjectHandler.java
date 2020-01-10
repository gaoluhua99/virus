package com.virus.pt.db.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class DateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("created", System.currentTimeMillis(), metaObject);
        this.setInsertFieldValByName("modified", System.currentTimeMillis(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("modified", System.currentTimeMillis(), metaObject);
    }
}
