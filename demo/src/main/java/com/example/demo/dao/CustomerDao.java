package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.Customer;

public interface CustomerDao extends BaseMapper<Customer> {
	
	@Select("select * from customer")
	List<Customer> selectAll();
}
