package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	private String eventId;
	private String name;
	private String description;
	private LocalDate date;
	private List<Schedule> schedules;

	public Event() {
	}

	public Event(String eventId, String name, String description, LocalDate date, List<Schedule> schedules) {
		super();
		this.eventId = eventId;
		this.name = name;
		this.description = description;
		this.date = date;
		this.schedules = schedules;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

}
