package model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

public class Schedule implements Serializable {
	private static final long serialVersionUID = 1L;
	private String scheduleId;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<Zone> zones;

	public Schedule() {

	}

	public Schedule(String scheduleId, LocalTime startTime, LocalTime endTime, List<Zone> zones) {
		super();
		this.scheduleId = scheduleId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.zones = zones;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

}
