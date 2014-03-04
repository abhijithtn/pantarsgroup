package org.jss.polytechnic.web;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestNumberUtils {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsDigits() {
		String s = "58";
		Assert.assertTrue(NumberUtils.isDigits(s));
	}

	@Test
	public void testIsNumber() {
		String s = "58";
		Assert.assertTrue(NumberUtils.isNumber(s));
	}

}
