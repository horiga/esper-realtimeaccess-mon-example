package org.horiga.study.test.eventprocessing.esper.event;

public class TpsEvent {
	
	String procedure;
	Long elapsed;
	int status;
	
	public String getProcedure() {
		return procedure;
	}
	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}
	public Long getElapsed() {
		return elapsed;
	}
	public void setElapsed(Long elapsed) {
		this.elapsed = elapsed;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
