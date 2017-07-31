package toannguyen.rem.dal.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SearchUtils {

	static String district = StringUtils.decodeUnicode("quận");
	static List<String> districtList = new ArrayList<>();
	static List<String> typeList = new ArrayList<>();
	
	private static String getDistrict(String query) {
		StringTokenizer token = new StringTokenizer(query);
		while (token.hasMoreTokens()) {
			if (token.nextToken().equalsIgnoreCase("quận")) {
				String t = token.nextToken();
				try {
					if (Integer.valueOf(t) != 0) {
						return t;
					} else {
						return t + " " + token.nextToken();
					}
				} catch (Exception e) {
					return t + " " + token.nextToken();
				}
			}
		}
		return "";
	}
	
	public static String getDistrictFromQuery(String query) {
		String d = getDistrict(query);
		if (d.isEmpty()) {
			return "";
		}
		for (int i = 0; i < districtList.size(); ++i) {
			if (d.equalsIgnoreCase(districtList.get(i))) {
				return districtList.get(i);
			}
		}
		return "";
	}
	
	public static String getEstateTypeFromQuery(String query) {
		for (int i = 0; i < typeList.size(); ++i) {
			if (query.contains(typeList.get(i))) {
				return typeList.get(i);
			}
		}
		return "";
	}
}
