package com.virus.pt.db.service;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class DateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "created", Date.class, new Date());
        this.fillStrategy(metaObject, "created", new Date());
        this.strictUpdateFill(metaObject, "modified", Date.class, new Date());
        this.fillStrategy(metaObject, "modified", new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "modified", Date.class, new Date());
        this.fillStrategy(metaObject, "modified", new Date());
    }
}
