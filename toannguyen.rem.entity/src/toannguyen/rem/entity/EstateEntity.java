package toannguyen.rem.entity;

public class EstateEntity extends Entity {
	
	public static String ESTATE_LIST = "estates";
	
	UserEntity owner;
	AddressEntity address;
	EstateDetailEntity detail;
	boolean available;
	String type;

	public EstateEntity(int id, String name, UserEntity owner, AddressEntity address, boolean status, String type) {
		super(id, name);
		this.owner = owner;
		this.address = address;
		this.available = status;
		this.type = type;
	}

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public EstateDetailEntity getDetail() {
		return detail;
	}

	public void setDetail(EstateDetailEntity detail) {
		this.detail = detail;
	}

	public boolean isStatus() {
		return available;
	}

	public void setStatus(boolean status) {
		this.available = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
