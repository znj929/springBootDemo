package com.example.demo.utils.mail;

import lombok.Data;

/**
 * 邮件参数实体类
 * 
 * @author znj
 *
 */
@Data
public class EmailParam {

	private String content;// 内容
	private String updatePerson;// 操作人员
	private String updateDate;// 操作时间
	private String remarks;// 备注

}
