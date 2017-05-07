package toannguyen.rem.server.response;

import java.util.List;

import com.google.gson.JsonObject;

import toannguyen.rem.entity.Entity;

public abstract class APIResponse {

	public String unsuccessResponse(String message) {
		JsonObject jsonError = new JsonObject();
		jsonError.addProperty(Entity.STATUS_KEY, false);
		jsonError.addProperty(Entity.MESSAGE, message);
		return jsonError.toString();
	}

	public abstract String successResponse(Entity entity);

	public abstract String successResponse(List<? extends Entity> entity, String listTag);
}
