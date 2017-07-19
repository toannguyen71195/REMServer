package toannguyen.rem.dal.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SearchUtils {

	static String district = StringUtils.decodeUnicode("quận");
	static List<String> districtList = new ArrayList<>();
	
	public static String getDistrict(String query) {
		StringTokenizer token = new StringTokenizer(query);
		while (token.hasMoreTokens()) {
			if (token.nextToken().equalsIgnoreCase(district)) {
				return token.nextToken();
			}
		}
		
		return null;
	}
}
