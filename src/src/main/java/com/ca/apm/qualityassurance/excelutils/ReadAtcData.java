package com.ca.apm.qualityassurance.excelutils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ca.apm.qualityassurance.utils.FilePathUtils;

public class ReadAtcData extends ParseExcelCellData {
	public String path;
	public InputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;

	public ReadAtcData(String fileName) {
		try {
			fis = FilePathUtils.getjarFilePath(fileName);
			workbook = new XSSFWorkbook(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount(final String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			XSSFSheet sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

	}

	public List<AtcRequestData> getdataListFromExcel(String sheetName) {
		List<AtcRequestData> dataList = new ArrayList<AtcRequestData>();
		try {
			Sheet datatypeSheet = workbook.getSheet(sheetName);
			Iterator<Row> rowIter = datatypeSheet.iterator();
			if (rowIter.hasNext()) {
				rowIter.next();
			}
			while (rowIter.hasNext()) {
				AtcRequestData data = new AtcRequestData();
				Row currentRow = rowIter.next();
				data.setTestcaseName(getStringValueFromCell(currentRow
						.getCell(0)));
				data.setRequestId(getStringValueFromCell(currentRow.getCell(1)));
				data.setSourceER(getStringValueFromCell(currentRow.getCell(2)));
				data.setAttributeER(getStringValueFromCell(currentRow
						.getCell(3)));
				data.setSourceName(getStringValueFromCell(currentRow.getCell(4)));
				data.setSourceNames(getAttributeList(getStringValueFromCell(currentRow
						.getCell(4))));
				data.setAttributeName(getStringValueFromCell(currentRow
						.getCell(5)));
				data.setAttributeNames(getAttributeList(getStringValueFromCell(currentRow
						.getCell(5))));
				data.setRangeSize(getlongValueFromCell(currentRow.getCell(6)));
				data.setFrequency(getlongValueFromCell(currentRow.getCell(7)));
				data.setLimit(getStringValueFromCell(currentRow.getCell(8)));
				data.setIncludeAggregateTimeSeries(getbooleanValueFromCell(currentRow
						.getCell(9)));
				data.setOperator(getStringValueFromCell(currentRow.getCell(10)));
				data.setNameAttr(getStringValueFromCell(currentRow.getCell(11)));
				data.setNameAttrValues(getAttributeList(getStringValueFromCell(currentRow
						.getCell(12))));
				data.setVerficationValues(getAttributeList(getStringValueFromCell(currentRow
						.getCell(13))));
				data.setVerifySource(getAttributeList(getStringValueFromCell(currentRow
						.getCell(13))));
				data.setVerifyAttribute(getAttributeList(getStringValueFromCell(currentRow
						.getCell(14))));
				// System.out.println("Adding:"+data);
				dataList.add(data);
			}

			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}

}
