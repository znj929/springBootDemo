package com.example.demo.handler;

import java.lang.reflect.Field;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.demo.annotation.ExcelStyle;

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
            if (annotation != null) {
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
                sheet.autoSizeColumn(1, true);
            }else {
                super.setContentCellStyle(cell,head,relativeRowIndex);
            }
        } catch (NoSuchFieldException e) {
            log.error("ExcelStyleAnnotationCellWriteHandler error{0}",e);
        }

    }

}
