package org.jss.polytechnic.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
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
import org.jss.polytechnic.bean.Student;

public class ExcelUtility {

	public static List<BoardResult> parseResultSheet(final InputStream is,
			boolean isXlsx) throws IOException {

		Workbook wb = null;

		if (isXlsx) {
			wb = new XSSFWorkbook(is);
		} else {
			wb = new HSSFWorkbook(is);
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

				int lastCellNum = r.getLastCellNum();

				if (lastCellNum < 35) {
					throw new InvalidObjectException("File has less columns");
				}
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

			String[] ex = br.getEx();
			for (int j = 0; j < ex.length;) {
				String marks = getStringCellValue(r.getCell(i++,
						Row.CREATE_NULL_AS_BLANK));
				if (!(NumberUtils.isNumber(marks))) {
					if (!(marks.equalsIgnoreCase("AB")
							|| marks.equalsIgnoreCase("XX") || marks.isEmpty())) {
						marks = "ZZ";
					}
				}
				ex[j++] = marks;
			}

			br.setEx(ex);

			br.setExTotal(NumberUtils.toInt(
					getStringCellValue(r.getCell(i++, Row.CREATE_NULL_AS_BLANK)),
					0));

			String[] in = br.getIn();
			for (int j = 0; j < in.length;) {
				String marks = getStringCellValue(r.getCell(i++,
						Row.CREATE_NULL_AS_BLANK));
				if (!NumberUtils.isNumber(marks)) {
					if (!(marks.equalsIgnoreCase("AB")
							|| marks.equalsIgnoreCase("XX") || marks.isEmpty())) {
						marks = "ZZ";
					}
				}
				in[j++] = marks;

				// in[j++] = getStringCellValue(r.getCell(i++,
				// Row.CREATE_NULL_AS_BLANK));
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

		if (!hasData) {
			System.out.println("File has no header");
			throw new InvalidObjectException("File has no header");
		}

		return brResultList;
	}

	public static List<Student> parsePersonalSheet(final InputStream is,
			boolean isXlsx) throws IOException {

		Workbook wb = null;

		if (isXlsx) {
			wb = new XSSFWorkbook(is);
		} else {
			wb = new HSSFWorkbook(is);
		}

		List<Student> studentList = null;

		Sheet s = wb.getSheetAt(wb.getActiveSheetIndex());

		Iterator<Row> it = s.rowIterator();

		boolean hasData = false;

		while (it.hasNext()) {
			Row r = it.next();

			Cell c = r.getCell(0, Row.CREATE_NULL_AS_BLANK);

			if (!hasData
					&& StringUtils.equalsIgnoreCase(c.getStringCellValue(),
							"SLNo.")) {

				int lastCellNum = r.getLastCellNum();

				if (lastCellNum < 7) {
					throw new InvalidObjectException("File has less columns");
				}
				hasData = true;
				studentList = new LinkedList<Student>();
				continue;
			}

			if (!hasData)
				continue;

			int i = 1;

			Student student = new Student();
			student.setReg_no(getStringCellValue(r.getCell(i++,
					Row.CREATE_NULL_AS_BLANK)));
			if (StringUtils.isEmpty(student.getReg_no())) {
				break;
			}

			student.setName(getStringCellValue(r.getCell(i++,
					Row.CREATE_NULL_AS_BLANK)));

			student.setFather(getStringCellValue(r.getCell(i++,
					Row.CREATE_NULL_AS_BLANK)));

			student.setMother(getStringCellValue(r.getCell(i++,
					Row.CREATE_NULL_AS_BLANK)));

			student.setGender(getStringCellValue(r.getCell(i++,
					Row.CREATE_NULL_AS_BLANK)));

			student.setCategory(getStringCellValue(r.getCell(i++,
					Row.CREATE_NULL_AS_BLANK)));

			studentList.add(student);

		}

		if (!hasData) {
			System.out.println("File has no header");
			throw new InvalidObjectException("File has no header");
		}

		return studentList;

	}

	private static String getStringCellValue(Cell c) {
		c.setCellType(Cell.CELL_TYPE_STRING);
		return StringUtils.trimToEmpty(c.getStringCellValue());
	}
}
