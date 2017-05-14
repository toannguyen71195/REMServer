package toannguyen.rem.entity;

public class UserEntity extends Entity {
	
	public static String USER_LIST = "users";
	public static String TOKEN = "token";
	private String email;
	private String password;
	private String address;
	private int typeId;
	private String fullName;
	private String phone;
	// private ImageEntity imageEntity;

	public UserEntity(int id, String name, String fullName, String email, String phone, String password, String address, int typeId) {
		super(id, name);
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.address = address;
		this.typeId = typeId;
		this.phone = phone;
	}

	// public UserEntity(String id, String name, String email, String password,
	// String path) {
	// super(id, name);
	// this.email = email;
	// this.password = password;
	// this.imageEntity = new ImageEntity(null, path, null, null);
	// }
	//
	// public UserEntity(String id, String name, String email, String password,
	// ImageEntity image) {
	// super(id, name);
	// this.email = email;
	// this.password = password;
	// this.imageEntity = image;
	// }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
}
