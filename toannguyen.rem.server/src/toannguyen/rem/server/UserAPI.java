package toannguyen.rem.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONObject;

import toannguyen.rem.dal.UserDAL;
import toannguyen.rem.dal.mapping.UserColumn;
import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.UserEntity;
import toannguyen.rem.entity.json.ErrorMessage;
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
}
