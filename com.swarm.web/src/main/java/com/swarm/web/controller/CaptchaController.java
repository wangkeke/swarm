package com.swarm.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.controller.BaseController;
import com.wf.captcha.utils.CaptchaUtil;

/**
 * 验证码
 * @author Administrator
 *
 */
@RestController
public class CaptchaController extends BaseController{
	
	@RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置位数
        CaptchaUtil.out(5, request, response);

    }
	
}
