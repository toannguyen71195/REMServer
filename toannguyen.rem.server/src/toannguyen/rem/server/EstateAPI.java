package toannguyen.rem.server;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONObject;

import com.google.gson.JsonObject;

import toannguyen.rem.dal.EstateDAL;
import toannguyen.rem.dal.mapping.CommentColumn;
import toannguyen.rem.entity.CommentEntity;
import toannguyen.rem.entity.EstateDetailEntity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.PhotoEntity;
import toannguyen.rem.entity.json.JsonToEntityConverter;
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
		} catch (Exception e) {
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
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getNew/{count}")
	public String getRecentEstate(@PathParam("count") int count) {
		TypicalAPIResponse response = new TypicalAPIResponse();
		try {
			List<EstateEntity> estateEntities = EstateDAL.getInstance().getNewEstate(count);
			return response.successResponse(estateEntities, EstateEntity.ESTATE_LIST);
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getDetail/{estateId}")
	public String getEstateDetail(@PathParam("estateId") int estateId) {
		TypicalAPIResponse response = new TypicalAPIResponse();
		try {
			EstateDetailEntity estateDetailEntity = EstateDAL.getInstance().getEstateDetail(estateId);
			return response.successResponse(estateDetailEntity);
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getInterested/{userId}")
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
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/setInterested/{userId}-{estateId}")
	public String setInterested(@PathParam("estateId") int estateId, @PathParam("userId") int userId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			EstateDAL.getInstance().setInterestedEstate(userId, estateId);
			return response.successEmptyResponse("Update success");
		} catch (ClassNotFoundException | SQLException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@POST
	@Path("/comment")
	public String commentEstate(String json) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			JSONObject jsonObject = new JSONObject(json);
			int userId = jsonObject.getInt(CommentColumn.USER_ID.getColumnName());
			int estateId = jsonObject.getInt(CommentColumn.ESTATE_ID.getColumnName());
			String comment = jsonObject.getString(CommentColumn.COMMENT.getColumnName());
			CommentEntity commentEntity = EstateDAL.getInstance().comment(userId, estateId, comment);
			if (commentEntity != null) {
				return response.successResponse(commentEntity);
			} else {
				return response.unsuccessResponse("Unable to retrieve updated comment. Please refresh");
			}
		} catch (ClassNotFoundException | SQLException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/deleteComment/{commentId}")
	public String deleteComment(@PathParam("commentId") int commentId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			EstateDAL.getInstance().deleteComment(commentId);
			return response.successEmptyResponse("Update success");
		} catch (ClassNotFoundException | SQLException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getComment/{estateId}")
	public String getComment(@PathParam("estateId") int estateId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			List<CommentEntity> commentEntities = EstateDAL.getInstance().queryCommentByEstate(estateId);
			return response.successResponse(commentEntities, "comments");
		} catch (ClassNotFoundException | SQLException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@POST
	@Path("/getNote/{estateId}-{userId}") // wip
	public String getNote(@PathParam("estateId") int estateId, @PathParam("userId") int userId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			List<CommentEntity> commentEntities = EstateDAL.getInstance().queryCommentByEstate(estateId);
			return response.successResponse(commentEntities, "comments");
		} catch (ClassNotFoundException | SQLException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getRepresentPhoto/{estateId}")
	public String getRepresentPhoto(@PathParam("estateId") int estateId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			String photo = EstateDAL.getInstance().getRepresentPhoto(estateId);
			if (photo != null) {
				return response.successStringResponse("photo", photo);
			} else {
				return response.unsuccessResponse("Estate does not have representative photo in db");
			}
		} catch (ClassNotFoundException | SQLException e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/post")
	public String post(String json) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			EstateEntity reqEntity = JsonToEntityConverter.convertJsonStringToEntity(json, EstateEntity.class);
			EstateEntity rtEntity = EstateDAL.getInstance().postEstate(reqEntity);
			return response.successResponse(rtEntity);
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/upRepresentPhoto")
	public String upRepresentPhoto(String json) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			PhotoEntity reqEntity = JsonToEntityConverter.convertJsonStringToEntity(json, PhotoEntity.class);
			EstateDAL.getInstance().upRepresentPhoto(reqEntity);
			return response.successEmptyResponse("Update success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/edit")
	public String edit(String json) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			EstateEntity reqEntity = JsonToEntityConverter.convertJsonStringToEntity(json, EstateEntity.class);
			EstateEntity rtEntity = EstateDAL.getInstance().editEstate(reqEntity);
			return response.successResponse(rtEntity);
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("upPhotoList")
	public String upPhotoList(String json) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			PhotoEntity reqEntity = JsonToEntityConverter.convertJsonStringToEntity(json, PhotoEntity.class);
			EstateDAL.getInstance().upRepresentPhoto(reqEntity);
			return response.successEmptyResponse("Update success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
}
