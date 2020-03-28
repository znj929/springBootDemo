package com.example.demo.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
@Data
public class BaseEntity {
	
	private String createUser;
	private String createUserName;
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	private String updateUser;
	private String updateUserName;
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;
	@TableLogic(delval = "-1")
	private Integer deleted;
	@Version
	private Integer version;

}
