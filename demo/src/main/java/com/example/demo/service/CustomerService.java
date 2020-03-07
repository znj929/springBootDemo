package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CustomerDao;
import com.example.demo.dynamicDataSource.TargetDataSource;
import com.example.demo.model.Customer;

@Service("customerService")
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;
	
	@TargetDataSource(name = "ds2")
	public List<Customer> getCustomers(){
		return customerDao.selectAll();
	}
	
}
