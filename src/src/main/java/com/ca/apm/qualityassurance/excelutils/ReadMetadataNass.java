package com.ca.apm.qualityassurance.excelutils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ca.apm.qualityassurance.utils.Constants;
import com.ca.apm.qualityassurance.utils.FilePathUtils;

public class ReadMetadataNass extends ParseExcelCellData {

	public List<MetadataNassData> getdataListFromExcel() {
		List<MetadataNassData> dataList = new ArrayList<MetadataNassData>();
		InputStream getinputStream = null;
		Workbook workbook = null;
		try {
			getinputStream = FilePathUtils
					.getjarFilePath(Constants.EXCEL_SHEET_NAME);
			workbook = new XSSFWorkbook(getinputStream);
			Sheet datatypeSheet = workbook
					.getSheet(Constants.META_DATA_SHEET_NAME);
			Iterator<Row> rowIter = datatypeSheet.iterator();
			if (rowIter.hasNext()) {
				rowIter.next();
			}
			while (rowIter.hasNext()) {
				MetadataNassData data = new MetadataNassData();
				Row currentRow = rowIter.next();
				data.setSourceName(getStringValueFromCell(currentRow.getCell(0)));
				data.setAttributeName(getStringValueFromCell(currentRow
						.getCell(1)));
				data.setMetricType(getStringValueFromCell(currentRow.getCell(2)));
				data.setMetricAttributes(getMap(getStringValueFromCell(currentRow
						.getCell(3))));
				data.setMin(getStringValueFromCell(currentRow.getCell(4)));
				data.setMax(getStringValueFromCell(currentRow.getCell(5)));
				data.setValue(getStringValueFromCell(currentRow.getCell(6)));
				data.setCount(getStringValueFromCell(currentRow.getCell(7)));
				dataList.add(data);
			}
			getinputStream.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}

}
