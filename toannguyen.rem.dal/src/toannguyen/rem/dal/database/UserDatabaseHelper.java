package toannguyen.rem.dal.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public UserEntity getUserByID(int id) throws ClassNotFoundException, SQLException, IOException {
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
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void insertLoginToken(int id, String token) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con.setAutoCommit(false);
			StringBuilder builder = new StringBuilder();
			builder.append("insert into " + TokenLoginColumn.TABLE_NAME);
			builder.append("values(" + id + "," + "'" + token + "');");
			stmt = con.prepareStatement(builder.toString());
			stmt.executeUpdate();
			con.commit();
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			if (con != null) {
				con.rollback();
			}
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
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
		try {
			// check username, email, phone
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(UserColumn.TABLE_NAME + " where ");
			builder.append(UserColumn.USER_NAME + " = '" + entity.getName() + "';");
			verifyDuplication(builder, stmt, rs);
			builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(UserColumn.TABLE_NAME + " where ");
			builder.append(UserColumn.EMAIL + " = '" + entity.getEmail() + "';");
			verifyDuplication(builder, stmt, rs);
			builder = new StringBuilder();
			builder.append("SELECT * FROM ");
			builder.append(UserColumn.TABLE_NAME + " where ");
			builder.append(UserColumn.PHONE + " = '" + entity.getPhone() + "';");
			verifyDuplication(builder, stmt, rs);
			// insert
			con.setAutoCommit(false);
			builder = new StringBuilder();
			builder.append("insert into users(UserType, UserName, FullName, Email, Phone, Password) ");
			builder.append("values (" + entity.getTypeId() + ",'" + entity.getName() + "','");
			builder.append(entity.getFullName() + "','" + entity.getEmail() + "','");
			builder.append(entity.getPhone() + "','" + entity.getPassword() + "');");
			stmt = con.prepareStatement(builder.toString());
			stmt.executeUpdate();
			con.commit();
			rs = stmt.executeQuery();
			// login
			con.setAutoCommit(true);
			return queryLogin(entity.getName(), entity.getPassword());
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	private void verifyDuplication(StringBuilder builder, PreparedStatement stmt, ResultSet rs) throws SQLException, Exception {
		stmt = con.prepareStatement(builder.toString());
		rs = stmt.executeQuery();
		if (rs.next()) {
			throw new Exception("Duplicate username");
		}
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
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
