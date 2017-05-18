package toannguyen.rem.dal;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import toannguyen.rem.dal.database.EstateDatabaseHelper;
import toannguyen.rem.entity.CommentEntity;
import toannguyen.rem.entity.EstateDetailEntity;
import toannguyen.rem.entity.EstateEntity;

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

	public List<EstateEntity> getAll() throws ClassNotFoundException, SQLException, IOException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryAllEstate();
		} finally {
			dbh.closeConnection();
		}
	}

	public List<EstateEntity> getEstateByOwnerID(int id) throws ClassNotFoundException, SQLException, IOException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryEstateByOwnerID(id);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<EstateEntity> getNewEstate(int count) throws ClassNotFoundException, SQLException, IOException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryNewEstate(count);
		} finally {
			dbh.closeConnection();
		}
	}

	public EstateDetailEntity getEstateDetail(int estateId) throws ClassNotFoundException, SQLException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryEstateDetail(estateId);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<EstateEntity> getInterestedEstate(int userId) throws ClassNotFoundException, SQLException, IOException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryInterestedEstate(userId);
		} finally {
			dbh.closeConnection();
		}
	}
	
	// return null if not visited
	public Timestamp getVisitedTime(int userId, int estateId) throws SQLException, ClassNotFoundException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.getVisitedTime(userId, estateId);
		} finally {
			dbh.closeConnection();
		}
	}

	public void setInterestedEstate(int userId, int estateId) throws SQLException, ClassNotFoundException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			dbh.setInterested(userId, estateId);
		} finally {
			dbh.closeConnection();
		}
	}

	public CommentEntity comment(int userId, int estateId, String comment) throws SQLException, ClassNotFoundException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.comment(userId, estateId, comment);
		} finally {
			dbh.closeConnection();
		}
	}

	public List<CommentEntity> queryCommentByEstate(int estateId) throws ClassNotFoundException, SQLException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.queryCommentByEstate(estateId);
		} finally {
			dbh.closeConnection();
		}
	}

	public void deleteComment(int commentId) throws ClassNotFoundException, SQLException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			dbh.deleteComment(commentId);
		} finally {
			dbh.closeConnection();
		}
	}

	public String getRepresentPhoto(int estateId) throws ClassNotFoundException, SQLException {
		EstateDatabaseHelper dbh = new EstateDatabaseHelper();
		try {
			return dbh.getRepresentPhoto(estateId);
		} finally {
			dbh.closeConnection();
		}
	}
}
