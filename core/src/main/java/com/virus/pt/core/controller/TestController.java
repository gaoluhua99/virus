package com.virus.pt.core.controller;

import com.virus.pt.db.service.UserAuthService;
import com.virus.pt.mail.service.MailService;
import com.virus.pt.model.dataobject.UserAuth;
import com.virus.pt.model.vo.MailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private MailService mailService;

    @GetMapping(value = "/1")
    public UserAuth getUser() {
        return userAuthService.getById(1);
    }

    @GetMapping(value = "/mail")
    public MailVo mail() {
        //发送邮件
        MailVo mailVo = new MailVo();
        mailVo.setTo("1428658308@qq.com");
        mailVo.setSubject("Virus激活");
        mailVo.setText("<h2><a href='?url'>点击激活账号</a></h2>");
        return mailService.sendMail(mailVo);
    }
}
