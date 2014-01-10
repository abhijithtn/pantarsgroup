package org.jss.polytechnic.web;

import java.io.File;
import java.util.List;

import org.jss.polytechnic.bean.BoardResult;
import org.jss.polytechnic.bean.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExcelUtilityTest {

	File file;

	@Before
	public final void setUp() {
		file = new File("src/test/resources/me.xlsx");
		System.out.println(file.getAbsolutePath());
		Assert.assertNotNull(file);
	}

	@Test
	public final void testParseResultSheet() {
		List<? extends Result> resultList = ExcelUtility.parseResultSheet(file);
		Assert.assertNotNull(resultList);
		for (Result r : resultList) {
			BoardResult br = (BoardResult) r;
			System.out.println(br);
		}
	}
}
