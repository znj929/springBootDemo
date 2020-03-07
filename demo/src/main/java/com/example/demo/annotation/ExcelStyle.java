package com.example.demo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;


@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelStyle {
	/**
	 * 默认微软雅黑
	 * @return
	 */
	String fontName() default "微软雅黑";
	
	/**
	 * 背景颜色 默认 1 就是 white IndexedColors.WHITE.getIndex()
	 * @return
	 */
	short fillBackgroundColor() default 1;
	
	/**
	 * 设置字体颜色 默认8 黑色 IndexedColors.BLACK.getIndex()
	 * BLACK
	 * @return
	 */
	short color() default 8;
	
	/**
	 * 设置字体粗体
	 * @return
	 */
	boolean bold() default false;
	
	/**
	 * 设置自动换行，默认为true
	 * @return
	 */
	boolean wrapped() default true;
	
	/**
	 * 字体大小 默认12
	 * @return
	 */
    short fontHeightInPoints() default 12;
    
    /**
     * 单元格对齐方式，默认左对齐
     * @return
     */
    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.LEFT;
    /**
     * 单元格垂直对齐类型，默认居中
     * @return
     */
    VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;
    
    /**
     * 设置下边框样式，默认NONE
     * Set the type of border to use for the bottom border of the cell
     */
    BorderStyle borderBottom() default BorderStyle.NONE;
    /**
     * 设置左边框样式，默认NONE
     * Set the type of border to use for the left border of the cell
     */
    BorderStyle borderLeft() default BorderStyle.NONE;
    /**
     * 设置右边框样式，默认NONE
     * Set the type of border to use for the right border of the cell
     */
    BorderStyle borderRight() default BorderStyle.NONE;
    /**
     * 设置上边框样式，默认NONE
     * Set the type of border to use for the top border of the cell
     */
    BorderStyle borderTop() default BorderStyle.NONE;

    
}
