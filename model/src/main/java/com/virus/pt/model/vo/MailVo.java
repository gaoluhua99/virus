package com.virus.pt.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/20 2:22 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class MailVo {
    private String id;//邮件id
    private String from;//邮件发送人
    private String to;//邮件接收人
    private String subject;//邮件主题
    private String text;//邮件内容
    private Date sentDate;//发送时间
    private String cc; //抄送
    private String bcc; //密送
    private String status; //状态
    private String error; //报错信息
    @JsonIgnore
    private MultipartFile[] multipartFiles;//邮件附件
}
