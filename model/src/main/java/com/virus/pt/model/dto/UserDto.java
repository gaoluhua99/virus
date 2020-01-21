package com.virus.pt.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 2:15 下午
 * @email zzy.main@gmail.com
 */
@ApiModel(value = "组合用户model")
@Getter
@Setter
@ToString
public class UserDto {

    // UserAuth
    @ApiModelProperty(value = "uid", example = "1")
    private Long userAuthId;

    @ApiModelProperty(value = "用户创建时间", example = "1561004995625")
    private Long create;

    @ApiModelProperty(value = "用户更新时间", example = "1561004995625")
    private Long modify;

    @ApiModelProperty(value = "email", example = "1428658308@qq.com")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @ApiModelProperty(value = "是否激活", example = "true")
    private Boolean isActivation;

    // UserData
    @ApiModelProperty(value = "passkey", example = "a88b78122abc866d9oc7acf0b7db048e")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String passkey;

    @ApiModelProperty(value = "下载量", example = "0")
    private Long downloaded;

    @ApiModelProperty(value = "上传量", example = "0")
    private Long uploaded;

    @ApiModelProperty(value = "用户状态", example = "1")
    private Short status;

    // UserInfo
    @ApiModelProperty(value = "昵称", example = "intent")
    private String username;

    @ApiModelProperty(value = "性别true男false女", example = "false")
    private Boolean sex;

    @ApiModelProperty(value = "金币", example = "0")
    private Long gold;

    @ApiModelProperty(value = "经验", example = "1")
    private Long exp;

    @ApiModelProperty(value = "邀请人的id", example = "1")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long inviterId;

    @ApiModelProperty(value = "用户头像链接", example = "xxx")
    private String avatarUrl;

    @ApiModelProperty(value = "注册时的ip地址", example = "127.0.0.1")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ip;

    @ApiModelProperty(value = "token", example = "xxoo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @ApiModelProperty(value = "今天是否签到", example = "false")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isSigned;
}