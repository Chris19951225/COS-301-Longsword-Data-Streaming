import nsq
import json
import tornado.ioloop
import building_lookup
import floor_lookup
import location_lookup
import aruba_wrapper
import json
import argparse
import logging
import logging.config
# import malformed_test


parser = argparse.ArgumentParser(description='Serving location requests on the an NSQ topic',formatter_class=argparse.ArgumentDefaultsHelpFormatter)
parser.add_argument('--nsqlookupd_hostname',
                    help='The http hostname of the NSQ lookupd daemon', default='127.0.0.1', metavar='')
parser.add_argument('--nsqlookupd_port',
                    help='The port number of the NSQ lookupd daemon', default='4161', metavar='')
parser.add_argument('--nsqlookupd_polling_interval',
                    help='The amount of time in seconds between querying all of the supplied nsqlookupd instances. A random amount of time based on this value will be initially introduced in order to add jitter when multiple readers are running', default='15', type=int, metavar='')
parser.add_argument('--nsqd_hostname',
                    help='The http hostname of the NSQ daemon', default='127.0.0.1', metavar='')
parser.add_argument('--nsqd_port',
                    help='The http port of the NSQ daemon', default='4150', metavar='')
parser.add_argument('--subscribe_topic',
                    help='The nsq topic to listen for requests', default='data', metavar='')
parser.add_argument('--nsq_channel',
                    help='The channel within an NSQ topic to listen for requests', default='navup', metavar='')
parser.add_argument('--aruba_hostname',
                    help='The hostname of the aruba location engine', default='https://137.215.6.208', metavar='')
parser.add_argument('--aruba_port',
                    help='The port of the aruba location engine', default='80', metavar='')
parser.add_argument('--aruba_username',
                    help='The username to use the aruba location engine', default='', metavar='')
parser.add_argument('--aruba_password',
                    help='The username to use the aruba location engine', default='', metavar='')
parser.add_argument('--test', action='store_true', help='The username to use the aruba location engine')
args = parser.parse_args()

if(args.test):
    building_lookup.BuildingLookupTest().test_lookup()
    floor_lookup.FloorLookupTest().test_lookup()
    location_lookup.LocationLookupTest().test_lookup()
    exit()

logging.basicConfig(filename='error.log',level=logging.WARNING)
writer = nsq.Writer([args.nsqd_hostname+':'+args.nsqd_port])

def publish(src, dest, msgtype, content):
  result="{\"src\":\""+src+"\",\"dest\":\""+dest+"\",\"msgType\":\""+msgtype+"\",,\"queryType\":\"getCurrentLocation\",\"content\":"+content+" }"
  return result
  
def Searcher(mac_string):
  locationL= location_lookup.LocationLookup(args.aruba_hostname,args.aruba_port,args.aruba_username,args.aruba_password)
  location_json=json.loads(locationL.lookup(mac_string))
  buildingID=location_json['building_id']
  floorID=location_json['floor_id']
  x=location_json['x']
  y=location_json['y']

  
  floorL=floor_lookup.FloorLookup(args.aruba_hostname,args.aruba_port,args.aruba_username,args.aruba_password)
  floor_name=floorL.lookup(buildingID,floorID)

  buildingL= building_lookup.BuildingLookup(args.aruba_hostname,args.aruba_port,args.aruba_username,args.aruba_password)
  building_name=buildingL.lookup(buildingID)

  final_content=("{ \"mac_address\": \""+mac_string+"\" ,\"x\": "+str(x)+", \"y\": "+str(y)+", \"building_name\": \""+building_name+"\", \"floor_name\": \""+floor_name+"\"}")
  return final_content

m="";
def handler(message):
  # validator=malformed_test.validateRequest()
  # validator.validate(message)
  obj = json.loads(message.body)
  if (obj['src'] == 'data' and obj['msgType'] == 'request'):
    #print obj['content']['mac']
    location = Searcher(obj['content']['mac'])
    src = obj['src']
    dest = obj['dest']
    msgtype = "response"
    content = location
    m=publish(src, dest, msgtype, content)
    tornado.ioloop.PeriodicCallback(pub_message(m,dest), 1000).start()
    return True
def pub_message(message,destination):
  writer.pub(destination,str(message), finish_pub)
def finish_pub(conn, data):
  print(data)
 
r = nsq.Reader(message_handler=handler, lookupd_http_addresses=[args.nsqlookupd_hostname+':'+args.nsqlookupd_port],
topic=args.subscribe_topic, channel=args.nsq_channel, lookupd_poll_interval=args.nsqlookupd_polling_interval)
nsq.run()

