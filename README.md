# Radius-agent

#To run the Applicaton:

1. Open command prompt and navigate to the root directory of the project
2. Execute "mvn package" to compile and build the project
3. Execute "java -jar target\radius.agent-0.0.1-SNAPSHOT.jar" to run the application

#How to use the application

  - Adding a new property
    Enter 1 in the main menu to add a new property
    Enter data when prompted
  
  - Searching for a property
    Enter 2 in the main menu to search for a property
    Enter data when prompeted
    If min or max of an attribute is to be skipped enter 0
    Price should be entered in positive integer values

#Assumptions taken:
1. Number of bedrooms and bathrooms are limited to a maximum of 99
2. Distance is measured in kilometers
3. Price will be a whole number
4. All values entered will be positive

#Algorithm
- When user adds a new property a hash is generated to index properties for fast retrieval.
- The hash is a combination of price, number of bedrooms, number of bathrooms.
  Eg: if price is 2000 and there are 2 bedrooms and 4 bathrooms, the hash will be 20000204
- The property entered is mapped to the hash and stored in ascending order in a sortedMap implementation TreeMap.
- The treeMap allows retrieval of all the relevant properties in O(1) time.
- When user searches for a property a minHash and maxHash is generated like above.
  Eg: let min price = 100, max price = 500, min bed = 1, max bed = 10, min bath =1 and max bath = 10
      then minHash = 1000101 and maxHash = 5001010
      all properties with hash in between 1000101 and 5001010 are selected.
- All properties in the above range are then filtered and scored using distance, price, number of bedrooms and number of bathrooms.
