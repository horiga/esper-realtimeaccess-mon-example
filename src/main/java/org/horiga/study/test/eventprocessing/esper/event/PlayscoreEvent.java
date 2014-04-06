package org.horiga.study.test.eventprocessing.esper.event;

import java.util.Date;

public class PlayscoreEvent {
	
	/* factor is <appid>#<factor-id>*/
	private String factor;
	private String userHash;
	private Double score;
	private Date timestamp;
	
	public String getUserHash() {
		return userHash;
	}

	public void setUserHash(String userHash) {
		this.userHash = userHash;
	}

	public String getFactor() {
		return factor;
	}

	public void setFactor(String factor) {
		this.factor = factor;
	}
	
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
