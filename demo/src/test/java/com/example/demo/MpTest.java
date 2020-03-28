package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;

@SpringBootTest
public class MpTest {
	
	@Autowired
	private CustomerService customerService;

	@Test
	public void mp(){
		List<Customer> list = customerService.list();
		System.err.println(list.size());
	}
	@Test
	public void bachTest(){
		List<Customer> customers = Lists.newArrayList();
		Customer customer1 = new Customer(UUID.randomUUID().toString(), "徐峰年", "");
		Customer customer2 = new Customer(UUID.randomUUID().toString(),"赵文肖", "");
		customers.add(customer1);
		customers.add(customer2);
		customerService.saveBatch(customers);
	}
	
	@Test
	public void pageTest(){
		Page<Customer> page = new Page<>(0, 2);
		page = customerService.page(page);
		System.err.println(page);
	}
	/**
	 * 测试逻辑删除
	 */
	@Test
	public void deleteTest(){
		customerService.removeById("1");
	}
	
	/**
	 * 乐观锁测试
	 */
	@Test
	public void versionTest(){
		Customer entity = new Customer();
		entity.setId("1");
		entity.setName("znnnn");
		entity.setVersion(0);
		customerService.updateById(entity );
	}
}
