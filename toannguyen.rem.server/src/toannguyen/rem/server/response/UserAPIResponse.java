package toannguyen.rem.server.response;

import java.util.List;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.json.EntityToJsonConverter;

public class UserAPIResponse extends APIResponse {

	@Override
	public String successResponse(Entity entity) {
		JsonElement jsonUser = EntityToJsonConverter.convertEntityToJson(entity);
		JsonObject jsonUserConvert = (JsonObject) jsonUser;
		jsonUserConvert.addProperty(Entity.STATUS_KEY, true);
		return jsonUserConvert.toString();
	}

	@Override
	public String successResponse(List<? extends Entity> entity) {
		JsonElement jsonElement = EntityToJsonConverter.convertEntityListToJson(entity);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(Entity.STATUS_KEY, true);
		return jsonObject.toString();
	}
}
