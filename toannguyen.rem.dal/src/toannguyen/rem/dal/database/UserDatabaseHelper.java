package toannguyen.rem.dal.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import toannguyen.rem.dal.mapping.NoteColumn;
import toannguyen.rem.dal.mapping.TokenLoginColumn;
import toannguyen.rem.dal.mapping.UserColumn;
import toannguyen.rem.entity.UserEntity;

public class UserDatabaseHelper extends DatabaseHelper {

	public UserDatabaseHelper() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	protected UserEntity getEntityFromResultSet(ResultSet resultSet)
			throws SQLException, ClassNotFoundException, IOException {
		int id, type;
		String email, password, address, name, fullName, phone;
		id = resultSet.getInt(UserColumn.ID.getColumnName());
		type = resultSet.getInt(UserColumn.USER_TYPE.getColumnName());
		email = resultSet.getString(UserColumn.EMAIL.getColumnName());
		password = resultSet.getString(UserColumn.PASSWORD.getColumnName());
		address = resultSet.getString(UserColumn.ADDRESS.getColumnName());
		name = resultSet.getString(UserColumn.USER_NAME.getColumnName());
		fullName = resultSet.getString(UserColumn.FULL_NAME.getColumnName());
		phone = resultSet.getString(UserColumn.PHONE.getColumnName());
		return new UserEntity(id, name, fullName, email, phone, password, address, type);
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
		builder.append("insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) ");
		builder.append("values (" + entity.getTypeId() + ",'" + entity.getName() + "','");
		builder.append(entity.getFullName() + "','" + entity.getEmail() + "','");
		builder.append(entity.getPhone() + "','" + entity.getAddress() + "','" + entity.getPassword() + "');");
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

	public void updateNote(String userId, String estateId, String note) throws SQLException {
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
				builder.append("set " + NoteColumn.NOTE + " = '" + note + "'");
				builder.append("where " + NoteColumn.ID + " = " + rs.getInt(NoteColumn.ID.getColumnName()) + ";");
				builder.append(NoteColumn.ESTATE_ID + " = " + estateId + ";");
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
