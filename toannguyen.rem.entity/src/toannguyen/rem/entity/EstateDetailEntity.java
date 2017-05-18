package toannguyen.rem.entity;

public class EstateDetailEntity extends Entity {
	int bathroom;
	int bedroom;
	String condition;
	String description;
	int floor;
	double length;
	double width;

	public EstateDetailEntity(int id, int bathroom, int bedroom, String condition, String description, int floor,
			double length, double width) {
		super(id);
		this.bathroom = bathroom;
		this.bedroom = bedroom;
		this.condition = condition;
		this.description = description;
		this.floor = floor;
		this.length = length;
		this.width = width;
	}

	public int getBathroom() {
		return bathroom;
	}

	public void setBathroom(int bathroom) {
		this.bathroom = bathroom;
	}

	public int getBedroom() {
		return bedroom;
	}

	public void setBedroom(int bedroom) {
		this.bedroom = bedroom;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

}
