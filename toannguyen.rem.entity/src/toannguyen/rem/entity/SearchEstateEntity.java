package toannguyen.rem.entity;

public class SearchEstateEntity {
	AddressEntity address;
	String type;
	int areaMin, areaMax;
	int priceMin, priceMax;
	int floarMin, floorMax;
	int bedMin, bedMax;
	int bathMin, bathMax;
	String direction;
	int userID;

	public SearchEstateEntity(EstateEntity entity) {
		address = entity.getAddress();
		type = entity.getType();
		setAreaRange(entity);
		setPriceRange(entity);
		setFloarRange(entity);
		setBedRange(entity);
		setBathRange(entity);
		direction = entity.getDetail().getCondition();
	}
	
	public SearchEstateEntity(AddressEntity entity, String type) {
		address = entity;
		this.type = type;
		setAreaRange(null);
		setPriceRange(null);
		setFloarRange(null);
		setBedRange(null);
		setBathRange(null);
		direction = "";
	}

	private void setBathRange(EstateEntity entity) {
		bathMax = -1;
		bathMin = -1;
		if (entity == null) {
			return;
		}
		switch (entity.getDetail().getBedroom()) {
		case 1:
			bathMin = 1;
			bathMax = 1;
			break;
		case 2:
			bathMin = 1;
			break;
		case 3:
			bathMin = 2;
			break;
		case 4:
			bathMin = 3;
			break;
		case 5:
			bathMin = 4;
			break;
		case 6:
			bathMin = 5;
			break;
		default:
			bathMin = -1;
		}

	}

	private void setBedRange(EstateEntity entity) {
		bedMax = -1;
		bedMin = -1;
		if (entity == null) {
			return;
		}
		switch (entity.getDetail().getBedroom()) {
		case 1:
			bedMin = 1;
			bedMax = 1;
			break;
		case 2:
			bedMin = 1;
			break;
		case 3:
			bedMin = 2;
			break;
		case 4:
			bedMin = 3;
			break;
		case 5:
			bedMin = 4;
			break;
		case 6:
			bedMin = 5;
			break;
		default:
			bedMin = -1;
		}
	}

	private void setFloarRange(EstateEntity entity) {
		// So tang
		// 0
		// 1
		// 1+
		// 2+
		// 3+
		// 4+
		// 5+
		floarMin = -1;
		if (entity == null) {
			return;
		}
		switch (entity.getDetail().getFloor()) {
		case 1:
			floarMin = 0;
			floorMax = 0;
			break;
		case 2:
			floarMin = 1;
			floorMax = 1;
			break;
		case 3:
			floarMin = 1;
			floorMax = -1;
			break;
		case 4:
			floarMin = 2;
			floorMax = -1;
			break;
		case 5:
			floarMin = 3;
			floorMax = -1;
			break;
		case 6:
			floarMin = 4;
			floorMax = -1;
			break;
		case 7:
			floarMin = 5;
			floorMax = -1;
			break;
		default:
			floarMin = -1;
		}
	}

	private void setPriceRange(EstateEntity entity) {
		// Gia (trieu dong)
		// < 500
		// 500 - 800
		// 800 - 1200
		// 1200 - 2000
		// 2000 - 3000
		// 3000 - 5000
		// 5000 - 7000
		// 7000 - 10000
		// 10000 - 20000
		// \> 20000
		priceMin = -1;
		if (entity == null) {
			return;
		}
		switch ((int) entity.getPrice()) {
		case 1:
			priceMin = 0;
			priceMax = 500;
			break;
		case 2:
			priceMin = 500;
			priceMax = 800;
			break;
		case 3:
			priceMin = 800;
			priceMax = 1200;
			break;
		case 4:
			priceMin = 1200;
			priceMax = 2000;
			break;
		case 5:
			priceMin = 2000;
			priceMax = 3000;
			break;
		case 6:
			priceMin = 3000;
			priceMax = 5000;
			break;
		case 7:
			priceMin = 5000;
			priceMax = 7000;
			break;
		case 8:
			priceMin = 7000;
			priceMax = 10000;
			break;
		case 9:
			priceMin = 10000;
			priceMax = 20000;
			break;
		case 10:
			priceMin = 20000;
			priceMax = -1;
			break;
		default:
			priceMin = -1;
		}

	}

	private void setAreaRange(EstateEntity entity) {
		// Dien tich (m2)
		// < 30
		// 30 - 50
		// 50 - 80
		// 80 - 120
		// 120 - 200
		// 200 - 300
		// 300 - 500
		// \> 500
		areaMin = -1;
		if (entity == null) {
			return;
		}
		switch ((int) entity.getArea()) {
		case 1:
			areaMin = 0;
			areaMax = 30;
			break;
		case 2:
			areaMin = 30;
			areaMax = 50;
			break;
		case 3:
			areaMin = 50;
			areaMax = 80;
			break;
		case 4:
			areaMin = 80;
			areaMax = 120;
			break;
		case 5:
			areaMin = 120;
			areaMax = 200;
			break;
		case 6:
			areaMin = 200;
			areaMax = 300;
			break;
		case 7:
			areaMin = 300;
			areaMax = 500;
			break;
		case 8:
			areaMin = 500;
			areaMax = -1;
			break;
		default:
			areaMin = -1;
		}
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAreaMin() {
		return areaMin;
	}

	public void setAreaMin(int areaMin) {
		this.areaMin = areaMin;
	}

	public int getAreaMax() {
		return areaMax;
	}

	public void setAreaMax(int areaMax) {
		this.areaMax = areaMax;
	}

	public int getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(int priceMin) {
		this.priceMin = priceMin;
	}

	public int getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(int priceMax) {
		this.priceMax = priceMax;
	}

	public int getFloarMin() {
		return floarMin;
	}

	public void setFloarMin(int floarMin) {
		this.floarMin = floarMin;
	}

	public int getFloorMax() {
		return floorMax;
	}

	public void setFloorMax(int floorMax) {
		this.floorMax = floorMax;
	}

	public int getBedMin() {
		return bedMin;
	}

	public void setBedMin(int bedMin) {
		this.bedMin = bedMin;
	}

	public int getBedMax() {
		return bedMax;
	}

	public void setBedMax(int bedMax) {
		this.bedMax = bedMax;
	}

	public int getBathMin() {
		return bathMin;
	}

	public void setBathMin(int bathMin) {
		this.bathMin = bathMin;
	}

	public int getBathMax() {
		return bathMax;
	}

	public void setBathMax(int bathMax) {
		this.bathMax = bathMax;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	
}
