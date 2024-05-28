package model;

import java.util.Date;
import java.util.List;

public class Event {
	private String eventId;
    private String name;
    private String description;
    private Date date;
    private List<Schedule> schedules;
}
