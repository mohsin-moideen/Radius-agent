package com.radius.agent.search;

/**
 * Class to represent search params
 * @author Mohsin
 *
 */
public class SearchParams {
	
	private String id;
	private Double latitude;
	private Double longitude;
	private Integer minprice;
	private Integer maxprice;
	private Integer minBedrooms;
	private Integer minBathrooms;
	private Integer maxBedrooms;
	private Integer maxBathrooms;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getMinprice() {
		return minprice;
	}
	public void setMinprice(Integer minprice) {
		this.minprice = minprice;
	}
	public Integer getMaxprice() {
		return maxprice;
	}
	public void setMaxprice(Integer maxprice) {
		this.maxprice = maxprice;
	}
	public Integer getMinBedrooms() {
		return minBedrooms;
	}
	public void setMinBedrooms(Integer minBedrooms) {
		this.minBedrooms = minBedrooms;
	}
	public Integer getMinBathrooms() {
		return minBathrooms;
	}
	public void setMinBathrooms(Integer minBathrooms) {
		this.minBathrooms = minBathrooms;
	}
	public Integer getMaxBedrooms() {
		return maxBedrooms;
	}
	public void setMaxBedrooms(Integer maxBedrooms) {
		this.maxBedrooms = maxBedrooms;
	}
	public Integer getMaxBathrooms() {
		return maxBathrooms;
	}
	public void setMaxBathrooms(Integer maxBathrooms) {
		this.maxBathrooms = maxBathrooms;
	}
}
