package toannguyen.rem.entity.json;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import toannguyen.rem.entity.Entity;

public class JsonToEntityConverter {
	private JsonToEntityConverter() {
		// hide constructor
	}

	public static <T extends Entity> T convertJsonStringToEntity(String jsonString, Type type) {
		Gson gson = new GsonBuilder()
				   .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
		return gson.fromJson(jsonString, type);
	}

	public static <T extends Entity> T convertJsonToEntity(JsonElement jsonElement, Type type) {
		Gson gson = new GsonBuilder()
				   .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
		return gson.fromJson(jsonElement, type);
	}

	public static <T extends Entity> List<T> convertJsonArrayToEntityList(JsonArray jsonArray, Type type) {
		Gson gson = new GsonBuilder()
				   .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
		return gson.fromJson(jsonArray, type);
	}

}
