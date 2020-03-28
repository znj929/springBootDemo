package com.example.demo.executor;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.executor.AbstractExcelWriteExecutor;
import com.alibaba.excel.write.metadata.holder.WriteHolder;

public class ExcelCellExecutor extends AbstractExcelWriteExecutor {
	protected WriteContext writeContext;
	
	public ExcelCellExecutor(WriteContext writeContext) {
		super(writeContext);
		this.writeContext = writeContext;
	}
	
	protected CellData converterAndSet(WriteHolder currentWriteHolder, Class clazz, Cell cell, Object value,
			ExcelContentProperty excelContentProperty) {
		if (value == null) {
			return new CellData(CellDataTypeEnum.EMPTY);
		}
		if (value instanceof String && currentWriteHolder.globalConfiguration().getAutoTrim()) {
			value = ((String) value).trim();
		}
		CellData cellData = convert(currentWriteHolder, clazz, cell, value, excelContentProperty);
		if (cellData.getFormula() != null && cellData.getFormula()) {
			cell.setCellFormula(cellData.getFormulaValue());
		}
		if (cellData.getType() == null) {
			cellData.setType(CellDataTypeEnum.EMPTY);
		}
		switch (cellData.getType()) {
		case STRING:
			cell.setCellValue(cellData.getStringValue());
			return cellData;
		case BOOLEAN:
			cell.setCellValue(cellData.getBooleanValue());
			return cellData;
		case NUMBER:
			cell.setCellValue(cellData.getNumberValue().doubleValue());
			return cellData;
		case IMAGE:
			setImageValue(cellData, cell);
			return cellData;
		case EMPTY:
			return cellData;
		default:
			throw new ExcelDataConvertException(cell.getRow().getRowNum(), cell.getColumnIndex(), cellData,
					excelContentProperty, "Not supported data:" + value + " return type:" + cell.getCellType()
							+ "at row:" + cell.getRow().getRowNum());
		}
	}

	private void setImageValue(CellData cellData, Cell cell) {
		Sheet sheet = cell.getSheet();
		int index = sheet.getWorkbook().addPicture(cellData.getImageValue(), HSSFWorkbook.PICTURE_TYPE_PNG);
		Drawing drawing = sheet.getDrawingPatriarch();
		if (drawing == null) {
			drawing = sheet.createDrawingPatriarch();
		}
		CreationHelper helper = sheet.getWorkbook().getCreationHelper();
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setDx1(0);
		anchor.setDx2(0);
		anchor.setDy1(0);
		anchor.setDy2(0);
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex() + 1);
		anchor.setRow1(cell.getRowIndex());
		anchor.setRow2(cell.getRowIndex() + 1);
		anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
		drawing.createPicture(anchor, index);
	}
}
