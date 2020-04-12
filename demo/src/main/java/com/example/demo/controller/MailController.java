package com.example.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.utils.mail.EmailParam;
import com.example.demo.utils.mail.SendEmailUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mail")
public class MailController {
	
	@Value("${spring.mail.username}")
	private String from;
	/*
	 * @Value("${templatePath}") private String templatePath;
	 * 
	 * @Value("${imagePath}") private String imagePath;
	 */

	@Autowired
	private SendEmailUtils sendEmailUtils;
	
	@RequestMapping("/sendHtmlMail")
	public String sendHtmlMail() {
		try {
			EmailParam emailParam = new EmailParam();
			emailParam.setContent("测试一下邮件");
			emailParam.setUpdatePerson("赵南健");
			emailParam.setRemarks("备注");
			emailParam.setUpdateDate(new Date().toLocaleString());
            //此处to数组输入多个值，即可实现批量发送
			String [] to={"18379183218@163.com"};
			String imagePath = "";
			String templatePath = "";
			sendEmailUtils.thymeleafEmail(from, to, "这是一封测试邮件主题", emailParam, templatePath , imagePath );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ok";
	}

}
