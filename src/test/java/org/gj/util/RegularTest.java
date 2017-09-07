package org.gj.util;

import org.junit.Test;

public class RegularTest {
	@Test
	public void replace() {
		String r="sada/sda‘，".replaceAll("[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]", "_");
		System.out.println(r);
	}

}
