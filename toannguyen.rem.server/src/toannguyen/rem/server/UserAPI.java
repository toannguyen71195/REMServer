package toannguyen.rem.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONObject;

import com.google.gson.JsonObject;

import toannguyen.rem.dal.EstateDAL;
import toannguyen.rem.dal.UserDAL;
import toannguyen.rem.dal.mapping.NoteColumn;
import toannguyen.rem.dal.mapping.UserColumn;
import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.UserEntity;
import toannguyen.rem.entity.json.ErrorMessage;
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
				return response.loginSuccess(userEntity, token);
			} else {
				return response.unsuccessResponse(ErrorMessage.LOGIN_ERROR);
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
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
				return response.successResponse(userEntity);
			} else {
				return response.unsuccessResponse(ErrorMessage.TOKEN_ERROR);
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
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
					jsonObject.getInt(UserColumn.USER_TYPE.getColumnName()));
			UserEntity userEntity = UserDAL.getInstance().register(entity);
			if (userEntity != null) {
				String token = UserDAL.getInstance().createLoginToken(userEntity.getId());
				return response.loginSuccess(userEntity, token);
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
			String userId = jsonObject.getString(NoteColumn.USER_ID.getColumnName());
			String estateId = jsonObject.getString(NoteColumn.ESTATE_ID.getColumnName());
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
	public String setInterested(@PathParam("estateId") int estateId, @PathParam("userId") int userId, @PathParam("rate") int rate) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			UserDAL.getInstance().setInterestedEstate(userId, estateId, rate);
			return response.successEmptyResponse("Update success");
		} catch (ClassNotFoundException | SQLException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/setVisited/{userId}-{estateId}")
	public String setVisited(@PathParam("estateId") int estateId, @PathParam("userId") int userId) {
//		EstateAPIResponse response = new EstateAPIResponse();
//		try {
//			UserDAL.getInstance().setVisitedEstate(userId, estateId);
//			return response.successEmptyResponse("Update success");
//		} catch (ClassNotFoundException | SQLException e) {
//			return response.unsuccessResponse(e.getMessage());
//		}
		return null;
	}
	
//	select distinct * from
//	(select * from users where FullName like 'query%' 
//							or UserName like 'query%' 
//							or Email like 'query%'
//							or Phone like 'query%' union all
//	select * from users where (Fullname like '%query%' and FullName not like 'query%')
//							or (UserName like '%query%' and UserName not like 'query%')
//							or (Email like '%query%' and Email not like 'query%')
//							or (Phone like '%query%' and Phone not like 'query%')
//	) as us
}
