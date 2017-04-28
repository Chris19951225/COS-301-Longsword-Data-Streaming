import nsq
import json

address_port = 'http://127.0.0.1:4161'

def publish(src, dest, msgtype, content):
  result="{\"src\":\""+src+"\"}"#,\"dest\":\""+dest+"\",\"msgType\":\""+msgtype+"\",,\"queryType\":\"getCurrentLocation\",\"content\":\""+content+"\"} }"
  return result
  
def Searcher(mac_string):
  file = open("output.txt", "r")
  lines = file.readlines()

  for line in lines:
    if mac_string in line:
      parsed_json = json.loads(line)
      mac=parsed_json["MacAddress"]
      longitude=parsed_json["longitude"]
      latitude=parsed_json["latitude"]
      x=parsed_json["x"]
      y=parsed_json["y"]
      result=("{\"MacAddress\":\""+mac+"\",\"Longitude\":\""+longitude+"\",\"Latitude\":\""+latitude+"\",\"x\":\""+x+"\",\"y\":\""+y+"\"}")
      return result
  file.close()
m="";
def h(message):
  print(message.body)
  #obj = json.loads(message.body)
  if "src" in message.body:
#    print obj['content']['mac']
    #location = Searcher(obj['content']['mac'])
    #src = obj['src']
    ##dest = obj['dest']
   # msgtype = "response"
    #content = location
    #m=publish(src, dest, msgtype, content)
    print(message.body)
    return True


f = nsq.Reader(message_handler=h, lookupd_http_addresses=[address_port],
                topic='navigation', channel='navup', lookupd_poll_interval=15)
#tornado.ioloop.IOLoop.instance().run_sync(do_pub(m))
#print("wrote one message to nsq")
nsq.run()