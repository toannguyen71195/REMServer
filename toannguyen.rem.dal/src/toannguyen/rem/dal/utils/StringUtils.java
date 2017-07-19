package toannguyen.rem.dal.utils;

import java.io.UnsupportedEncodingException;

public class StringUtils {
	
	public static String decodeUnicode(String src) {
		if (src == null) {
			return null;
		}
		String convert = "";
		try {
			convert = new String(src.getBytes("ISO-8859-1"), "UTF-8");
			return convert;
		} catch (UnsupportedEncodingException e) {
			return src;
		}
	}
}
