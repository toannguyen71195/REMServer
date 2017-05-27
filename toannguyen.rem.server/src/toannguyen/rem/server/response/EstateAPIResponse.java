package toannguyen.rem.server.response;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.json.EntityToJsonConverter;

public class EstateAPIResponse extends TypicalAPIResponse {
	
	public JsonObject successResponseInterested(EstateEntity entity, int rate) {
		JsonElement jsonUser = EntityToJsonConverter.convertEntityToJson(entity);
		JsonObject jsonObject = (JsonObject) jsonUser;
		jsonObject.addProperty(EstateEntity.RATE, true);
		return jsonObject;
	}

	public String successResponseInterestedList(List<JsonObject> jsonObjects) {
		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = new JsonObject();
		for (JsonObject object : jsonObjects) {
			jsonArray.add(object);
		}
		jsonObject.add(EstateEntity.ESTATE_LIST, jsonArray);
		jsonObject.addProperty(Entity.STATUS_KEY, true);
		return jsonObject.toString();
	}
}
