package models;

public class Dictionary {
	private int id;
	private String mon;
	private String eng;
	private String descMon;
	private String descEng;
	private int createdUserId;
	private int ConfirmedUserId;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMon() {
		return mon;
	}
	public void setMon(String mon) {
		this.mon = mon;
	}
	public String getEng() {
		return eng;
	}
	public void setEng(String eng) {
		this.eng = eng;
	}
	public String getDescMon() {
		return descMon;
	}
	public void setDescMon(String descMon) {
		this.descMon = descMon;
	}
	public String getDescEng() {
		return descEng;
	}
	public void setDescEng(String descEng) {
		this.descEng = descEng;
	}
	public int getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(int createdUserId) {
		this.createdUserId = createdUserId;
	}
	public int getConfirmedUserId() {
		return ConfirmedUserId;
	}
	public void setConfirmedUserId(int confirmedUserId) {
		ConfirmedUserId = confirmedUserId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
