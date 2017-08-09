package toannguyen.rem.entity;

public class CommentEntity extends Entity {

	String buyer, owner;
	int estateId, buyerId, ownerId;
	String question, answer;
	
	public CommentEntity(int id, String buyer, int buyerId, String owner, int ownerId, int estateId, String question, String answer) {
		super(id);
		this.buyer = buyer;
		this.owner = owner;
		this.estateId = estateId;
		this.question = question;
		this.answer = answer;
		this.buyerId = buyerId;
		this.ownerId = ownerId;
	}

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
