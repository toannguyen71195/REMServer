package toannguyen.rem.entity;

import java.sql.Timestamp;

public class PhotoEntity extends Entity {

	String photo;
	Timestamp time;
	int estateID;
	
	public PhotoEntity(int id, String photo, int esId) {
		super(id);
		this.photo = photo;
		this.estateID = esId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getEstateID() {
		return estateID;
	}

	public void setEstateID(int estateID) {
		this.estateID = estateID;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
}
