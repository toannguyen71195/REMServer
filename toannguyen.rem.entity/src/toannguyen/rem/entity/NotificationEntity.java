package toannguyen.rem.entity;

public class NotificationEntity extends Entity {

	UserEntity user, requestUser;
	EstateEntity estate;
	String message;
	int notiType;
	
	public NotificationEntity(int id, UserEntity user, UserEntity requestUser, EstateEntity estate, String message,
			int notiType) {
		super(id);
		this.user = user;
		this.requestUser = requestUser;
		this.estate = estate;
		this.message = message;
		this.notiType = notiType;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public UserEntity getRequestUser() {
		return requestUser;
	}

	public void setRequestUser(UserEntity requestUser) {
		this.requestUser = requestUser;
	}

	public EstateEntity getEstate() {
		return estate;
	}

	public void setEstate(EstateEntity estate) {
		this.estate = estate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getNotiType() {
		return notiType;
	}

	public void setNotiType(int notiType) {
		this.notiType = notiType;
	}
	
	
}
