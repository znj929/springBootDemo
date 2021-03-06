package com.example.demo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.demo.handler.easyexcel.ExcelStyleAnnotationCellWriteHandler;
import com.example.demo.model.Customer;
import com.example.demo.model.excel.CustomerExcelTemple;
import com.example.demo.service.CustomerService;
import com.example.demo.utils.FileUtils;
import com.example.demo.utils.ImageUtils;

@RestController
public class EasyExcelController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/test")
	public void test(){
		List<Customer> customers = customerService.getCustomers();
		System.err.println("customers"+customers.size());
		List<Customer> customers2 = customerService.getCustomers2();
		System.err.println("customers2"+customers2.size());
	}
	
	public List<CustomerExcelTemple> getData() throws MalformedURLException{
		List<Customer> customers = customerService.getCustomers();
		List<CustomerExcelTemple> customerExcelTemples = new ArrayList<>();
		for (Customer customer : customers) {
			CustomerExcelTemple customerExcelTemple = new CustomerExcelTemple();
			BeanUtils.copyProperties(customer, customerExcelTemple);
			//customerExcelTemple.setUrl(new URL(customer.getUrl()));
			
			customerExcelTemples.add(customerExcelTemple);
		}
		return customerExcelTemples;
	}
	public List<CustomerExcelTemple> getDataImage() throws MalformedURLException{
		List<Customer> customers = customerService.getCustomers();
		List<CustomerExcelTemple> customerExcelTemples = new ArrayList<>();
		for (Customer customer : customers) {
			CustomerExcelTemple customerExcelTemple = new CustomerExcelTemple();
			BeanUtils.copyProperties(customer, customerExcelTemple);
			//customerExcelTemple.setUrl(new URL(customer.getUrl()));
			byte[] byteByImgUrl = ImageUtils.getByteByImgUrl(customer.getUrl());
			if(byteByImgUrl!=null && byteByImgUrl.length>0) {
				//customerExcelTemple.setBaseImg(byteByImgUrl);
			}
			customerExcelTemples.add(customerExcelTemple);
		}
		return customerExcelTemples;
	}

	@GetMapping("/getCustomers")
	public List<Customer> getCustomers() {
		return customerService.getCustomers();
	}
	
	@GetMapping("/getExcelImage")
	public String getExcelImage() throws MalformedURLException{
		//指定目标地址
        String fileName = FileUtils.getPath() + System.currentTimeMillis() + ".xlsx";
        //准备数据
        List<CustomerExcelTemple> data = getDataImage();
        ExcelWriter excelWriter = EasyExcel.write(fileName,CustomerExcelTemple.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.write(data, writeSheet);
        excelWriter.finish();
		return fileName;
	}
	
	
	
	
	/**
	 * 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
	 * {} 代表普通变量 {.} 代表是list的变量
	 * @return
	 * @throws MalformedURLException 
	 * @throws FileNotFoundException 
	 */
	@GetMapping("getExcelTemple")
	public String getExcelTemple() throws MalformedURLException, FileNotFoundException{
		//模板地址
        //String templateFileName = "D:\\test\\order.xlsx";
		//System.getProperty（"user.dir"）+“file”
		String templateFileName = FileUtils.getPath()+"template"+File.separator+"order.xlsx";
        //指定目标地址
        String fileName = FileUtils.getPath() + System.currentTimeMillis() + ".xlsx";
        //准备数据
        List<CustomerExcelTemple> data = getData();
        //开始
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
       
        //如果list不是最后一行就要吧forceNewRow置为TRUE
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(data, fillConfig, writeSheet);
        
        //
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2020年03月05日13:28:28");
        map.put("total", "1000.23");
        excelWriter.fill(map, writeSheet);
		/*
		 * CustomerExcelTemple excelTemple = new CustomerExcelTemple();
		 * excelTemple.setTotal("1000.23"); excelWriter.fill(excelTemple, writeSheet);
		 */
        
        //结束关闭流
        excelWriter.finish();
		return fileName;
	}
	
	
	@GetMapping("/getExcel")
	public String getExcel() throws MalformedURLException {
		String fileName = EasyExcelController.class.getResource("/").getPath() + "imageWrite" + System.currentTimeMillis() + ".xlsx";
		List<Customer> customers = customerService.getCustomers();
		List<CustomerExcelTemple> customerExcelTemples = new ArrayList<>();
		for (Customer customer : customers) {
			CustomerExcelTemple customerExcelTemple = new CustomerExcelTemple();
			BeanUtils.copyProperties(customer, customerExcelTemple);
			//customerExcelTemple.setUrl(new URL(customer.getUrl()));
			customerExcelTemple.setAddrUrl(customer.getUrl());
			customerExcelTemples.add(customerExcelTemple);
		}
		ExcelStyleAnnotationCellWriteHandler writeHandler = new ExcelStyleAnnotationCellWriteHandler(CustomerExcelTemple.class, null, null);
		EasyExcel.write(fileName, CustomerExcelTemple.class).registerWriteHandler(writeHandler)
		.sheet("测试导出图片").doWrite(customerExcelTemples);
		return fileName;
	}
	
	@GetMapping("/excel")
	public String excel() throws MalformedURLException{
		String fileName = EasyExcelController.class.getResource("/").getPath() + "imageWrite" + System.currentTimeMillis() + ".xlsx";
		List<Customer> customers = customerService.getCustomers();
		List<CustomerExcelTemple> customerExcelTemples = new ArrayList<>();
		for (Customer customer : customers) {
			CustomerExcelTemple customerExcelTemple = new CustomerExcelTemple();
			BeanUtils.copyProperties(customer, customerExcelTemple);
			//customerExcelTemple.setUrl(new URL(customer.getUrl()));
			customerExcelTemples.add(customerExcelTemple);
		}
		/*EasyExcel.write(fileName, CustomerExcelTemple.class).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
		.sheet("测试导出图片").doWrite(customerExcelTemples);*/
		//头的样式
		WriteCellStyle headWriteCellStyle = new WriteCellStyle();
		headWriteCellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		WriteFont headWriteFont = new WriteFont();
		headWriteFont.setFontHeightInPoints((short)15);
		headWriteCellStyle.setWriteFont(headWriteFont );
		//内容的样式
		WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
		//设置边框样式
		contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
		contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
		contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
		contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
		 //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置 垂直居中
        //contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		WriteFont writeFont = new WriteFont();
		writeFont.setBold(true);
		contentWriteCellStyle.setWriteFont(writeFont);
		HorizontalCellStyleStrategy horizontalCellStyleStrategy =
	            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
		EasyExcel.write(fileName, CustomerExcelTemple.class).registerWriteHandler(horizontalCellStyleStrategy)
		.sheet("测试导出图片").doWrite(customerExcelTemples);
		return fileName;
	}
}
