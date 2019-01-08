package com.radius.agent.search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.radius.agent.property.Property;

/**
 * This class contains the logic to search for a property
 * @author Mohsin
 *
 */
public class Search {
	
	public static TreeMap<Integer, List<Property>> propertyIndex= new TreeMap <>();
	
	/**
	 * Driver method to find matching properties
	 * @param params
	 * @return
	 */
	public Map<Property, Integer> getMatchingProperties (SearchParams params) {
		
		int minHash = getMinHash(params);
		int maxHash = getMaxHash(params);
		List<Property> searchResults = getSearchResults(propertyIndex.subMap(minHash, true, maxHash, true));
		Map<Property, Integer> propertyMatch = new HashMap<>();
		
		for (Property property : searchResults) {
			if (!isFilteredByDistance(searchResults, property, propertyMatch, params)) {
				if (!isFilteredByBedrooms(property, propertyMatch, params)) {
					if (!isFilteredByBathrooms(property, propertyMatch, params)) {
						filterByPrice(property, propertyMatch, params);
					}
				}
				
			}
		}
		return propertyMatch;
	}
	
	/**
	 * This method converts the filtered submap into a list of properties
	 * @param map
	 * @return list of properties
	 */
	private List<Property> getSearchResults(NavigableMap<Integer, List<Property>> map) {
		
		List<Property> searchResults = new LinkedList<>();
		for (Map.Entry<Integer, List<Property>> entry : map.entrySet())
		{
			searchResults.addAll(entry.getValue());
		}
		return searchResults;
	}
	
	/**
	 * This method also assigns a match score based on the number of bedrooms
	 * @param property
	 * @param propertyMatch
	 * @param params
	 */
	private boolean isFilteredByBedrooms(Property property, Map<Property, Integer> propertyMatch, SearchParams params) {
		int score = 0;
		if (property.getBedrooms() >= params.getMinBedrooms() && property.getBedrooms() <= params.getMaxBedrooms()) {
			score = 20;
		} else if (params.getMinBedrooms() - property.getBedrooms() <= 2 || property.getBedrooms() - params.getMaxBedrooms() <= 2) {
			score = 10;
		} else {
			propertyMatch.remove(property);
			return true;
		}
		propertyMatch.put(property, propertyMatch.get(property) + score);
		return false;
	}

	/**
	 * This method also assigns a match score based on the number of bathrooms
	 * @param property
	 * @param propertyMatch
	 * @param params
	 */
	private boolean isFilteredByBathrooms(Property property, Map<Property, Integer> propertyMatch, SearchParams params) {
		int score = 0;
		if (property.getBathrooms() >= params.getMinBathrooms() && property.getBathrooms() <= params.getMaxBathrooms() ) {
			score = 20;
		} else if (params.getMinBathrooms() - property.getBathrooms() == 1 || property.getBathrooms() - params.getMaxBathrooms() == 1 ) {
			score = 10;
		} else {
			propertyMatch.remove(property);
			return true;
		}
		propertyMatch.put(property, propertyMatch.get(property) + score);
		return false;
	}

	/**
	 * This method also assigns a match score based on the price
	 * @param property
	 * @param propertyMatch
	 * @param params
	 */
	private void filterByPrice(Property property, Map<Property, Integer> propertyMatch, SearchParams params) {
		int score = 0;
		if (params.getMinprice() == 0 || params.getMaxprice() == 0) {
			int price = params.getMinprice();
			if (price == 0) {
				price = params.getMaxprice();
			}
			if (property.getPrice() >= (0.9 * price) && property.getPrice() <= (1.1 * price)) {
				score = 30;
			}
		} else if (property.getPrice() >= params.getMinprice() && property.getPrice() <= params.getMaxprice()) {
			score = 30;
		}
		propertyMatch.put(property, propertyMatch.get(property) + score);
	}

	/**
	 * This method further filters the search results to make sure all properties are within 10km from the search point
	 * This method also assigns a match score based on the distance
	 * @param searchResults
	 * @param property
	 * @param propertyMatch
	 * @param params
	 * @return
	 */
	private boolean isFilteredByDistance(List<Property> searchResults, Property property, Map<Property, Integer> propertyMatch, SearchParams params) {
		int score = 0;
		int distance = getDistance(property.getLatitude(), property.getLongitude(), params.getLatitude(), params.getLongitude());
		if (distance < 2) {
			score = 30;
		} else if(distance > 10) {
			return true;
		}
		propertyMatch.put(property, score);
		return false;
	}
	
	/**
	 * This method calculates the distance between to coordinates
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return distance
	 */
	private int getDistance(double lat1, double lng1, double lat2, double lng2) {
		final int AVERAGE_RADIUS_OF_EARTH_KM = 6371;
	    double latDistance = Math.toRadians(lat1 - lat2);
	    double lngDistance = Math.toRadians(lng1 - lng2);

	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	      + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	      * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
	}
	
	/**
	 * This method generates the start index of the range of hashes mapped to relevant properties
	 * @param params
	 * @return min hash
	 */
	private int getMinHash(SearchParams params) {
		final double minPercent = 25.0  / 100.0;
		final int minRooms = 2;
		int minPrice =  params.getMinprice();
		minPrice = (int) (minPrice - minPrice * minPercent);
		int minHash = minPrice * 100;
		if (params.getMinBedrooms() >= 2) {
			minHash += (params.getMinBedrooms() - minRooms);
		}
		minHash = minHash * 100;
		if (params.getMinBathrooms() >= 2) {
			minHash += (params.getMinBathrooms() - minRooms);
		}
		return minHash;
	}
	
	/**
	 * This method generates the end index of the range of hashes mapped to relevant properties
	 * @param params
	 * @return max hash
	 */
	private int getMaxHash(SearchParams params) {
		final double maxPercent = 25.0  / 100.0;
		final int maxRooms = 2;
		int maxPrice =  params.getMaxprice();
		maxPrice = (int) (maxPrice + maxPrice * maxPercent);
		int maxHash = maxPrice * 100 + (params.getMaxBedrooms() + maxRooms);
		maxHash = maxHash * 100 + (params.getMaxBathrooms() + maxRooms);
		return maxHash;
	}
}
