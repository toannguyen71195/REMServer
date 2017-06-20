package toannguyen.rem.server;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONObject;

import toannguyen.rem.dal.AppointmentDAL;
import toannguyen.rem.entity.AppointmentEntity;
import toannguyen.rem.entity.UserEntity;
import toannguyen.rem.server.response.AppointmentAPIResponse;

@Path("/appointment")
public class AppointmentAPI {

	@GET
	@Path("/getAll/{userId}")
	public String getAppointment(@PathParam("userId") int userId) {
		AppointmentAPIResponse response = new AppointmentAPIResponse();
		try {
			List<AppointmentEntity> entities = AppointmentDAL.getInstance().getAll(userId);
			return response.successResponse(entities, "appointments");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@POST
	@Path("/updateStatus")
	public String updateStatus(String json) {
		AppointmentAPIResponse response = new AppointmentAPIResponse();
		try {
			JSONObject jsonObject = new JSONObject(json);
			int id = jsonObject.getInt("ApptID");
			int status = jsonObject.getInt("Status");
			if (jsonObject.has("Note")) {
				String name = jsonObject.getString("Name");
				String note = jsonObject.getString("Note");
				return response.successResponse(AppointmentDAL.getInstance().updateStatus(id, status, name, note));
			} else {
				return response.successResponse(AppointmentDAL.getInstance().updateStatus(id, status));
			}
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/book")
	public String book(String json) {
		AppointmentAPIResponse response = new AppointmentAPIResponse();
		try {
			JSONObject object = new JSONObject(json);
			AppointmentEntity entity = new AppointmentEntity(object.getString("name"), 
					object.getString("address"), object.getString("note"), new UserEntity(object.getJSONObject("user1").getInt("id")),
					new UserEntity(object.getJSONObject("user2").getInt("id")));
			SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a dd/MM/yyyy");
			Date time = dateFormat.parse(object.getString("time"));
			entity.setTime(new Timestamp(time.getTime()));
			AppointmentDAL.getInstance().book(entity);
			return response.successEmptyResponse("Book success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
}
