package model;

import java.util.List;

public class Zone {
	private String zoneId;
    private String name;
    private double ticketPrice;
    private int rows;
    private int column;
    private List<Seat> seats;
    public Zone()
    {
    	
    }
	public Zone(String zoneId, String name, double ticketPrice, int rows, int column, List<Seat> seats) {
		super();
		this.zoneId = zoneId;
		this.name = name;
		this.ticketPrice = ticketPrice;
		this.rows = rows;
		this.column = column;
		this.seats = seats;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
    
}
