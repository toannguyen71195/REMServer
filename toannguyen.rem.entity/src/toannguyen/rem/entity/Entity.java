package toannguyen.rem.entity;

public abstract class Entity {
	private int id;
	private String name;
	public static final String STATUS_KEY = "statuskey";
	public static final String SUCCESS_KEY = "success";
	public static final String ERROR_KEY = "error";
	public static final String MESSAGE= "message";
	
	public Entity(int id) {
		this.id = id;
	}
	
	public Entity(int id, String name) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
