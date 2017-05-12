package toannguyen.rem.entity.json;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import toannguyen.rem.entity.Entity;

public class EntityToJsonConverter {
	private EntityToJsonConverter() {
		// hide constructor
	}

	public static <T extends Entity> JsonElement convertEntityToJson(T entity) {
		return new Gson().toJsonTree(entity, new TypeToken<T>() {}.getType());
	}

	public static JsonElement convertEntityListToJson(List<? extends Entity> entityList) {
		return new Gson().toJsonTree(entityList, new TypeToken<List<Entity>>() {}.getType());
	}
}