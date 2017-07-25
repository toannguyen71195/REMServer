package toannguyen.rem.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import toannguyen.rem.dal.EstateDAL;
import toannguyen.rem.dal.UserDAL;
import toannguyen.rem.dal.mapping.NoteColumn;
import toannguyen.rem.dal.mapping.UserColumn;
import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.NotificationEntity;
import toannguyen.rem.entity.PhotoEntity;
import toannguyen.rem.entity.UserEntity;
import toannguyen.rem.entity.json.ErrorMessage;
import toannguyen.rem.entity.json.JsonToEntityConverter;
import toannguyen.rem.server.response.EstateAPIResponse;
import toannguyen.rem.server.response.UserAPIResponse;

@Path("/user")
public final class UserAPI {

	@GET
	@Path("/getAll")
	public String getAllUser() {
		UserAPIResponse response = new UserAPIResponse();
		try {
			List<Entity> users = UserDAL.getInstance().getAll();
			return response.successResponse(users, UserEntity.USER_LIST);
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@POST
	@Path("/login")
	public String logIn(String json) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			JSONObject jsonObject = new JSONObject(json);
			String username = jsonObject.getString(UserColumn.USER_NAME.getColumnName());
			String password = jsonObject.getString(UserColumn.PASSWORD.getColumnName());
			UserEntity userEntity = UserDAL.getInstance().logIn(username, password);
			if (userEntity != null) {
				String token = UserDAL.getInstance().createLoginToken(userEntity.getId());
				int estateNum;
				if (userEntity.getTypeId() == 1) {
					estateNum = EstateDAL.getInstance().getInterestedEstateNumber(userEntity.getId());
				} else {
					estateNum = EstateDAL.getInstance().getEstateNumberOwner(userEntity.getId());
				}
				return response.loginSuccess(userEntity, token, estateNum);
			} else {
				return response.unsuccessResponse(ErrorMessage.LOGIN_ERROR);
			}
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/login/{token}")
	public String logInWithToken(@PathParam("token") String token) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			UserEntity userEntity = UserDAL.getInstance().logIn(token);
			if (userEntity != null) {
				int estateNum;
				if (userEntity.getTypeId() == 1) {
					estateNum = EstateDAL.getInstance().getInterestedEstateNumber(userEntity.getId());
				} else {
					estateNum = EstateDAL.getInstance().getEstateNumberOwner(userEntity.getId());
				}
				return response.loginSuccess(userEntity, token, estateNum);
			} else {
				return response.unsuccessResponse(ErrorMessage.TOKEN_ERROR);
			}
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@POST
	@Path("/register")
	public String register(String json) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			JSONObject jsonObject = new JSONObject(json);
			UserEntity entity = new UserEntity(0, jsonObject.getString(UserColumn.USER_NAME.getColumnName()),
					jsonObject.getString(UserColumn.FULL_NAME.getColumnName()),
					jsonObject.getString(UserColumn.EMAIL.getColumnName()),
					jsonObject.getString(UserColumn.PHONE.getColumnName()),
					jsonObject.getString(UserColumn.PASSWORD.getColumnName()), "",
					jsonObject.getInt(UserColumn.USER_TYPE.getColumnName()),
					"");
			UserEntity userEntity = UserDAL.getInstance().register(entity);
			if (userEntity != null) {
				String token = UserDAL.getInstance().createLoginToken(userEntity.getId());
				return response.loginSuccess(userEntity, token, 0);
			} else {
				return response.unsuccessResponse("Server unknown error!");
			}
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@POST
	@Path("updateNote")
	public String updateNote(String json) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			JSONObject jsonObject = new JSONObject(json);
			int userId = jsonObject.getInt(NoteColumn.USER_ID.getColumnName());
			int estateId = jsonObject.getInt(NoteColumn.ESTATE_ID.getColumnName());
			String note = jsonObject.getString(NoteColumn.NOTE.getColumnName());
			UserDAL.getInstance().updateNote(userId, estateId, note);
			return response.successEmptyResponse("Update success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("getNote/{userId}-{estateId}")
	public String getNote(@PathParam("userId") int userId, @PathParam("estateId") int estateId) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			String note = UserDAL.getInstance().getNote(userId, estateId);
			return response.successStringResponse("note", note);
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@POST
	@Path("upPhotoNote")
	public String upPhotoList(String json) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			JSONObject jsonObject = new JSONObject(json);
			int uid = jsonObject.getInt("UserID");
			int eid = jsonObject.getInt("EstateID");
			List<PhotoEntity> entities = new ArrayList<>();
			JSONArray array = jsonObject.getJSONArray("photos");
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				PhotoEntity entity = JsonToEntityConverter.convertJsonStringToEntity(object.toString(),
						PhotoEntity.class);
				entities.add(entity);
			}
			UserDAL.getInstance().postPhotos(uid, eid, entities);
			return response.successEmptyResponse("Update success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("getPhotoNote/{userId}-{estateId}")
	public String getPhotoList(@PathParam("userId") int userId, @PathParam("estateId") int estateId) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			List<PhotoEntity> photoEntities = UserDAL.getInstance().getPhotos(userId, estateId);
			return response.successResponse(photoEntities, "photos");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getInterested/{userId}")
	public String getInterestedEstate(@PathParam("userId") int userId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			List<EstateEntity> estateEntities = UserDAL.getInstance().getInterestedEstate(userId);
			List<JsonObject> jsonObjects = new ArrayList<>();
			for (EstateEntity entity : estateEntities) {
				int rate = EstateDAL.getInstance().getRate(userId, entity.getId());
				jsonObjects.add(response.successResponseInterested(entity, rate));
			}
			return response.successResponseInterestedList(jsonObjects);
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/setInterested/{userId}-{estateId}-{rate}")
	public String setInterested(@PathParam("estateId") int estateId, @PathParam("userId") int userId,
			@PathParam("rate") int rate) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			UserDAL.getInstance().setInterestedEstate(userId, estateId, rate);
			return response.successEmptyResponse("Update success");
		} catch (ClassNotFoundException | SQLException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/checkInterested/{userId}-{estateId}")
	public String setInterested(@PathParam("estateId") int estateId, @PathParam("userId") int userId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			boolean b = UserDAL.getInstance().checkInterestedEstate(userId, estateId);
			return response.successBooleanResponse("interested", b);
		} catch (ClassNotFoundException | SQLException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/updateRequest/{userId}-{estateId}")
	public String updateRequest(@PathParam("userId") int userId, @PathParam("estateId") int estateId) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			UserDAL.getInstance().updateRequest(userId, estateId);
			return response.successEmptyResponse("Update success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/getNoti/{userId}")
	public String getNoti(@PathParam("userId") int userId) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			List<NotificationEntity> entities =	UserDAL.getInstance().getNoti(userId);
			return response.successResponse(entities, "notifications");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/deleteNoti/{notiId}")
	public String deleteNoti(@PathParam("notiId") int notiId) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			UserDAL.getInstance().deleteNoti(notiId);
			return response.successEmptyResponse("Update success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/reportSpam/{userId}-{notiId}")
	public String reportSpam(@PathParam("userId") int userId, @PathParam("notiId") int notiId) {
		UserAPIResponse response = new UserAPIResponse();
		try {
			UserDAL.getInstance().reportSpam(userId, notiId);
			return response.successEmptyResponse("Report success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	// @GET
	// @Path("/setVisited/{userId}-{estateId}")
	// public String setVisited(@PathParam("estateId") int estateId,
	// @PathParam("userId") int userId) {
	// EstateAPIResponse response = new EstateAPIResponse();
	// try {
	// UserDAL.getInstance().setVisitedEstate(userId, estateId);
	// return response.successEmptyResponse("Update success");
	// } catch (ClassNotFoundException | SQLException e) {
	// return response.unsuccessResponse(e.getMessage());
	// }
	// return null;
	// }

	// @GET
	// @Path("/search/{query}-{offset}-{range}")
	// public String setVisited(@PathParam("query") String query,
	// @PathParam("offset") int offset,
	// @PathParam("range") int range) {
	// UserAPIResponse response = new UserAPIResponse();
	// try {
	// List<UserEntity> entities = UserDAL.getInstance().searchUser(query,
	// offset, range);
	// return response.successResponse(entities, "users");
	// } catch (ClassNotFoundException | SQLException | IOException e) {
	// return response.unsuccessResponse(e.getMessage());
	// }
	// }
}
