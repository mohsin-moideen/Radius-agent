package com.radius.agent.property;

/**
 * Class to represent a property.
 * 
 * @author Mohsin
 *
 */
public class Property extends Rental {
	
	private Integer bedrooms;
	private Integer bathrooms;
	private Double latitude;
	private Double longitude;
	
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getBedrooms() {
		return bedrooms;
	}
	public void setBedrooms(Integer bedrooms) {
		this.bedrooms = bedrooms;
	}
	public Integer getBathrooms() {
		return bathrooms;
	}
	public void setBathrooms(Integer bathrooms) {
		this.bathrooms = bathrooms;
	}
	
	@Override
	public String toString() {
		return "id = " + this.id + " price = " + this.price + " latitude = " + this.latitude + " longitude = " + this.longitude + " bedrooms = " + this.bedrooms + " bathrooms = " + this.bathrooms;
	}
	
}
