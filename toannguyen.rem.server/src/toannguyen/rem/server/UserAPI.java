package toannguyen.rem.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import toannguyen.rem.dal.UserDAL;
import toannguyen.rem.dal.database.UserDatabaseHelper;
import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.UserEntity;
import toannguyen.rem.server.response.UserAPIResponse;

@Path("/user")
public final class UserAPI {

	@GET
	@Path("/getAllUser")
	public String getAllUser() {
		UserAPIResponse response = new UserAPIResponse();
		try {
			List<Entity> users = UserDAL.getInstance().getAll(new UserDatabaseHelper());
			return response.successResponse(users, UserEntity.USER_LIST);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
}
