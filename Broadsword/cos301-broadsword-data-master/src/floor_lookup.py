import json
import aruba_wrapper

class FloorLookup:
    def __init__(self, hostname, port, username, password):
        self.aruba_handle = aruba_wrapper.Aruba(hostname,port,username,password)

    def get_json(self):
        raw_json = self.aruba_handle.get("/api/v1/floor")
        return json.loads(raw_json)

    def lookup(self,building_id,floor_id):
        obj = self.get_json()
        for field in obj['Floor_result']:
            if 'msg' in field:
                if building_id in field['msg']['building_id']:
                    if floor_id in field['msg']['floor_id']:
                        return field['msg']['floor_name']

class FloorLookupTest:
    def __init__(self):
        self.fl = FloorLookup("127.0.0.1","80","","")

    def mock_get_json(self):
        return json.loads(open('mock_floor_json', 'r').read())

    def test_lookup(self):
        self.fl.get_json = self.mock_get_json
        mock_floor_hashes = [
        "8F7C5FE07A323A6993F614F8CA89A485",
        "5B074AEF0AA63696B7276C606602F63B",
        "0B0FFB96CACD3E0D8EBC353BE8712495",
        "BC25CFC5A7703DCE8B491A0654802B1C",
        "0D80FE42238A3B97BB84574CF1C6B2F3"
        ]
        mock_building_hashes = [
        "9E4C881FFFE335F596BF9420FDED8BC7",
        "9E4C881FFFE335F596BF9420FDED8BC7",
        "9E4C881FFFE335F596BF9420FDED8BC7",
        "9E4C881FFFE335F596BF9420FDED8BC7",
        "08BEB4DE95EA3D6B999119FAA9B0F1C1"
        ]
        mock_floor_numbers = ["Floor 1","Floor 3",
        "Floor 4","Floor 2",
        "Floor 4"]
        for i in range(0,5):
            if(self.fl.lookup(mock_building_hashes[i],mock_floor_hashes[i],)
                !=mock_floor_numbers[i]):
                print "Floor lookup test failed at case:"+str(i)
        print "Floor lookup test passed"
