package com.example.coronavirustracker.models;

public class LocationStats {

	private String state;
	private String region;
	private int latestTotalCases;
	private int differenceFromPreviousDay;

	public int getDifferenceFromPreviousDay() {
		return differenceFromPreviousDay;
	}

	public void setDifferenceFromPreviousDay(int differenceFromPreviousDay) {
		this.differenceFromPreviousDay = differenceFromPreviousDay;
	}

	public int getLatestTotalCases() {
		return latestTotalCases;
	}

	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", region=" + region + ", latestTotalCases=" + latestTotalCases + "]";
	}

}
