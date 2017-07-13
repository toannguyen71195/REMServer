package toannguyen.rem.dal.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toannguyen.rem.dal.mapping.AppointmentColumn;
import toannguyen.rem.entity.AppointmentEntity;
import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.UserEntity;

public class AppointmentDatabaseHelper extends DatabaseHelper {

	public AppointmentDatabaseHelper() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	protected Entity getEntityFromResultSet(ResultSet resultSet) throws Exception {
		int user1 = resultSet.getInt(AppointmentColumn.USER1.getColumnName());
		int user2 = resultSet.getInt(AppointmentColumn.USER2.getColumnName());
		UserDatabaseHelper userdatabaseHelper = null;
		UserEntity userEntity1 = null;
		UserEntity userEntity2 = null;
		try {
			userdatabaseHelper = new UserDatabaseHelper();
			userEntity1 = userdatabaseHelper.getUserByID(user1);
			userEntity2 = userdatabaseHelper.getUserByID(user2);
		} finally {
			if (userdatabaseHelper != null) {
				userdatabaseHelper.closeConnection();
			}
		}
		return new AppointmentEntity(resultSet.getInt(AppointmentColumn.ID.getColumnName()),
				resultSet.getString(AppointmentColumn.TITLE.getColumnName()),
				resultSet.getString(AppointmentColumn.ADDRESS.getColumnName()),
				resultSet.getString(AppointmentColumn.NOTE.getColumnName()),
				resultSet.getTimestamp(AppointmentColumn.TIME.getColumnName()),
				resultSet.getInt(AppointmentColumn.STATUS.getColumnName()), userEntity1, userEntity2,
				resultSet.getInt(AppointmentColumn.ESTATE_ID.getColumnName()));
	}

	public List<AppointmentEntity> getListAppointment(int userId) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<AppointmentEntity> entities = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(AppointmentColumn.TABLE_NAME);
			builder.append(" where ");
			builder.append(AppointmentColumn.USER1 + " = " + userId);
			builder.append(" or " + AppointmentColumn.USER2 + " = " + userId);
			builder.append(" order by " + AppointmentColumn.TIME + " desc");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				AppointmentEntity entity = (AppointmentEntity) getEntityFromResultSet(rs);
				entities.add(entity);
			}
			return entities;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public AppointmentEntity updateStatus(int apptId, int status, String name, String note) throws Exception {
		AppointmentEntity entity = (AppointmentEntity) queryByID(AppointmentColumn.TABLE_NAME, AppointmentColumn.ID.getColumnName(), apptId);
		if (entity == null) {
			throw new Exception("Appointment doesnot exist");
		}
		StringBuilder builder = new StringBuilder();
		builder.append("update ");
		builder.append(AppointmentColumn.TABLE_NAME);
		builder.append(" set ");
		builder.append(AppointmentColumn.STATUS + " = " + status + ", ");
		String extraNote = entity.getNote() + "\n" + name + ": " + note;
		builder.append(AppointmentColumn.NOTE + " = '" + extraNote + "'");
		builder.append(" where " + AppointmentColumn.ID + " = " + apptId + ";");
		executeUpdate(builder.toString());
		entity = (AppointmentEntity) queryByID(AppointmentColumn.TABLE_NAME, AppointmentColumn.ID.getColumnName(), apptId);
		return entity;
	}

	public AppointmentEntity updateStatus(int apptId, int status) throws Exception {
		AppointmentEntity entity = (AppointmentEntity) queryByID(AppointmentColumn.TABLE_NAME, AppointmentColumn.ID.getColumnName(), apptId);
		if (entity == null) {
			throw new Exception("Appointment doesnot exist");
		}
		StringBuilder builder = new StringBuilder();
		builder.append("update ");
		builder.append(AppointmentColumn.TABLE_NAME);
		builder.append(" set ");
		builder.append(AppointmentColumn.STATUS + " = " + status);
		builder.append(" where " + AppointmentColumn.ID + " = " + apptId + ";");
		executeUpdate(builder.toString());
		entity = (AppointmentEntity) queryByID(AppointmentColumn.TABLE_NAME, AppointmentColumn.ID.getColumnName(), apptId);
		return entity;
	}

	public void book(AppointmentEntity entity) throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into ");
		builder.append(AppointmentColumn.TABLE_NAME);
		builder.append(" (" + AppointmentColumn.ADDRESS + ", ");
		builder.append(AppointmentColumn.TITLE + ", ");
		builder.append(AppointmentColumn.NOTE + ", ");
		builder.append(AppointmentColumn.STATUS + ", ");
		builder.append(AppointmentColumn.TIME + ", ");
		builder.append(AppointmentColumn.USER1 + ", ");
		builder.append(AppointmentColumn.USER2 + ", ");
		builder.append(AppointmentColumn.ESTATE_ID + ") values (");
		builder.append("'" + entity.getAddress() + "', ");
		builder.append("'" + entity.getName() + "', ");
		builder.append("'" + entity.getNote() + "', ");
		builder.append("1 , ");
		builder.append("'" + entity.getTime() + "', ");
		builder.append(entity.getUser1().getId() + ", ");
		builder.append(entity.getUser2().getId() + ", ");
		builder.append(entity.getEstate() + ");");
		executeUpdate(builder.toString());
	}

}
