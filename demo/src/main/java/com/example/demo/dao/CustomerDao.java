package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.example.demo.model.Customer;

public interface CustomerDao {
	
	@Select("select * from customer")
	List<Customer> selectAll();
}
