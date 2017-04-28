#!/usr/bin/python

import socket
from random import randint
from socket import error as SocketError
import errno

def gen_random_y_coordinate():
	y = randint(1,15)
	decimal = randint(0,99)
	return str(y)+"."+str(decimal)

def gen_random_x_coordinate():
	x = randint(1,15)
	decimal = randint(0,99)
	return str(x)+"."+str(decimal)


def gen_random_macAddr():
	myList=[]
	myList.append("1e:06:2a:1c:be:3b")
	myList.append("b6:f0:c4:ab:1f:e2")
	myList.append("02:b0:94:36:27:cd")
	myList.append("52:de:0a:ac:a0:47")
	myList.append("d2:ea:da:68:1b:0a")
	num=randint(0,4)
	return str(myList[num])

HOST = ''
PORT = 3000
COUNT = 1
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM);
#from here: http://stackoverflow.com/questions/4465959/python-errno-98-address-already-in-use
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1);
s.bind((HOST, PORT))
s.listen(4);

while True:
  try:
      sock,addr = s.accept()
      send = 1
      while send!=0: 
          st = """{{Location_result": [{{"msg":{{"sta_eth_mac": {{"addr": "{}"}},"sta_location_x": "{}","sta_location_y": "{}","error_level": 9,"associated": true,"campus_id": "08FBBBBF81D937759B5DAC4963DFBC1A","building_id": "24C73B58A1F33C3ABE427485A9977BFF","floor_id": "D635A61B06673775ADFF61D70B55785C","hashed_sta_eth_mac": "A09B5D8F99F9BB8034A8ADBBEC11B24494981096","geofence_ids": true,"loc_algorithm": "ALGORITHM_CALIBRATION","longitude": -122.008,"latitude": 37.4129,"altitude": 5,"unit": METERS}} "ts": 1434750262 }}]}}""".format(gen_random_macAddr(), gen_random_x_coordinate(), gen_random_y_coordinate()   )
          send = sock.send(st.encode())
        
  except SocketError as e:
    continue #do nothing
  finally:
      sock.close();
     