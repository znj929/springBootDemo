package com.example.demo.handler;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.demo.annotation.ExcelStyle;
import com.example.demo.utils.ImageUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelStyleAnnotationCellWriteHandler extends HorizontalCellStyleStrategy {
	
	private Class c;
	
	public ExcelStyleAnnotationCellWriteHandler(Class c, WriteCellStyle headWriteCellStyle, WriteCellStyle contentWriteCellStyle) {
        super(headWriteCellStyle, contentWriteCellStyle);
        this.c = c;
    }
    
    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        try {
            Field declaredField = c.getDeclaredField(head.getFieldName());
            ExcelStyle annotation = declaredField.getAnnotation(ExcelStyle.class);
            if(declaredField.getName().equals("addrUrl")) {
            	String addr = cell.getStringCellValue();
            	Sheet sheet = cell.getSheet();
            	Row row = cell.getRow();
                Workbook workbook = sheet.getWorkbook();
                CreationHelper helper = workbook.getCreationHelper();
                CellStyle cellStyle = workbook.createCellStyle();
                ClientAnchor clientAnchor = helper.createClientAnchor();
                Drawing patriarch = cell.getSheet().createDrawingPatriarch();
                Integer columnIndex = head.getColumnIndex();
                byte[] b = new byte[1024];		
                try {
                	b = ImageUtils.getByteByImgUrl(addr);
                	clientAnchor = new XSSFClientAnchor(4,5,6,7,columnIndex,row.getRowNum(),columnIndex-1,row.getRowNum());
				} catch (Exception e) {
					e.printStackTrace();
				}
                patriarch.createPicture(clientAnchor, workbook.addPicture(b, HSSFWorkbook.PICTURE_TYPE_PNG));
            }else{
            	if(annotation != null) {
            		Sheet sheet = cell.getSheet();
                    Workbook wb = sheet.getWorkbook();
                    CellStyle cellStyle = wb.createCellStyle();
                    Font font = wb.createFont();
                    font.setFontName(annotation.fontName());
                    font.setColor(annotation.color());
                    font.setFontHeightInPoints(annotation.fontHeightInPoints());
                    font.setBold(annotation.bold());
                    cellStyle.setFont(font);
                    cellStyle.setWrapText(annotation.wrapped());//是否自动换行
                    cellStyle.setFillBackgroundColor(annotation.fillBackgroundColor());
                    cellStyle.setBorderBottom(annotation.borderBottom());
                    cellStyle.setBorderLeft(annotation.borderLeft());
                    cellStyle.setBorderRight(annotation.borderRight());
                    cellStyle.setBorderTop(annotation.borderTop());
                    cellStyle.setAlignment(annotation.horizontalAlignment());
                    cellStyle.setVerticalAlignment(annotation.verticalAlignment());
                    cell.setCellStyle(cellStyle);
            	}else {
                    super.setContentCellStyle(cell,head,relativeRowIndex);
                }
            	
            }
        } catch (NoSuchFieldException e) {
            log.error("ExcelStyleAnnotationCellWriteHandler error{0}",e);
        }

    }

}
