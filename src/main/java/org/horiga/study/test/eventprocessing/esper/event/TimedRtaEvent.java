package org.horiga.study.test.eventprocessing.esper.event;

import java.util.Date;

public class TimedRtaEvent {
	
	private String app;
	private long access;
	private Date timestamp;
	
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public long getAccess() {
		return access;
	}
	public void setAccess(long access) {
		this.access = access;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
