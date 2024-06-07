package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserBooked implements Serializable {
	private static final long serialVersionUID = 1L;
	private String phoneNumber;
	private String userName;
	private String idEvent;
	private String schedule;
	private String zone;
	private String idSeats;
	private LocalDateTime time;
	public UserBooked()
	{
		
	}
	
	public UserBooked(String phoneNumber, String userName, String idEvent, String schedule, String zone, String idSeats,
			LocalDateTime time) {
		super();
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.idEvent = idEvent;
		this.schedule = schedule;
		this.zone = zone;
		this.idSeats = idSeats;
		this.time = time;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(String idEvent) {
		this.idEvent = idEvent;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
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
