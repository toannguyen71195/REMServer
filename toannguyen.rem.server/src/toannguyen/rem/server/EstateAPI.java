package toannguyen.rem.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import toannguyen.rem.dal.EstateDAL;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.server.response.TypicalAPIResponse;

@Path("/estate")
public class EstateAPI {

	@GET
	@Path("/getAll")
	public String getAllEstate() {
		TypicalAPIResponse response = new TypicalAPIResponse();
		try {
			List<EstateEntity> estateEntities = EstateDAL.getInstance().getAll();
			return response.successResponse(estateEntities, EstateEntity.ESTATE_LIST);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
}
