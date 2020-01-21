package com.virus.pt.core.util;

import com.virus.pt.common.enums.FileTypeEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.ImageUtils;
import com.virus.pt.common.util.PathUtils;
import com.virus.pt.core.service.impl.CaptchaStorageService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class CaptchaUtils {
    // 图片的宽度
    private static final int CAPTCHA_WIDTH = 90;
    // 图片的高度
    private static final int CAPTCHA_HEIGHT = 20;
    // 验证码的个数
    private static final int CAPTCHA_CODECOUNT = 4;

    private static final int XX = 15;
    private static final int CAPTCHA_FONT_HEIGHT = 18;
    private static final int CAPTCHA_CODE_Y = 16;
    private static final char[] codeSequence = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'J', 'K', 'L', 'M', 'N',
            'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String genCaptcha(CaptchaStorageService captchaStorageService, long millis) throws TipException {
        // 定义图像 Buffer
        BufferedImage buffImg = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 创建一个绘制图像的对象
        Graphics2D g = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(new Color(240, 242, 245));
        g.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        // 设置字体
        g.setFont(new Font("Fixedsys", Font.BOLD, CAPTCHA_FONT_HEIGHT));
        // 设置字体边缘光滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 画边框
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, CAPTCHA_WIDTH - 1, CAPTCHA_HEIGHT - 1);
        // 随机产生20条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.BLACK);
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(CAPTCHA_WIDTH);
            int y = random.nextInt(CAPTCHA_HEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 保存随机产生的验证码，以便用户登录后进行验证
        StringBuilder randomCode = new StringBuilder();
        int red = 0, green = 0, blue = 0;
        // 随机产生验证码
        for (int i = 0; i < CAPTCHA_CODECOUNT; i++) {
            // 得到随机产生的验证码数字
            String code = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中
            g.setColor(new Color(red, green, blue));
            g.drawString(code, (i + 1) * XX, CAPTCHA_CODE_Y);
            // 将产生的随机数组合在一起
            randomCode.append(code);
        }
        ImageUtils.saveImage(buffImg, FileTypeEnum.CAPTCHA_IMAGE.getName(),
                new File(captchaStorageService.getRootLocation().toAbsolutePath() + PathUtils.SPEA +
                        millis + "." + FileTypeEnum.CAPTCHA_IMAGE.getName()));
        return randomCode.toString();
    }
}
