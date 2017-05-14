package toannguyen.rem.server.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.UserEntity;
import toannguyen.rem.entity.json.EntityToJsonConverter;

public class UserAPIResponse extends TypicalAPIResponse {

	public String loginSuccess(UserEntity entity, String token) {
		JsonElement jsonUser = EntityToJsonConverter.convertEntityToJson(entity);
		JsonObject jsonObject = (JsonObject) jsonUser;
		jsonObject.addProperty(Entity.STATUS_KEY, true);
		jsonObject.addProperty(UserEntity.TOKEN, token);
		return jsonObject.toString();
	}
}
