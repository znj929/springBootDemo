package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.CustomerDao;
import com.example.demo.dynamicdatasource.TargetDataSource;
import com.example.demo.model.Customer;

@Service("customerService")
public class CustomerService extends ServiceImpl<CustomerDao, Customer> {
	@Autowired
	private CustomerDao customerDao;
	
	@TargetDataSource(name = "ds1")
	public List<Customer> getCustomers(){
		List<Customer> selectAll = customerDao.selectAll();
		return selectAll;
	}
	@TargetDataSource(name = "ds2")
	public List<Customer> getCustomers2(){
		List<Customer> selectAll = customerDao.selectAll();
		return selectAll;
	}
	
}
