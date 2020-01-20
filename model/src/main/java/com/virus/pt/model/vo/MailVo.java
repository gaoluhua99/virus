package com.virus.pt.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "邮件id", example = "1", required = true)
    private String id;
    @ApiModelProperty(value = "邮件发送人", example = "123456@qq.com", required = true)
    private String from;
    @ApiModelProperty(value = "邮件接收人", example = "123456@qq.com", required = true)
    private String to;
    @ApiModelProperty(value = "邮件主题", example = "Virus", required = true)
    private String subject;
    @ApiModelProperty(value = "邮件内容", example = "Virus邀请码", required = true)
    private String text;
    @ApiModelProperty(value = "发送时间", required = true)
    private Date sentDate;
    @ApiModelProperty(value = "抄送")
    private String cc;
    @ApiModelProperty(value = "密送")
    private String bcc;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "报错信息", example = "xxxx")
    private String error;

    @ApiModelProperty(value = "邮件附件")
    @JsonIgnore
    private MultipartFile[] multipartFiles;
}
