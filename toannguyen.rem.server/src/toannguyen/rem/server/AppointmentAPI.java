package toannguyen.rem.server;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import toannguyen.rem.dal.AppointmentDAL;
import toannguyen.rem.entity.AppointmentEntity;
import toannguyen.rem.server.response.AppointmentAPIResponse;

@Path("/appointment")
public class AppointmentAPI {

//	@GET
//	@Path("/getAll/{userId}")
//	public String getAppointment(@PathParam("userId") int userId) {
//		AppointmentAPIResponse response = new AppointmentAPIResponse();
//		try {
//			List<AppointmentEntity> entities = AppointmentDAL.getInstance().getAll(userId);
//			return response.successResponse(entities, "appointments");
//		} catch (Exception e) {
//			return response.unsuccessResponse(e.getMessage());
//		}
//	}
}
