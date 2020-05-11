package com.virus.pt.pestilence.task;

import com.virus.pt.common.exception.TipException;
import com.virus.pt.db.service.UserDataService;
import com.virus.pt.model.dataobject.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/5/11 6:11 下午
 * @email zzy.main@gmail.com
 */
@Configuration
@EnableScheduling
public class UpdateDataTask {
    private static final Logger logger = LoggerFactory.getLogger(UpdateDataTask.class);

    @Autowired
    private UserDataService userDataService;

    /**
     * 定时更新用户数据
     * 每天2点执行一次
     *
     * @throws TipException 异常
     */
    @Scheduled(cron = "0 0 2 * * *")
    private void updateData() throws TipException {
        logger.info("开始更新数据...");
        List<UserData> userDataList = userDataService.getListRedisById();
        userDataList.forEach(userData -> {
            userDataService.updateData(userData.getUkPasskey(), userData.getUploaded(), userData.getDownloaded());
            logger.info("正在更新用户 {} 的数据, uploaded: {}, downloaded: {}",
                    userData.getId(), userData.getUploaded(), userData.getDownloaded());
        });
        logger.info("结束更新数据...");
    }
}
