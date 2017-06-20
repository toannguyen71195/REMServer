package toannguyen.rem.entity;

import java.sql.Timestamp;

public class AppointmentEntity extends Entity {

	public static final String PENDING = "Pending";
	public static final String ACCEPTED = "Accepted";
	public static final String REJECTED = "Rejected";
	public static final String COMPLETED = "Completed";
	
	String address;
	String note;
	Timestamp time;
	int status;
	UserEntity user1;
	UserEntity user2;
	
	public AppointmentEntity(int id, String name, String address, String note, Timestamp time, int status, UserEntity user1,
			UserEntity user2) {
		super(id, name);
		this.address = address;
		this.note = note;
		this.time = time;
		this.status = status;
		this.user1 = user1;
		this.user2 = user2;
	}
	
	public AppointmentEntity(String name, String address, String note, UserEntity user1,
			UserEntity user2) {
		super(0, name);
		this.address = address;
		this.note = note;
		this.status = 1;
		this.user1 = user1;
		this.user2 = user2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public UserEntity getUser1() {
		return user1;
	}

	public void setUser1(UserEntity user1) {
		this.user1 = user1;
	}

	public UserEntity getUser2() {
		return user2;
	}

	public void setUser2(UserEntity user2) {
		this.user2 = user2;
	}
	
	
}
