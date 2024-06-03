package model;

import java.io.Serializable;
import java.time.LocalDateTime;
public class UserBooked implements Serializable {
	private static final long serialVersionUID = 1L;
	private String phoneNumber;
	private String idEvent;
	private String idSchedule;
	private String idZone;
	private String idSeats;
	private LocalDateTime time;
	public UserBooked()
	{
		
	}
	
	public UserBooked(String phoneNumber, String idEvent, String idSchedule, String idZone, String idSeats,
			LocalDateTime time) {
		super();
		this.phoneNumber = phoneNumber;
		this.idEvent = idEvent;
		this.idSchedule = idSchedule;
		this.idZone = idZone;
		this.idSeats = idSeats;
		this.time = time;
	}

	public String getIdZone() {
		return idZone;
	}

	public void setIdZone(String idZone) {
		this.idZone = idZone;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(String idEvent) {
		this.idEvent = idEvent;
	}
	public String getIdSchedule() {
		return idSchedule;
	}
	public void setIdSchedule(String idSchedule) {
		this.idSchedule = idSchedule;
	}
	public String getIdSeats() {
		return idSeats;
	}
	public void setIdSeats(String idSeats) {
		this.idSeats = idSeats;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	
}
