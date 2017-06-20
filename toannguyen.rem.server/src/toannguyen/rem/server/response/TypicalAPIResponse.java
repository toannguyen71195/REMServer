package toannguyen.rem.server.response;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.json.EntityToJsonConverter;

public class TypicalAPIResponse extends APIResponse {

	@Override
	public String successResponse(Entity entity) {
		JsonElement jsonUser = EntityToJsonConverter.convertEntityToJson(entity);
		JsonObject jsonUserConvert = (JsonObject) jsonUser;
		jsonUserConvert.addProperty(Entity.STATUS_KEY, true);
		return jsonUserConvert.toString();
	}

	@Override
	public String successResponse(List<? extends Entity> entities, String listTag) {
		JsonElement jsonElement = EntityToJsonConverter.convertEntityListToJson(entities);
		JsonObject jsonObject = new JsonObject();
		jsonObject.add(listTag, jsonElement);
		jsonObject.addProperty(Entity.STATUS_KEY, true);
		return jsonObject.toString();
	}

	public String successStringListResponse(List<String> wards, String tag) {
		// TODO Auto-generated method stub
		JsonArray array = new JsonArray();
		for (String s : wards) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("name", s);
			array.add(jsonObject);
		}
		JsonObject rs = new JsonObject();
		rs.add(tag, array);
		return rs.toString();
	}

}
