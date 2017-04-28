import json
import aruba_wrapper

class LocationLookup:
	def __init__(self, hostname, port, username, password):
		self.aruba_handle = aruba_wrapper.Aruba(hostname,port,username,password)
 
	def get_json(self,mac_addr):
		raw_json = self.aruba_handle.get('/api/v1/location?sta_eth_mac='+mac_addr)
		return json.loads(raw_json)


	def lookup(self,mac_addrress):
		obj = self.get_json(mac_addrress)
		for field in obj['Location_result']:
			if 'msg' in field:
				return ("{\"x\": "+str(field['msg']['sta_location_x'])+", \"y\": "+str(field['msg']['sta_location_y'])+", \"building_id\": \""+field['msg']['building_id']+"\", \"floor_id\": \""+field['msg']['floor_id']+"\"}")


class LocationLookupTest:
	def __init__(self):
		self.loc = LocationLookup("127.0.0.1","80","","")

	def get_mock_json(self,mac_addr):
		raw_json = open('mock_location_json', 'r').read()
		return json.loads(raw_json)

	def test_lookup(self):
		self.loc.get_json = self.get_mock_json
		if(self.loc.lookup("58:48:22:a7:84:6b")=="{\"x\": "+str(55.566734)+", \"y\": "+str(42.82108)+", \"building_id\": \"08BEB4DE95EA3D6B999119FAA9B0F1C1\", \"floor_id\": \"0D80FE42238A3B97BB84574CF1C6B2F3\"}"):
			print "Location lookup test passed"
		else:
			print "Location lookup test failed"
