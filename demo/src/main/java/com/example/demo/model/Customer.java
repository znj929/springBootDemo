package com.example.demo.model;

import lombok.Data;

/**
 * 客户
 * @author Administrator
 *
 */
@Data
public class Customer extends BaseEntity {

	private String id;
	private String name;
	private String url;
	
	public Customer(){
		
	}
	public Customer(String id,String name,String url){
		this.id = id;
		this.name = name;
		this.url = url;
	}
}
