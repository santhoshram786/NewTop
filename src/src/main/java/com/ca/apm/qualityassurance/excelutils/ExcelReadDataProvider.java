package com.ca.apm.qualityassurance.excelutils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class ExcelReadDataProvider implements Iterator<Object[]> {
	Logger logger = Logger.getLogger(ExcelReadDataProvider.class);
	private int rowIndex = 2;
	private final ReadAtcData excelRead;
	private final String sheetName;
	private List<AtcRequestData> atcDataList = new ArrayList<AtcRequestData>();

	public ExcelReadDataProvider(String fileName, String sheetName) {
		excelRead = new ReadAtcData(fileName);
		this.sheetName = sheetName;
		this.atcDataList = excelRead.getdataListFromExcel(sheetName);
	}

	@Override
	public boolean hasNext() {
		logger.debug("row index" + rowIndex);
		logger.debug("number of rows in" + excelRead.getRowCount(sheetName));
		return (rowIndex <= excelRead.getRowCount(sheetName));
	}

	@Override
	public Object[] next() {
		return new Object[] { atcDataList.get(rowIndex - 2) };
	}
}