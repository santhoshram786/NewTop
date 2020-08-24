package com.ca.apm.qualityassurance.excelutils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ParseExcelCellData {
	protected String getStringValueFromCell(Cell currentCell) {
		if (currentCell != null) {
			if (currentCell.getCellType() == CellType.STRING) {
				return currentCell.getStringCellValue();
			} else if (currentCell.getCellType() == CellType.NUMERIC) {
				return getNumeric(currentCell.getNumericCellValue());

			}
		}
		return null;
	}

	protected String getNumeric(double numericCellValue) {
		DecimalFormat df = new DecimalFormat("0.#######");
		return df.format(numericCellValue);
	}

	protected long getlongValueFromCell(Cell currentCell) {
		if (currentCell != null) {
			if (currentCell.getCellType() == CellType.NUMERIC) {
				return (long) currentCell.getNumericCellValue();
			}
		}
		return 0L;
	}

	protected Boolean getbooleanValueFromCell(Cell currentCell) {
		if (currentCell != null) {
			if (currentCell.getStringCellValue().equals("false")) {
				return false;
			}
		}
		return true;
	}

	protected List<String> getAttributeList(String attrNmae) {
		List<String> attNames = new ArrayList<String>();
		if (attrNmae != null) {
			if (attrNmae.contains(",")) {
				String[] attrList = attrNmae.split(",");
				for (String a : attrList) {
					attNames.add(a);
				}
			} else
				attNames.add(attrNmae);

		}
		return attNames;
	}

	protected static Map<String, List<String>> getMap(String stringCellValue) {
		Map<String, List<String>> attributeListMap = new HashMap<String, List<String>>();
		if (stringCellValue != null) {
			String[] attrList = stringCellValue.split(",");
			for (String attri : attrList) {
				String[] attr = attri.split("=");
				if (attr.length == 2) {
					attributeListMap.put(attr[0],
							Arrays.asList(attr[1].split("&")));
				} else {
					System.out.println("The foramt is wrong please check for "
							+ attr);
				}
			}
		}
		return attributeListMap;
	}
}
