package model;

public class Seat {
	private String seatId;
    private int rowNumber;
    private int seatNumber;
    private boolean isBooked;
    public Seat()
    {
    	
    }
	public Seat(String seatId, int rowNumber, int seatNumber, boolean isBooked) {
		super();
		this.seatId = seatId;
		this.rowNumber = rowNumber;
		this.seatNumber = seatNumber;
		this.isBooked = isBooked;
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
