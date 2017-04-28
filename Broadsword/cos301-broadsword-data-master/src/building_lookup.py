import aruba_wrapper
import json

class BuildingLookup:
    def __init__(self, hostname, port, username, password):
        self.aruba_handle = aruba_wrapper.Aruba(hostname,port,username,password)

    def get_json(self):
        raw_json = self.aruba_handle.get("/api/v1/building")
        return json.loads(raw_json)

    def lookup(self,building_id):
        obj = self.get_json()
        for field in obj['Building_result']:
            if 'msg' in field:
                if building_id in field['msg']['building_id']:
                    return field['msg']['building_name']

class BuildingLookupTest:
    def __init__(self):
        self.bl = BuildingLookup("127.0.0.1","80","","")

    def mock_get_json(self):
        return json.loads(open('mock_building_json', 'r').read())

    def test_lookup(self):
        func_type = type(self.bl.get_json);
        self.bl.get_json = self.mock_get_json
        if(self.bl.lookup("83393A922FB249C1929B95393A2AAFDA")=="3600-RFBOX"):
            print "simple mock test passed"
        else:
            print "simple mock test failed"
