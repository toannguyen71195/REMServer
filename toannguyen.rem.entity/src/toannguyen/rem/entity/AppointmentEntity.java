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
}
