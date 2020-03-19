package com.example.demo.handler;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ExcelCallStyleHandler implements CellWriteHandler{

	@Override
	public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
			Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
		// TODO Auto-generated method stub
		log.info("==============1");
	}

	@Override
	public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
			Head head, Integer relativeRowIndex, Boolean isHead) {
		// TODO Auto-generated method stub
		log.info("==============2");
	}

	@Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
			List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
		// TODO Auto-generated method stub
		log.info("==============3");
	}


}
