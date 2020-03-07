package com.example.demo.model.excel;

import java.net.URL;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.example.demo.annotation.ExcelStyle;

import lombok.Data;

@Data
@HeadRowHeight(value = 20)
@ContentRowHeight(40)
public class CustomerExcelTemple {
	
	@ExcelProperty(value = "id",index = 0)
	@ColumnWidth(value = 10)
	@ExcelStyle(bold = true,color = 2)
	private String id;
	
	@ExcelProperty(value = "name",index = 1)
	@ColumnWidth(value = 10)
	@ExcelStyle()
	private String name;
	/**
	 * 根据URL导出
	 */
	@ExcelProperty(value = "url",index = 2)
	@ColumnWidth(value = 30)
	@ExcelStyle()
	private URL url;
	
	private String total;
	
}
