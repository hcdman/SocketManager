package dto;

import java.io.Serializable;

public class Booked implements Serializable {
	private static final long serialVersionUID = 1L;
	private String seatId;
	private String zoneId;
	private String nameZone;
	private double price;
	
	public Booked() {
		
	}
	public Booked(String seatId, String zoneId, String nameZone, double price) {
		super();
		this.seatId = seatId;
		this.zoneId = zoneId;
		this.nameZone = nameZone;
		this.price = price;
	}
	public String getSeatId() {
		return seatId;
	}
	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getNameZone() {
		return nameZone;
	}
	public void setNameZone(String nameZone) {
		this.nameZone = nameZone;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
