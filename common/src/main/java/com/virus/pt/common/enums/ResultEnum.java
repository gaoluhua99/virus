package com.virus.pt.common.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(200, "成功"),
    ERROR(4000, "错误"),
    FAIL(4001, "失败"),
    IP_ERROR(4002, "ip被封禁，请24小时后重试"),
    CONFIG_ERROR(4003, "数据库配置错误"),
    // 登录错误
    LOGIN_ERROR(4004, "邮箱或密码错误"),
    // 验证码错误
    CAPTCHA_ERROR(4005, "验证码错误"),
    ACTIVATION_ERROR(4006, "用户未激活"),
    LOGIN_STATE_ERROR(4007, "登录功能未开放"),
    ACCOUNT_BAN_ERROR(4008, "账号被ban"),
    METHOD_ERROR(4009, "未实现该功能"),
    REGISTER_STATE_ERROR(4010, "注册功能未开放"),
    // 创建用户错误,
    ACCOUNT_CREATE_ERROR(4011, "创建用户错误"),
    STORAGE_INIT_ERROR(4012, "存储初始化错误"),
    FILE_NOT_FOUND(4014, "文件未找到"),
    FILE_READ_ERROR(4015, "文件读取错误"),
    FILE_WRITE_ERROR(4016, "文件写入错误"),
    FILE_EMPTY(4017, "文件不能为空"),
    // token错误,
    TOKEN_ERROR(4020, "token错误"),
    // token decode错误，token可能被篡改
    TOKEN_DECODE_ERROR(4021, "token可能被篡改"),
    // token不能为空
    TOKEN_EMPTY_ERROR(4022, "token不能为空"),
    // token长度错误
    TOKEN_LENGTH_ERROR(4023, "token长度错误"),
    // 用户不存在
    USER_EMPTY_ERROR(4030, "用户不存在"),
    SIGN_RECORD_ERROR(4040, "签到错误，今天已签到"),
    SIGN_RECORD_EXP_ERROR(4041, "签到成功但经验更新错误"),
    LOGOUT_ERROR(4050, "用户注销错误"),
    LOGOUT_DATA_UPDATE_ERROR(4051, "用户注销时数据更新错误"),
    // 参数错误
    ARGS_ERROR(5000, "参数错误"),
    EMAIL_ERROR(5010, "邮箱错误"),
    EMAIL_FORMAT_ERROR(5011, "邮箱格式错误"),
    // 邮箱不能为空
    EMAIL_EMPTY_ERROR(5012, "邮箱不能为空"),
    // 邮箱长度错误
    EMAIL_LEN_ERROR(5013, "邮箱长度错误"),
    // 密码长度错误
    PASSWORD_LEN_ERROR(5020, "密码长度错误"),
    // 密码不能为空
    PASSWORD_EMPTY_ERROR(5021, "密码不能为空"),
    USERNAME_LEN_ERROR(5030, "用户名长度错误"),
    USERNAME_EMPTY_ERROR(5031, "用户名不能为空"),
    // 验证码格式错误
    CAPTCHA_FORMAT_ERROR(5040, "验证码格式错误"),
    // 验证码不能为空
    CAPTCHA_EMPTY_ERROR(5041, "验证码不能为空"),
    ACTIVATION_CODE_ERROR(5050, "激活码错误或已过期"),
    ACTIVATION_CODE_EMPTY_ERROR(5051, "激活码不能为空"),
    PASSKEY_ERROR(5060, "passkey错误"),
    PASSKEY_EMPTY_ERROR(5061, "passkey不能为空"),
    PASSKEY_LEN_ERROR(5062, "passkey长度必须为32位"),
    RESET_PASS_CODE_ERROR(5070, "重置密码认证码错误或已过期"),
    RESET_PASS_CODE_EMPTY_ERROR(5071, "重置密码认证码不能为空"),
    RESET_PASS_ERROR(5072, "重置密码错误"),
    IMAGE_TYPE_ERROR(5080, "只能上传图片文件"),
    UPLOAD_IMAGE_ERROR(5081, "上传图片失败"),
    NO_SUCH_SEED(5090, "没有该种子"),
    CATEGORY_ERROR(5100, "分类错误"),
    CATEGORY_QULITY_ERROR(5101, "获取分类质量错误"),
    POST_ERROR(5102, "获取文章错误"),
    GET_POST_LEN_ERROR(5103, "获取文章长度错误"),
    POST_EMPTY_ERROR(5104, "没有该文章"),
    IMDB_NOT_FOUND(5110, "没有从IMDB上获取到信息"),
    DOUBAN_NOT_FOUND(5111, "没有从豆瓣上获取到信息"),
    REGISTER_ERROR(5120, "邮箱或用户名已存在"),
    TORRENT_TYPE_ERROR(5121, "种子文件格式错误"),
    SAME_TORRENT_ERROR(5122, "其他用户已经上传该种子"),
    UPLOAD_TORRENT_ERROR(5123, "上传种子失败"),
    RELEASE_ERROR(5124, "文章发布失败"),
    RELEASE_SERIES_ERROR(5125, "文章发布成功但系列信息保存失败"),
    RELEASE_TORRENT_ERROR(5126, "文章发布成功但种子信息保存失败"),
    WAIT_ERROR(5130, "候选错误"),
    FIND_WAIT_ERROR(5131, "查询候选错误"),
    REPEAT_WAIT_ERROR(5132, "重复候选"),
    ADMIN_PERMISSION_ERROR(6000, "没有权限"),
    DB_UNIQUE_ERROR(7000, "唯一键约束");
    private int code;
    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\"code\":" + code + ",\"message\":\"" + message + "\"}";
    }
}
