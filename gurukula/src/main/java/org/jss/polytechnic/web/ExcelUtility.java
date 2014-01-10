package org.jss.polytechnic.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jss.polytechnic.bean.BoardResult;
import org.jss.polytechnic.bean.Result;

public class ExcelUtility {

	public static List<? extends Result> parseResultSheet(final File file) {

		Workbook wb = null;

		try {
			if (file.getName().endsWith("xlsx")) {
				wb = new XSSFWorkbook(new FileInputStream(file));
			} else {
				wb = new HSSFWorkbook(new FileInputStream(file));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<BoardResult> brResultList = null;

		Sheet s = wb.getSheetAt(wb.getActiveSheetIndex());

		Iterator<Row> it = s.rowIterator();

		boolean hasData = false;

		while (it.hasNext()) {
			Row r = it.next();

			Cell c = r.getCell(0, Row.CREATE_NULL_AS_BLANK);

			if (!hasData
					&& StringUtils.equalsIgnoreCase(c.getStringCellValue(),
							"SNo.")) {
				hasData = true;
				brResultList = new LinkedList<BoardResult>();
				continue;
			}

			if (!hasData)
				continue;

			BoardResult br = new BoardResult();
			br.setSlNo(NumberUtils.toInt(getStringCellValue(c), 0));
			if (br.getSlNo() == 0) {
				break;
			}

			int i = 1;

			br.setRegNo(getStringCellValue(r.getCell(i++,
					Row.CREATE_NULL_AS_BLANK)));

			br.setStudentName(getStringCellValue(r.getCell(i++,
					Row.CREATE_NULL_AS_BLANK)));

			br.setSem(getStringCellValue(r.getCell(i++,
					Row.CREATE_NULL_AS_BLANK)));

			int[] ex = br.getEx();
			for (int j = 0; j < ex.length;) {
				ex[j++] = NumberUtils.toInt(getStringCellValue(r.getCell(i++,
						Row.CREATE_NULL_AS_BLANK)), 0);
			}

			br.setExTotal(NumberUtils.toInt(
					getStringCellValue(r.getCell(i++, Row.CREATE_NULL_AS_BLANK)),
					0));

			int[] in = br.getIn();
			for (int j = 0; j < in.length;) {
				in[j++] = NumberUtils.toInt(getStringCellValue(r.getCell(i++,
						Row.CREATE_NULL_AS_BLANK)), 0);
			}

			br.setInTotal(NumberUtils.toInt(
					getStringCellValue(r.getCell(i++, Row.CREATE_NULL_AS_BLANK)),
					0));

			br.setTotal(NumberUtils.toInt(
					getStringCellValue(r.getCell(i++, Row.CREATE_NULL_AS_BLANK)),
					0));

			br.setResult(getStringCellValue(r.getCell(i++,
					Row.CREATE_NULL_AS_BLANK)));

			String[] qp = br.getQp();
			for (int j = 0; j < qp.length;) {
				qp[j++] = getStringCellValue(r.getCell(i++,
						Row.CREATE_NULL_AS_BLANK));
			}

			brResultList.add(br);

		}

		return brResultList;
	}

	private static String getStringCellValue(Cell c) {
		c.setCellType(Cell.CELL_TYPE_STRING);
		return c.getStringCellValue();
	}
}
