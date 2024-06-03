package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ActionClient implements Serializable {
	private static final long serialVersionUID = 1L;
	private String IP;
	private String port;
	private LocalDateTime time;
	private String action;
	public ActionClient(String iP, String port, LocalDateTime time, String action) {
		super();
		IP = iP;
		this.port = port;
		this.time = time;
		this.action = action;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
