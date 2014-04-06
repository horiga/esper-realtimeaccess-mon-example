package org.horiga.study.test.eventprocessing.esper.event;

public class AccessEvent {

	private String app;
	private String version;
	private String userHash;
	private String region;
	private String os;

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUserHash() {
		return userHash;
	}

	public void setUserHash(String userHash) {
		this.userHash = userHash;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(app).append("/").append(version).append(";")
				.append(os).append(";").append(region).append(";")
				.append(userHash).toString();
	}

}
