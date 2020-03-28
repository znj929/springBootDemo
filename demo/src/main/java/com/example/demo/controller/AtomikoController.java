package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事物一致性框架测试
 * 涉及到多个数据源同时操作的情况
 * @author Administrator
 *
 */
@RestController
public class AtomikoController {

	@RequestMapping("/testAtomiko")
	public void testAtomiko(){
		
	}
}
