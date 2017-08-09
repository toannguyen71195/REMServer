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

import toannguyen.rem.dal.EstateDAL;
import toannguyen.rem.entity.CommentEntity;
import toannguyen.rem.entity.EstateDetailEntity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.PhotoEntity;
import toannguyen.rem.entity.SearchEstateEntity;
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
	@Path("/getByID/{id}")
	public String getByID(@PathParam("id") int id) {
		TypicalAPIResponse response = new TypicalAPIResponse();
		try {
			EstateEntity estateEntity = EstateDAL.getInstance().getEstateByID(id);
			return response.successResponse(estateEntity);
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getNew/{page}")
	public String getRecentEstate(@PathParam("page") int page) {
		TypicalAPIResponse response = new TypicalAPIResponse();
		try {
			List<EstateEntity> estateEntities = EstateDAL.getInstance().getNewEstate(page);
			return response.successResponse(estateEntities, EstateEntity.ESTATE_LIST);
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("/getTopRate/{count}")
	public String getTopRate(@PathParam("count") int count) {
		TypicalAPIResponse response = new TypicalAPIResponse();
		try {
			List<EstateEntity> estateEntities = EstateDAL.getInstance().getTopRateEstate(count);
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
			JSONObject jsonObject = new JSONObject(json);
			EstateEntity reqEntity = JsonToEntityConverter.convertJsonStringToEntity(json, EstateEntity.class);
			boolean isBroker = jsonObject.getBoolean("broker");
			int userId = jsonObject.getJSONObject("owner").getInt("id");
			EstateEntity rtEntity = EstateDAL.getInstance().postEstate(reqEntity, userId, isBroker);
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
			JSONObject jsonObject = new JSONObject(json);
			int id = jsonObject.getInt("id");
			List<PhotoEntity> entities = new ArrayList<>();
			JSONArray array = jsonObject.getJSONArray("photos");
			int avatar = jsonObject.getInt("avatar");
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				PhotoEntity entity = JsonToEntityConverter.convertJsonStringToEntity(object.toString(),
						PhotoEntity.class);
				entities.add(entity);
			}
			EstateDAL.getInstance().postPhotos(id, entities, avatar);
			return response.successEmptyResponse("Update success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@GET
	@Path("getPhotoList/{estateId}")
	public String getPhotoList(@PathParam("estateId") int estateId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			List<PhotoEntity> photoEntities = EstateDAL.getInstance().getPhotos(estateId);
			return response.successResponse(photoEntities, "photos");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}

	@POST
	@Path("search/{page}")
	public String search(String searchEntityJson, @PathParam("page") int page) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			EstateEntity estateEntity = JsonToEntityConverter.convertJsonStringToEntity(searchEntityJson, EstateEntity.class);
			SearchEstateEntity searchEntity = null;
			try {
				searchEntity = new SearchEstateEntity(estateEntity);
			} catch (Exception e) {
				throw new Exception("Error parse to search entity: " + e.getMessage());
			}
			List<EstateEntity> estateEntities = EstateDAL.getInstance().search(searchEntity, page);
			return response.successResponse(estateEntities, "estates");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("searchGPS/{page}")
	public String searchGPS(String json, @PathParam("page") int page) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			JSONObject jsonObject = new JSONObject(json);
			double lat = jsonObject.getDouble("latitude");
			double lng = jsonObject.getDouble("longitude");
			int dist = jsonObject.getInt("distance");
			List<EstateEntity> estateEntities = EstateDAL.getInstance().searchGPS(lat, lng, dist, page);
			return response.successResponse(estateEntities, "estates");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("searchText/{text}/{page}")
	public String searchText(@PathParam("text") String text, @PathParam("page") int page) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			List<EstateEntity> estateEntities = EstateDAL.getInstance().searchText(text, page);
			return response.successResponse(estateEntities, "estates");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("updateStatus/{estateId}-{status}")
	public String updateStatus(@PathParam("estateId") int estateId, @PathParam("status") int status) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			EstateDAL.getInstance().updateStatus(estateId, status);
			return response.successEmptyResponse("Update success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("comment") 
	public String comment(String json) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			CommentEntity commentEntity = JsonToEntityConverter.convertJsonStringToEntity(json, CommentEntity.class);
			EstateDAL.getInstance().postComment(commentEntity);
			return response.successEmptyResponse("Update success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("answerComment") 
	public String answerComment(String json) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			CommentEntity commentEntity = JsonToEntityConverter.convertJsonStringToEntity(json, CommentEntity.class);
			EstateDAL.getInstance().answerComment(commentEntity);
			return response.successEmptyResponse("Update success");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("getCommentBuyer/{estateId}-{buyerId}") 
	public String getCommentBuyer(@PathParam("estateId") int estateId,
			@PathParam("buyerId") int buyerId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			List<CommentEntity> entities = EstateDAL.getInstance().getCommentBuyer(estateId, buyerId);
			return response.successResponse(entities, "comments");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("getCommentOwner/{estateId}-{ownerId}") 
	public String getCommentOwner(@PathParam("estateId") int estateId, @PathParam("ownerId") int ownerId) {
		EstateAPIResponse response = new EstateAPIResponse();
		try {
			List<CommentEntity> entities = EstateDAL.getInstance().getCommentOwner(estateId, ownerId);
			return response.successResponse(entities, "comments");
		} catch (Exception e) {
			return response.unsuccessResponse(e.getMessage());
		}
	}
	
	/*
	 * @POST
	 * 
	 * @Path("/comment") public String commentEstate(String json) {
	 * EstateAPIResponse response = new EstateAPIResponse(); try { JSONObject
	 * jsonObject = new JSONObject(json); int userId =
	 * jsonObject.getInt(CommentColumn.USER_ID.getColumnName()); int estateId =
	 * jsonObject.getInt(CommentColumn.ESTATE_ID.getColumnName()); String
	 * comment = jsonObject.getString(CommentColumn.COMMENT.getColumnName());
	 * CommentEntity commentEntity = EstateDAL.getInstance().comment(userId,
	 * estateId, comment); if (commentEntity != null) { return
	 * response.successResponse(commentEntity); } else { return
	 * response.unsuccessResponse(
	 * "Unable to retrieve updated comment. Please refresh"); } } catch
	 * (ClassNotFoundException | SQLException e) { return
	 * response.unsuccessResponse(e.getMessage()); } }
	 * 
	 * @GET
	 * 
	 * @Path("/deleteComment/{commentId}") public String
	 * deleteComment(@PathParam("commentId") int commentId) { EstateAPIResponse
	 * response = new EstateAPIResponse(); try {
	 * EstateDAL.getInstance().deleteComment(commentId); return
	 * response.successEmptyResponse("Update success"); } catch
	 * (ClassNotFoundException | SQLException e) { return
	 * response.unsuccessResponse(e.getMessage()); } }
	 * 
	 * @GET
	 * 
	 * @Path("/getComment/{estateId}") public String
	 * getComment(@PathParam("estateId") int estateId) { EstateAPIResponse
	 * response = new EstateAPIResponse(); try { List<CommentEntity>
	 * commentEntities = EstateDAL.getInstance().queryCommentByEstate(estateId);
	 * return response.successResponse(commentEntities, "comments"); } catch
	 * (ClassNotFoundException | SQLException e) { return
	 * response.unsuccessResponse(e.getMessage()); } }
	 */

}
