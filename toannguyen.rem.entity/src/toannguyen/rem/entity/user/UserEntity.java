package toannguyen.rem.entity.user;

import toannguyen.rem.entity.Entity;
import toannguyen.rem.entity.ImageEntity;

public class UserEntity extends Entity {
	private String email;
	private String password;
	private ImageEntity imageEntity;

	public UserEntity(String id, String name, String email, String password) {
		super(id, name);
		this.email = email;
		this.password = password;
	}

	public UserEntity(String id, String name, String email, String password, String path) {
		super(id, name);
		this.email = email;
		this.password = password;
		this.imageEntity = new ImageEntity(null, path, null, null);
	}

	public UserEntity(String id, String name, String email, String password, ImageEntity image) {
		super(id, name);
		this.email = email;
		this.password = password;
		this.imageEntity = image;
	}

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

	@Override
	public String toString() {
		return this.getId() + "\n" + this.getName() + "\n" + this.email;
	}

	public ImageEntity getImage() {
		return imageEntity;
	}

	public void setImageEntity(ImageEntity image) {
		this.imageEntity = image;
	}

}
