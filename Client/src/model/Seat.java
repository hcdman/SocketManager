package model;

import java.io.Serializable;

public class Seat implements Serializable {
	private static final long serialVersionUID = 1L;
	private String seatId;
    private int rowNumber;
    private int seatNumber;
    private boolean isBooked;
    private String zoneId;
    public Seat()
    {
   
    }
	
	public Seat(String seatId, int rowNumber, int seatNumber, boolean isBooked, String zoneId) {
		super();
		this.seatId = seatId;
		this.rowNumber = rowNumber;
		this.seatNumber = seatNumber;
		this.isBooked = isBooked;
		this.zoneId = zoneId;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getSeatId() {
		return seatId;
	}
	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	public boolean isBooked() {
		return isBooked;
	}
	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	} 
    
}
