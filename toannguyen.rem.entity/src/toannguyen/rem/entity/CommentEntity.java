package toannguyen.rem.entity;

import java.sql.Timestamp;

public class CommentEntity extends Entity {

	int userId, estateId;
	String comment;
	Timestamp postTime;

	public CommentEntity(int id, int userId, int estateId, String comment, Timestamp postTime) {
		super(id);
		this.userId = userId;
		this.estateId = estateId;
		this.comment = comment;
		this.postTime = postTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getPostTime() {
		return postTime;
	}

	public void setPostTime(Timestamp postTime) {
		this.postTime = postTime;
	}

}
