package toannguyen.rem.dal;

import java.sql.SQLException;
import java.util.List;

import toannguyen.rem.dal.database.EstateDatabaseHelper;
import toannguyen.rem.dal.utils.SearchUtils;
import toannguyen.rem.entity.AddressEntity;
import toannguyen.rem.entity.CommentEntity;
import toannguyen.rem.entity.EstateDetailEntity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.PhotoEntity;
import toannguyen.rem.entity.SearchEstateEntity;

public class EstateDAL extends EntityDAL {
	private static EstateDAL _instance;

	protected EstateDAL() {
		// hide constructor
	}

	public static EstateDAL getInstance() {
		if (_instance == null) {
			_instance = new EstateDAL();
		}
		return _instance;
	}

	public List<EstateEntity> getAll() throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryAllEstate();
		} finally {
			dbh.closeConnection();
		}
	}

	public List<EstateEntity> getEstateByOwnerID(int id) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryEstateByOwnerID(id);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<EstateEntity> getNewEstate(int page) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryNewEstate(page);
		} finally {
			dbh.closeConnection();
		}
	}

	public EstateDetailEntity getEstateDetail(int estateId) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryEstateDetail(estateId);
		} finally {
			dbh.closeConnection();
		}
	}
	
	public int getRate(int userId, int estateId) throws SQLException, ClassNotFoundException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.getRate(userId, estateId);
		} finally {
			dbh.closeConnection();
		}
	}

	public EstateEntity postEstate(EstateEntity reqEntity, int userId, boolean isBroker) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.postEstate(reqEntity, userId, isBroker);
		} finally {
			dbh.closeConnection();
		}
	}

	public EstateEntity editEstate(EstateEntity reqEntity) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.editEstate(reqEntity);
		} finally {
			dbh.closeConnection();
		}
	}
	
	public String getRepresentPhoto(int id) throws SQLException, ClassNotFoundException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.getRepresentPhoto(id);
		} finally {
			dbh.closeConnection();
		}
	}

	public void upRepresentPhoto(PhotoEntity reqEntity) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			dbh.upRepresentPhoto(reqEntity, reqEntity.getEstateID(), reqEntity.getTime());
		} finally {
			dbh.closeConnection();
		}
	}

	public List<EstateEntity> getTopRateEstate(int count) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryTopRateEstate(count);
		} finally {
			dbh.closeConnection();
		}
	}

	public void postPhotos(int id, List<PhotoEntity> entities, int avatar) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			dbh.postPhotos(id, entities, avatar);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<PhotoEntity> getPhotos(int estateId) throws SQLException, ClassNotFoundException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.getPhotos(estateId);
		} finally {
			dbh.closeConnection();
		}
	}

	public EstateEntity getEstateByID(int id) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryByID(id);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<EstateEntity> search(SearchEstateEntity searchEntity, int page) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.search(searchEntity, page);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<EstateEntity> searchText(String text, int page) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			String district = SearchUtils.getDistrictFromQuery(text);
			String estateType = SearchUtils.getEstateTypeFromQuery(text);
			if (!district.isEmpty() || !estateType.isEmpty()) {
				SearchEstateEntity searchEntity = new SearchEstateEntity(new AddressEntity(0, "", district, "", ""), estateType);
				List<EstateEntity> l1 = dbh.search(searchEntity, page);
				if (!l1.isEmpty()) {
					return l1;
				}
			}
			return dbh.searchText(text, page);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<EstateEntity> searchGPS(double lat, double lng, int dist, int page) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.searchGPS(lat, lng, dist, page);
		} finally {
			dbh.closeConnection();
		}
	}

	public void updateStatus(int estateId, int status) throws SQLException, ClassNotFoundException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			dbh.updateStatus(estateId, status);
		} finally {
			dbh.closeConnection();
		}
	}
	
	public int getEstateNumberOwner(int id) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryEstateNumberByOwnerID(id);
		} finally {
			dbh.closeConnection();
		}
	}
	
	public int getInterestedEstateNumber(int id) throws Exception {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryInterestedEstateNumber(id);
		} finally {
			dbh.closeConnection();
		}
	}

	public void postComment(CommentEntity commentEntity) throws SQLException, ClassNotFoundException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			dbh.postComment(commentEntity);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<CommentEntity> getComment(int estateId, int ownerId, int buyerId) throws ClassNotFoundException, SQLException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.getComment(estateId, ownerId, buyerId);
		} finally {
			dbh.closeConnection();
		}
	}

	public void answerComment(CommentEntity commentEntity) throws SQLException, ClassNotFoundException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			dbh.answerComment(commentEntity);
		} finally {
			dbh.closeConnection();
		}
	}
}
