package toannguyen.rem.entity;

public class CommentEntity extends Entity {

	String message;
	int user1, user2, estateId;
	
	public CommentEntity(int id, String message, int user1, int user2, int estateId) {
		super(id);
		this.message = message;
		this.user1 = user1;
		this.user2 = user2;
		this.estateId = estateId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getUser1() {
		return user1;
	}

	public void setUser1(int user1) {
		this.user1 = user1;
	}

	public int getUser2() {
		return user2;
	}

	public void setUser2(int user2) {
		this.user2 = user2;
	}

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}
	
	
}
