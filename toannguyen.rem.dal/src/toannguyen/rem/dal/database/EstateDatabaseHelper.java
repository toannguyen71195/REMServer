package toannguyen.rem.dal.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import toannguyen.rem.dal.mapping.AddressColumn;
import toannguyen.rem.dal.mapping.CommentColumn;
import toannguyen.rem.dal.mapping.EstateColumn;
import toannguyen.rem.dal.mapping.EstateDetailColumn;
import toannguyen.rem.dal.mapping.EstateTypeColumn;
import toannguyen.rem.dal.mapping.InterestedEstateColumn;
import toannguyen.rem.dal.mapping.PhotoColumn;
import toannguyen.rem.entity.AddressEntity;
import toannguyen.rem.entity.CommentEntity;
import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.EstateDetailEntity;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.UserEntity;

public class EstateDatabaseHelper extends DatabaseHelper {

	public EstateDatabaseHelper() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	protected Entity getEntityFromResultSet(ResultSet resultSet)
			throws SQLException, ClassNotFoundException, IOException {
		UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper();
		try {
			UserEntity userEntity = userDatabaseHelper
					.getUserByID(resultSet.getInt(EstateColumn.OWNER_ID.getColumnName()));
			return new EstateEntity(resultSet.getInt(EstateColumn.ID.getColumnName()),
					resultSet.getString(EstateColumn.NAME.getColumnName()), userEntity,
					getAddressEntityFromResultSet(resultSet),
					resultSet.getInt(EstateColumn.STATUS_ID.getColumnName()) == EstateEntity.STATUS_AVAILABLE,
					resultSet.getString(EstateTypeColumn.NAME.getColumnName()),
					resultSet.getTimestamp(EstateColumn.POST_TIME.getColumnName()),
					resultSet.getDouble(EstateColumn.PRICE.getColumnName()),
					resultSet.getDouble(EstateColumn.AREA.getColumnName()));

		} finally {
			if (userDatabaseHelper != null) {
				userDatabaseHelper.closeConnection();
			}
		}
	}

	private AddressEntity getAddressEntityFromResultSet(ResultSet resultSet) throws SQLException {
		return new AddressEntity(resultSet.getInt(AddressColumn.ID.getColumnName()),
				resultSet.getString(AddressColumn.CITY.getColumnName()),
				resultSet.getString(AddressColumn.DISTRICT.getColumnName()),
				resultSet.getString(AddressColumn.WARD.getColumnName()),
				resultSet.getString(AddressColumn.STREET.getColumnName()),
				resultSet.getString(AddressColumn.ADDRESS.getColumnName()));
	}

	private CommentEntity getCommentFromResultSet(ResultSet resultSet) throws SQLException {
		return new CommentEntity(resultSet.getInt(CommentColumn.ID.getColumnName()),
				resultSet.getInt(CommentColumn.USER_ID.getColumnName()),
				resultSet.getInt(CommentColumn.ESTATE_ID.getColumnName()),
				resultSet.getString(CommentColumn.COMMENT.getColumnName()),
				resultSet.getTimestamp(CommentColumn.TIME.getColumnName()));
	}

	public List<EstateEntity> queryAllEstate() throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EstateEntity> result = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(EstateColumn.TABLE_NAME + " e left join ");
			builder.append(AddressColumn.TABLE_NAME + " a on e.");
			builder.append(EstateColumn.ADDRESS_ID + " = a.");
			builder.append(AddressColumn.ID);
			builder.append(" left join ");
			builder.append(EstateTypeColumn.TABLE_NAME + " t on e.");
			builder.append(EstateColumn.ESTATE_TYPE_ID + " = t.");
			builder.append(EstateTypeColumn.ID + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				EstateEntity entity = (EstateEntity) getEntityFromResultSet(rs);
				result.add(entity);
			}
			return result;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public List<EstateEntity> queryEstateByOwnerID(int id) throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EstateEntity> entities = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(EstateColumn.TABLE_NAME + " e left join ");
			builder.append(AddressColumn.TABLE_NAME + " a on e.");
			builder.append(EstateColumn.ADDRESS_ID + " = a.");
			builder.append(AddressColumn.ID);
			builder.append(" left join ");
			builder.append(EstateTypeColumn.TABLE_NAME + " t on e.");
			builder.append(EstateColumn.ESTATE_TYPE_ID + " = t.");
			builder.append(EstateTypeColumn.ID + " where e.");
			builder.append(EstateColumn.OWNER_ID + " = ");
			builder.append(id + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				EstateEntity entity = (EstateEntity) getEntityFromResultSet(rs);
				entities.add(entity);
			}
			return entities;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public List<EstateEntity> queryNewEstate(int count) throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EstateEntity> result = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(EstateColumn.TABLE_NAME + " e left join ");
			builder.append(AddressColumn.TABLE_NAME + " a on e.");
			builder.append(EstateColumn.ADDRESS_ID + " = a.");
			builder.append(AddressColumn.ID);
			builder.append(" left join ");
			builder.append(EstateTypeColumn.TABLE_NAME + " t on e.");
			builder.append(EstateColumn.ESTATE_TYPE_ID + " = t.");
			builder.append(EstateTypeColumn.ID + " where e.");
			builder.append(EstateColumn.STATUS_ID + " = " + EstateEntity.STATUS_AVAILABLE);
			builder.append(" order by " + EstateColumn.POST_TIME + " desc");
			builder.append(" limit " + count + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				EstateEntity entity = (EstateEntity) getEntityFromResultSet(rs);
				result.add(entity);
			}
			return result;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public EstateDetailEntity queryEstateDetail(int estateId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(EstateColumn.TABLE_NAME + " e left join ");
			builder.append(EstateDetailColumn.TABLE_NAME + " d on e.");
			builder.append(EstateColumn.ESTATE_DETAIL_ID + " = d.");
			builder.append(EstateDetailColumn.ID);
			builder.append(" where e.");
			builder.append(EstateColumn.ID + " = ");
			builder.append(estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			rs.next();
			EstateDetailEntity entity = (EstateDetailEntity) getEstateDetailFromResultSet(rs);
			return entity;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	private EstateDetailEntity getEstateDetailFromResultSet(ResultSet rs) throws SQLException {
		return new EstateDetailEntity(rs.getInt(EstateDetailColumn.ID.getColumnName()),
				rs.getInt(EstateDetailColumn.BATHROOM.getColumnName()),
				rs.getInt(EstateDetailColumn.BEDROOM.getColumnName()),
				rs.getString(EstateDetailColumn.CONDITION.getColumnName()),
				rs.getString(EstateDetailColumn.DESCRIPTION.getColumnName()),
				rs.getInt(EstateDetailColumn.FLOOR.getColumnName()),
				rs.getDouble(EstateDetailColumn.LENGTH.getColumnName()),
				rs.getDouble(EstateDetailColumn.WIDTH.getColumnName()));
	}

	public List<EstateEntity> queryInterestedEstate(int userId)
			throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EstateEntity> entities = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(InterestedEstateColumn.TABLE_NAME + " i left join ");
			builder.append(EstateColumn.TABLE_NAME + " e on i.");
			builder.append(InterestedEstateColumn.ESTATE_ID + " = e.");
			builder.append(EstateColumn.ID);
			builder.append(" left join ");
			builder.append(AddressColumn.TABLE_NAME + " a on e.");
			builder.append(EstateColumn.ADDRESS_ID + " = a.");
			builder.append(AddressColumn.ID);
			builder.append(" left join ");
			builder.append(EstateTypeColumn.TABLE_NAME + " t on e.");
			builder.append(EstateColumn.ESTATE_TYPE_ID + " = t.");
			builder.append(EstateTypeColumn.ID);
			builder.append(" where i.");
			builder.append(InterestedEstateColumn.USER_ID + " = ");
			builder.append(userId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				EstateEntity entity = (EstateEntity) getEntityFromResultSet(rs);
				entities.add(entity);
			}
			return entities;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public Timestamp getVisitedTime(int userId, int estateId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(InterestedEstateColumn.TABLE_NAME);
			builder.append(" where ");
			builder.append(InterestedEstateColumn.USER_ID + " = ");
			builder.append(userId);
			builder.append(" and ");
			builder.append(InterestedEstateColumn.ESTATE_ID + " = ");
			builder.append(estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getTimestamp(InterestedEstateColumn.TIME.getColumnName());
			} else {
				return null;
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void setInterested(int userId, int estateId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// check if interested => delete
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM ");
		builder.append(InterestedEstateColumn.TABLE_NAME);
		builder.append(" where ");
		builder.append(InterestedEstateColumn.USER_ID + " = ");
		builder.append(userId + " and ");
		builder.append(InterestedEstateColumn.ESTATE_ID + " = ");
		builder.append(estateId + ";");
		stmt = con.prepareStatement(builder.toString());
		rs = stmt.executeQuery();
		if (rs.next()) { // delete
			builder = new StringBuilder();
			builder.append("delete from " + InterestedEstateColumn.TABLE_NAME);
			builder.append(" where ");
			builder.append(InterestedEstateColumn.USER_ID + " = ");
			builder.append(userId + " and ");
			builder.append(InterestedEstateColumn.ESTATE_ID + " = ");
			builder.append(estateId + ";");
			executeUpdate(stmt, rs, builder);
		} else { // else insert
			builder = new StringBuilder();
			builder.append("insert into " + InterestedEstateColumn.TABLE_NAME);
			builder.append(" (" + InterestedEstateColumn.ESTATE_ID.getColumnName() + ", ");
			builder.append(InterestedEstateColumn.USER_ID + ", ");
			builder.append(InterestedEstateColumn.IS_VISITED + ") ");
			builder.append("values (" + estateId + ", " + userId + ", false);");
			executeUpdate(stmt, rs, builder);
		}
	}

	private void executeUpdate(PreparedStatement stmt, ResultSet rs, StringBuilder builder) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		try {
			con.setAutoCommit(false);
			stmt = con.prepareStatement(builder.toString());
			stmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				con.rollback();
			}
			throw e;
		} finally {
			con.setAutoCommit(true);
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public CommentEntity comment(int userId, int estateId, String comment) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		builder.append("insert into " + CommentColumn.TABLE_NAME);
		builder.append(" (" + CommentColumn.ESTATE_ID.getColumnName() + ", ");
		builder.append(CommentColumn.USER_ID + ", ");
		builder.append(CommentColumn.COMMENT + ", ");
		builder.append(CommentColumn.TIME + ") ");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		builder.append("values (" + estateId + ", " + userId + ", '" + comment + "', '" + timestamp.toString() + "');");
		try {
			executeUpdate(stmt, rs, builder);
			return queryLastCommentBy2ID(userId, estateId);
		} catch (SQLException e) {
			throw e;
		}
	}

	private CommentEntity queryLastCommentBy2ID(int userId, int estateId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(CommentColumn.TABLE_NAME);
			builder.append(" where ");
			builder.append(CommentColumn.USER_ID + " = ");
			builder.append(userId + " and ");
			builder.append(CommentColumn.ESTATE_ID + " = ");
			builder.append(estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.last()) {
				return getCommentFromResultSet(rs);
			}
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public List<CommentEntity> queryCommentByEstate(int estateId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<CommentEntity> entities = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(CommentColumn.TABLE_NAME);
			builder.append(" where ");
			builder.append(CommentColumn.ESTATE_ID + " = ");
			builder.append(estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				CommentEntity entity = (CommentEntity) getCommentFromResultSet(rs);
				entities.add(entity);
			}
			return entities;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void deleteComment(int commentId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		builder.append("delete from " + CommentColumn.TABLE_NAME);
		builder.append(" where ");
		builder.append(CommentColumn.ID + " = ");
		builder.append(commentId + ";");
		executeUpdate(stmt, rs, builder);
	}

	public String getRepresentPhoto(int estateId) throws SQLException {
		// SELECT * FROM estate e left join photo p on e.PhotoID = p.PhotoID
		// where e.EstateID = 1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM " + EstateColumn.TABLE_NAME);
			builder.append(" e left join " + PhotoColumn.TABLE_NAME + " p on e.");
			builder.append(EstateColumn.PHOTO_ID + " = p." + PhotoColumn.ID);
			builder.append(" where e." + EstateColumn.ID + " = " + estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString(PhotoColumn.PHOTO.getColumnName());
			}
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

}
