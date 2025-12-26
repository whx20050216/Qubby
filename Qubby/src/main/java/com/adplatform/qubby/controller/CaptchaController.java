package com.adplatform.qubby.controller;

import com.adplatform.qubby.util.CaptchaUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class CaptchaController {

    @GetMapping("/captcha")
    public void captcha(HttpSession session, HttpServletResponse response) throws IOException {
        String captchaText = CaptchaUtil.generateText();
        session.setAttribute("captcha", captchaText);

        BufferedImage image = CaptchaUtil.generateImage(captchaText);
        response.setContentType("image/png");

        ImageIO.write(image, "png", response.getOutputStream());
    }
}