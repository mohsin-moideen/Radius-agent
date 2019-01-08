package com.radius.agent;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.radius.agent.property.Property;
import com.radius.agent.search.Search;
import com.radius.agent.search.SearchParams;

/**
 * Driver class for the application
 *
 */
public class App 
{
	private static Scanner scanner;
    public static void main( String[] args )
    {
    	int input = -1;
    	scanner = new Scanner (System.in);
    	App app = new App();
    	while (input != 0) {
    		System.out.println("------------------------------");
    		System.out.println("Enter 1 to add a new property.");
    		System.out.println("Enter 2 to search for a new property.");
    		System.out.println("Enter 0 to exit the program.");
    		System.out.println("------------------------------");
    		try {
    			input = scanner.nextInt();
    		} catch (InputMismatchException ex) {
				System.out.println("Please enter a valid input!");
			}
    		if (input != 0) {
    			app.processInput(input);
    		}
    	}
    	
    }

    /**
     * Method to perform task based on selected option
     * @param input
     */
	private void processInput(int input) {
		
		switch (input) {
			case 1 : {
				Property property = new Property ();
				try {
					System.out.println("Enter the property's Id");
					property.setId(scanner.next());
					System.out.println("Enter the property's latitude");
					property.setLatitude(scanner.nextDouble());
					System.out.println("Enter the property's longitude");
					property.setLongitude(scanner.nextDouble());
					System.out.println("Enter the price of the property");
					property.setPrice(scanner.nextInt());
					System.out.println("Enter the number of bedrooms in the property");
					property.setBedrooms(scanner.nextInt());
					System.out.println("Enter the number of bathrooms in the property");
					property.setBathrooms(scanner.nextInt());
				} catch (InputMismatchException ex) {
					System.out.println("Please enter a valid input!");
					scanner.nextLine();
				}
				addPropertyToIndex(property);
				break;
			}
			case 2 : {
				SearchParams params = new SearchParams();
				System.out.println("NOTE: enter '0' if no requirement");
				try {
					System.out.println("Enter the search property's Id");
					params.setId(scanner.next());
					System.out.println("Enter the search property's latitude");
					params.setLatitude(scanner.nextDouble());
					System.out.println("Enter the search property's longitude");
					params.setLongitude(scanner.nextDouble());
					System.out.println("Enter the minium price of the search property");
					params.setMinprice(scanner.nextInt());
					System.out.println("Enter the maximum price of the search property");
					params.setMaxprice(scanner.nextInt());
					System.out.println("Enter the minumum number of bedrooms required in the property");
					params.setMinBedrooms(scanner.nextInt());
					System.out.println("Enter the maximum number of bedrooms required in the property");
					params.setMaxBedrooms(scanner.nextInt());
					System.out.println("Enter the minumum number of bathrooms required in the property");
					params.setMinBathrooms(scanner.nextInt());
					System.out.println("Enter the maximum number of bathrooms required in the property");
					params.setMaxBathrooms(scanner.nextInt());
					
				} catch (InputMismatchException ex) {
					System.out.println("Please enter a valid input!");
					scanner.nextLine();
				}
				Search search = new Search();
				print(search.getMatchingProperties(params));
				System.out.println("------------------------------");
				break;
			}
			default : {
				System.out.println("Invalid input. Please try again!");
			}
			
		}
		
	}

	/**
	 * This method prints the search result and corresponding match %
	 * @param map
	 */
	private void print(Map<Property, Integer> map) {
		for (Entry<Property, Integer> entry : map.entrySet())
		{
			if(entry.getValue() >= 40 ) {
				System.out.println(entry.getKey());
				System.out.println("Match % = " + entry.getValue());
			}
		}
	}

	/**
	 * Every property is mapped to a hash.
	 * The hash is stored in a SortedMap in the increasing order of hash value
	 * This facilitates retrieval of a list of relevant properties in in O(1) time.
	 * @param property
	 */
	private void addPropertyToIndex(Property property) {
		Integer hash = getHash(property);
		List<Property> temp = Search.propertyIndex.getOrDefault(hash, new LinkedList<Property>());
		temp.add(property);
		Search.propertyIndex.put(hash, temp);
	}

	/**
	 * This method generates a hash for indexing based on price, number of bedrooms and number of bathrooms
	 * @param property
	 * @return hash
	 */
	private Integer getHash(Property property) {
		Integer hash = property.getPrice();
		hash = hash * 100 + property.getBedrooms(); // assuming maximum of 99 bedrooms in a property
		hash = hash * 100 + property.getBathrooms(); // assuming maximum of 99 bathrooms in a property
		return hash;
	}

}
