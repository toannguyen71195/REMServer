package toannguyen.rem.server;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.JsonObject;

import toannguyen.rem.dal.EstateDAL;
import toannguyen.rem.entity.EstateDetailEntity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.server.response.EstateAPIResponse;
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
	
	@GET
	@Path("/getByOwnerID/{userId}")
	public String getByOwnerID(@PathParam("userId") int id) {
		TypicalAPIResponse response = new TypicalAPIResponse();
		try {
			List<EstateEntity> estateEntity = EstateDAL.getInstance().getEstateByOwnerID(id);
			return response.successResponse(estateEntity, EstateEntity.ESTATE_LIST);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/getNewEstate/{count}")
	public String getRecentEstate(@PathParam("count") int count) {
		TypicalAPIResponse response = new TypicalAPIResponse();
		try {
			List<EstateEntity> estateEntities = EstateDAL.getInstance().getNewEstate(count);
			return response.successResponse(estateEntities, EstateEntity.ESTATE_LIST);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/getEstateDetail/{estateId}")
	public String getEstateDetail(@PathParam("estateId") int estateId) {
		TypicalAPIResponse response = new TypicalAPIResponse();
		try {
			EstateDetailEntity estateDetailEntity = EstateDAL.getInstance().getEstateDetail(estateId);
			return response.successResponse(estateDetailEntity);
		} catch (ClassNotFoundException | SQLException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/getInterestedEstate/{userId}")
	public String getInterestedEstate(@PathParam("userId") int userId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			List<EstateEntity> estateEntities = EstateDAL.getInstance().getInterestedEstate(userId);
			List<JsonObject> jsonObjects = new ArrayList<>();
			for (EstateEntity entity : estateEntities) {
				Timestamp timeVisited = EstateDAL.getInstance().getVisitedTime(userId, entity.getId());
				jsonObjects.add(response.successResponseInterested(entity, timeVisited));
			}
			return response.successResponseInterestedList(jsonObjects);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
}
