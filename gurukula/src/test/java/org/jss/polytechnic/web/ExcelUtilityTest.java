package org.jss.polytechnic.web;

import java.io.File;
import java.util.List;

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
		List<? extends Object> resultList = ExcelUtility.parseResultSheet(file);
		Assert.assertNotNull(resultList);
		Assert.assertTrue(resultList.size() > 100);
	}
}
