package toannguyen.rem.dal.database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		String email, password, address, name;
		id = resultSet.getInt(UserColumn.ID.getColumnName());
		type = resultSet.getInt(UserColumn.USER_TYPE.getColumnName());
		email = resultSet.getString(UserColumn.EMAIL.getColumnName());
		password = resultSet.getString(UserColumn.PASSWORD.getColumnName());
		address = resultSet.getString(UserColumn.ADDRESS.getColumnName());
		name = resultSet.getString(UserColumn.USER_NAME.getColumnName());
		return new UserEntity(id, name, email, password, address, type);
	}
	
	public UserEntity getUserByID(int id) throws ClassNotFoundException, SQLException, IOException {
		return (UserEntity) super.queryByID(UserColumn.TABLE_NAME, UserColumn.ID.getColumnName(), id);
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
