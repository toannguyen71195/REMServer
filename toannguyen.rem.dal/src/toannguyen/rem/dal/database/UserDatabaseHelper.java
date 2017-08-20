package toannguyen.rem.dal.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import toannguyen.rem.dal.mapping.InterestedEstateColumn;
import toannguyen.rem.dal.mapping.NoteColumn;
import toannguyen.rem.dal.mapping.NotificationColumn;
import toannguyen.rem.dal.mapping.PhotoNoteColumn;
import toannguyen.rem.dal.mapping.TokenLoginColumn;
import toannguyen.rem.dal.mapping.UserColumn;
import toannguyen.rem.dal.utils.StringUtils;
import toannguyen.rem.entity.EstateEntity;
import toannguyen.rem.entity.NotificationEntity;
import toannguyen.rem.entity.PhotoEntity;
import toannguyen.rem.entity.UserEntity;

public class UserDatabaseHelper extends DatabaseHelper {

	public UserDatabaseHelper() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	protected UserEntity getEntityFromResultSet(ResultSet resultSet)
			throws SQLException, ClassNotFoundException, IOException {
		int id, type;
		String email, password, address, name, fullName, phone, avt;
		id = resultSet.getInt(UserColumn.ID.getColumnName());
		type = resultSet.getInt(UserColumn.USER_TYPE.getColumnName());
		email = resultSet.getString(UserColumn.EMAIL.getColumnName());
		password = resultSet.getString(UserColumn.PASSWORD.getColumnName());
		address = resultSet.getString(UserColumn.ADDRESS.getColumnName());
		name = resultSet.getString(UserColumn.USER_NAME.getColumnName());
		fullName = resultSet.getString(UserColumn.FULL_NAME.getColumnName());
		phone = resultSet.getString(UserColumn.PHONE.getColumnName());
		avt = resultSet.getString(UserColumn.AVATAR.getColumnName());
		return new UserEntity(id, name, fullName, email, phone, password, address, type, avt);
	}

	protected NotificationEntity getNotiFromResultSet(ResultSet resultSet) throws Exception {
		int userId, requestId, estateId;
		userId = resultSet.getInt(NotificationColumn.USER_ID.getColumnName());
		requestId = resultSet.getInt(NotificationColumn.REQUEST_ID.getColumnName());
		estateId = resultSet.getInt(NotificationColumn.ESTATE_ID.getColumnName());
		EstateDatabaseHelper estateDbh = null;
		try {
			estateDbh = new EstateDatabaseHelper();
			EstateEntity estate = estateDbh.queryByID(estateId);
			UserEntity user = getUserByID(userId);
			UserEntity request = getUserByID(requestId);
			return new NotificationEntity(resultSet.getInt(NotificationColumn.ID.getColumnName()), user, request,
					estate, resultSet.getString(NotificationColumn.MESSAGE.getColumnName()),
					resultSet.getInt(NotificationColumn.NOTI_TYPE.getColumnName()));
		} finally {
			if (estateDbh != null) {
				estateDbh.closeConnection();
			}
		}
	}

	protected PhotoEntity getPhotoEntityFromResultSet(ResultSet resultSet) throws SQLException {
		return new PhotoEntity(resultSet.getInt(PhotoNoteColumn.ID.getColumnName()),
				resultSet.getString(PhotoNoteColumn.PHOTO.getColumnName()));
	}

	public UserEntity getUserByID(int id) throws Exception {
		return (UserEntity) super.queryByID(UserColumn.TABLE_NAME, UserColumn.ID.getColumnName(), id);
	}

	public UserEntity queryLogin(String username, String password)
			throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(UserColumn.TABLE_NAME + " where ");
			builder.append(UserColumn.USER_NAME + " = '" + username + "' and ");
			builder.append(UserColumn.PASSWORD + " = '" + password + "';");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return getEntityFromResultSet(rs);
			}
			return null;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public void clearLoginToken(int id) throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append("delete from " + TokenLoginColumn.TABLE_NAME);
		builder.append(" where " + TokenLoginColumn.USER_ID + " = " + id + ";");
		executeUpdate(builder.toString());
	}

	public void insertLoginToken(int id, String token) throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into " + TokenLoginColumn.TABLE_NAME);
		builder.append(" values (" + id + ",'" + token + "');");
		executeUpdate(builder.toString());
	}

	public UserEntity queryLogin(String token) throws SQLException, ClassNotFoundException, IOException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(UserColumn.TABLE_NAME + " u left join ");
			builder.append(TokenLoginColumn.TABLE_NAME + " t on u.");
			builder.append(UserColumn.ID + " = t." + TokenLoginColumn.USER_ID + " where ");
			builder.append(TokenLoginColumn.TOKEN + " = '" + token + "';");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return getEntityFromResultSet(rs);
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

	public UserEntity insertUser(UserEntity entity) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder builder = new StringBuilder();
		try {
			// check username, email, phone
			builder.append("SELECT * FROM ");
			builder.append(UserColumn.TABLE_NAME + " where ");
			builder.append(UserColumn.USER_NAME + " = '" + entity.getName() + "';");
			verifyDuplication(builder, stmt, rs, "Duplicate username");
			builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(UserColumn.TABLE_NAME + " where ");
			builder.append(UserColumn.EMAIL + " = '" + entity.getEmail() + "';");
			verifyDuplication(builder, stmt, rs, "Duplicate email");
			builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(UserColumn.TABLE_NAME + " where ");
			builder.append(UserColumn.PHONE + " = '" + entity.getPhone() + "';");
			verifyDuplication(builder, stmt, rs, "Duplicate phone number");
		} finally {
			closeQuery(stmt, rs);
		}
		// insert
		builder = new StringBuilder();
		builder.append("insert into users(UserType, UserName, FullName, Email, Phone, Address, Password, Avatar) ");
		builder.append("values (" + entity.getTypeId() + ",'" + entity.getName() + "','");
		builder.append(entity.getFullName() + "','" + entity.getEmail() + "','");
		builder.append(entity.getPhone() + "','" + entity.getAddress() + "','" + entity.getPassword() + "', '"
				+ StringUtils.DEFAULT_AVATAR + "');");
		executeUpdate(builder.toString());
		return queryLogin(entity.getName(), entity.getPassword());

	}

	private void verifyDuplication(StringBuilder builder, PreparedStatement stmt, ResultSet rs, String error)
			throws SQLException, Exception {
		stmt = con.prepareStatement(builder.toString());
		rs = stmt.executeQuery();
		if (rs.next()) {
			throw new Exception(error);
		}
		closeQuery(stmt, rs);
	}

	public void updateNote(int userId, int estateId, String note) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(NoteColumn.TABLE_NAME + " where ");
			builder.append(NoteColumn.USER_ID + " = " + userId + " and ");
			builder.append(NoteColumn.ESTATE_ID + " = " + estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) { // update note
				builder = new StringBuilder();
				builder.append("update " + NoteColumn.TABLE_NAME);
				builder.append(" set " + NoteColumn.NOTE + " = '" + note + "'");
				builder.append(" where " + NoteColumn.ID + " = " + rs.getInt(NoteColumn.ID.getColumnName()) + ";");
				executeUpdate(builder.toString());
			} else { // insert note
				builder = new StringBuilder();
				builder.append("insert into " + NoteColumn.TABLE_NAME);
				builder.append(
						" ( " + NoteColumn.USER_ID + ", " + NoteColumn.ESTATE_ID + ", " + NoteColumn.NOTE + ") ");
				builder.append("values (" + userId + ", " + estateId + ", '" + note + "');");
				executeUpdate(builder.toString());
			}
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public String getNote(int userId, int estateId) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(NoteColumn.TABLE_NAME + " where ");
			builder.append(NoteColumn.USER_ID + " = " + userId + " and ");
			builder.append(NoteColumn.ESTATE_ID + " = " + estateId + ";");
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString(NoteColumn.NOTE.getColumnName());
			} else {
				return null;
			}
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public List<UserEntity> searchUser(String query, int offset, int range)
			throws ClassNotFoundException, SQLException, IOException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<UserEntity> entities = new ArrayList<>();
		try {
			StringBuilder builder = new StringBuilder();
			// select distinct * from
			// (select * from users where FullName like 'query%'
			// or UserName like 'query%'
			// or Email like 'query%'
			// or Phone like 'query%' union all
			// select * from users where (Fullname like '%query%' and FullName
			// not like 'query%')
			// or (UserName like '%query%' and UserName not like 'query%')
			// or (Email like '%query%' and Email not like 'query%')
			// or (Phone like '%query%' and Phone not like 'query%')
			// ) as us
			builder.append("select distinct * from ");
			builder.append("(select * from users where FullName like '$query%' ");
			builder.append("or UserName like '$query%' ");
			builder.append("or Email like '$query%' ");
			builder.append("or Phone like '$query%' union all ");
			builder.append("select * from users where (Fullname like '%$query%' and FullName ");
			builder.append("not like '$query%') ");
			builder.append("or (UserName like '%$query%' and UserName not like '$query%') ");
			builder.append("or (Email like '%$query%' and Email not like '$query%') ");
			builder.append("or (Phone like '%$query%' and Phone not like '$query%') ");
			builder.append(") as us ");
			builder.append("limit $offset, $range");
			String q = builder.toString();
			q.replaceAll("$query", query);
			q.replaceAll("$offset", "" + offset);
			q.replaceAll("$range", "" + range);
			stmt = con.prepareStatement(q);
			rs = stmt.executeQuery();
			while (rs.next()) {
				UserEntity entity = getEntityFromResultSet(rs);
				entities.add(entity);
			}
			return entities;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public void postPhotos(int uid, int eid, List<PhotoEntity> entities) throws Exception {
		// clear all
		try {
			StringBuilder builder = new StringBuilder();
			builder = new StringBuilder();
			builder.append("delete from " + PhotoNoteColumn.TABLE_NAME);
			builder.append(" where ");
			builder.append(PhotoNoteColumn.USER_ID + " = ");
			builder.append(uid + " and ");
			builder.append(PhotoNoteColumn.ESTATE_ID + " = ");
			builder.append(eid + ";");
			executeUpdate(builder.toString());
		} catch (Exception e) {
			throw new Exception("Error clear photo: " + e.getMessage());
		}
		// insert all
		try {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < entities.size(); ++i) {
				PhotoEntity reqEntity = entities.get(i);
				builder = new StringBuilder();
				builder.append("insert into " + PhotoNoteColumn.TABLE_NAME);
				builder.append(" (" + PhotoNoteColumn.PHOTO + ", ");
				builder.append(PhotoNoteColumn.USER_ID + ", ");
				builder.append(PhotoNoteColumn.ESTATE_ID + ") ");
				builder.append("values ('" + reqEntity.getPhoto() + "',");
				builder.append(uid + ", ");
				builder.append(eid + ");");
				executeUpdate(builder.toString());
			}
		} catch (Exception e) {
			throw new Exception("Error insert photo: " + e.getMessage());
		}

	}

	public List<PhotoEntity> getPhotos(int userId, int estateId) throws SQLException {
		List<PhotoEntity> photoEntities = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		builder = new StringBuilder();
		builder.append("select * from " + PhotoNoteColumn.TABLE_NAME);
		builder.append(" where ");
		builder.append(PhotoNoteColumn.ESTATE_ID + " = " + estateId + " and ");
		builder.append(PhotoNoteColumn.USER_ID + " = " + userId + ";");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				PhotoEntity entity = getPhotoEntityFromResultSet(rs);
				photoEntities.add(entity);
			}
			return photoEntities;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public boolean checkInterestedEstate(int userId, int estateId) throws SQLException {
		StringBuilder builder = new StringBuilder();
		builder = new StringBuilder();
		builder.append("select * from " + InterestedEstateColumn.TABLE_NAME);
		builder.append(" where ");
		builder.append(InterestedEstateColumn.ESTATE_ID + " = " + estateId + " and ");
		builder.append(InterestedEstateColumn.USER_ID + " = " + userId + ";");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public void updateRequest(int userId, int estateId) throws Exception {
		EstateDatabaseHelper estateDatabaseHelper = null;
		EstateEntity estateEntity;
		UserEntity buyer;
		try {
			estateDatabaseHelper = new EstateDatabaseHelper();
			buyer = getUserByID(userId);
			estateEntity = estateDatabaseHelper.queryByID(estateId);
			if (estateEntity == null || buyer == null) {
				throw new Exception("Estate or user does not exist!");
			}
		} finally {
			if (estateDatabaseHelper != null) {
				estateDatabaseHelper.closeConnection();
			}
		}
		String tmp1 = StringUtils.decodeUnicode("Yêu cầu cập nhập đã bán bất động sản");
		String tmp2 = StringUtils.decodeUnicode("bởi người dùng");
		StringBuilder builder = new StringBuilder();
		builder.append("insert into " + NotificationColumn.TABLE_NAME);
		builder.append(" (" + NotificationColumn.USER_ID + ", ");
		builder.append(NotificationColumn.REQUEST_ID + ", ");
		builder.append(NotificationColumn.ESTATE_ID + ", ");
		builder.append(NotificationColumn.MESSAGE + ", ");
		builder.append(NotificationColumn.NOTI_TYPE + ") ");
		builder.append("values (" + userId + ",");
		builder.append(estateEntity.getOwner().getId() + ", ");
		builder.append(estateId + ", ");
		builder.append("'" + tmp1 + estateEntity.getName());
		builder.append(" " + tmp2 + " " + buyer.getFullName() + "', ");
		builder.append("1);");
		executeUpdate(builder.toString());
	}

	public void reportSpam(int userId, int notiId) throws SQLException, ClassNotFoundException, IOException {
		StringBuilder builder = new StringBuilder();
		builder.append("insert into report_user (UserID, ReportMessage) values (");
		builder.append(userId + ", '");
		builder.append("Report spam request');");
		executeUpdate(builder.toString());
		deleteByID(NotificationColumn.TABLE_NAME, NotificationColumn.ID.getColumnName(), notiId);
	}

	public List<NotificationEntity> getNoti(int userId) throws Exception {
		List<NotificationEntity> entities = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		builder = new StringBuilder();
		builder.append("select * from " + NotificationColumn.TABLE_NAME);
		builder.append(" where ");
		builder.append(NotificationColumn.REQUEST_ID + " = " + userId + ";");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(builder.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				NotificationEntity entity = getNotiFromResultSet(rs);
				entities.add(entity);
			}
			return entities;
		} finally {
			closeQuery(stmt, rs);
		}
	}

	public void deleteNoti(int notiId) throws ClassNotFoundException, SQLException, IOException {
		deleteByID(NotificationColumn.TABLE_NAME, NotificationColumn.ID.getColumnName(), notiId);
	}

	public void saveHistoryType(int id, String type) throws Exception {
		StringBuilder builder = new StringBuilder();
		try {
			builder.append("SELECT * FROM search_history where UserID = ");
			builder.append(id + ";");
			PreparedStatement stmt = null;
			ResultSet rs = null;
			int delete = -1;
			try {
				stmt = con.prepareStatement(builder.toString());
				rs = stmt.executeQuery();
				if (rs.next()) {
					delete = rs.getInt("ID");
					rs.last();
					if (rs.getRow() < 3) {
						delete = -1;
					}
				}
			} finally {
				closeQuery(stmt, rs);
			}
			if (delete > 0) {
				deleteByID("search_history", "ID", delete);
			}
			builder = new StringBuilder("insert into search_history (UserID, EstateType) values (");
			builder.append(id + ", '" + type + "');");
			executeUpdate(builder.toString());
		} catch (Exception e) {
			throw new Exception("Error save: " + e.getMessage() + ". Query: " + builder.toString());
		}
	}

	public void saveHistoryDistrict(int id, String district) throws Exception {
		StringBuilder builder = new StringBuilder();
		try {
			builder.append("SELECT * FROM search_history where UserID = ");
			builder.append(id + ";");
			PreparedStatement stmt = null;
			ResultSet rs = null;
			int delete = -1;
			try {
				stmt = con.prepareStatement(builder.toString());
				rs = stmt.executeQuery();
				if (rs.next()) {
					delete = rs.getInt("ID");
					rs.last();
					if (rs.getRow() < 3) {
						delete = -1;
					}
				}
			} finally {
				closeQuery(stmt, rs);
			}
			if (delete > 0) {
				deleteByID("search_history", "ID", delete);
			}
			builder = new StringBuilder("insert into search_history (UserID, District) values (");
			builder.append(id + ", '" + district + "');");
			executeUpdate(builder.toString());
		} catch (Exception e) {
			throw new Exception("Error save: " + e.getMessage() + ". Query: " + builder.toString());
		}
	}

	// private String saveImage(String base64String) throws IOException {
	// // create a buffered image
	// BufferedImage image = null;
	// byte[] imageByte;
	//
	// imageByte = Base64.decodeBase64(base64String);
	// ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	// try {
	// image = ImageIO.read(bis);
	// } finally {
	// bis.close();
	// }
	// // write the image to a file
	//
	// File imageDir = new File(PATH + File.separator + IMAGE);
	// if (!imageDir.exists()) {
	// imageDir.mkdirs();
	// }
	// SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd-HHmmss");
	// String fileName = fmt.format(new java.util.Date());
	// StringBuilder sb = new StringBuilder();
	// sb.append(imageDir.getAbsolutePath());
	// sb.append("\\");
	// sb.append(fileName + "." + ImageEntity.IMAGE_FORMAT);
	// File outputfile = new File(sb.toString());
	// ImageIO.write(image, ImageEntity.IMAGE_FORMAT, outputfile);
	//
	// return fileName + "." + ImageEntity.IMAGE_FORMAT;
	// }
	//
	// public String encodeToString(BufferedImage image, String type) throws
	// IOException {
	// String imageString = null;
	// ByteArrayOutputStream bos = new ByteArrayOutputStream();
	//
	// try {
	// ImageIO.write(image, type, bos);
	// byte[] imageBytes = bos.toByteArray();
	//
	// imageString = new String(Base64.encodeBase64(imageBytes));
	//
	// } finally {
	// bos.close();
	//
	// }
	// return imageString;
	// }
}
