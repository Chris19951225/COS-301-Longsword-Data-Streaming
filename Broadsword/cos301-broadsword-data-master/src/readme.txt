Setup:
	1.run the nsq_setup.sh file (this will install nsq)
	2.run the nsq_startup.sh file (this will build nsq for execution) and do not close the 	  terminal
	3.you can also run the MockGISProducer that will act as a publisher for the GIS module
	4.run the query_resolver.py (this contains the producer and consumer)	
Explaination:
Our NSQ will subscribe to a GIS topic
The GIS module will send a location request
Our module will consume the message on the topic and parse the JSON string
Then with will compare the mac address supplied by the GIS module to the mac address in the database(output.txt)
Then it will get the latest location(according to timestamps) for that mac address
It will format the location accordinly and then publish it to the topic specifies in the destination(in this case the navigation topic)

Further Explaination
The database was produce using a stream coming in from the Aruba Engine, then Apach Flink will sanitize and format the data accordingly.
Example of input:
	{
	"Location_result": [
		{
			"msg":{
				"sta_eth_mac": {
				"addr": "c0:bd:d1:56:81:f3"
			      },
				"sta_location_x": 17.033,
				"sta_location_y": 16.5164,
				"error_level": 9,
				"associated": true,
				"campus_id": "08FBBBBF81D937759B5DAC4963DFBC1A",
				"building_id": "24C73B58A1F33C3ABE427485A9977BFF",
				"floor_id": "D635A61B06673775ADFF61D70B55785C",
				"hashed_sta_eth_mac": "A09B5D8F99F9BB8034A8ADBBEC11B24494981096",
				"geofence_ids": true,
				"loc_algorithm": "ALGORITHM_CALIBRATION",
				"longitude": -122.008,
				"latitude": 37.4129,
				"altitude": 5,
				"unit": METERS
			} 
			,"ts": 1434750262
	}

Example of output:
{ "TimeStamp": "2017-04-05 21:46:52.584", "MacAddress": "b6:f0:c4:ab:1f:e2", "longitude": "-122.34", "latitude": "32.423429", "x": "5", "y": "15" }
