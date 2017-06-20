package toannguyen.rem.server;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import toannguyen.rem.dal.DataDAL;
import toannguyen.rem.server.response.TypicalAPIResponse;

@Path("/data")
public class DataAPI {

	@GET
	@Path("/getWard/{id}")
	public String getAllEstate(@PathParam("id") int id) {
		TypicalAPIResponse response = new TypicalAPIResponse();
		try {
			List<String> wards = DataDAL.getInstance().getWard(id);
			return response.successStringListResponse(wards, "wards");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
}

