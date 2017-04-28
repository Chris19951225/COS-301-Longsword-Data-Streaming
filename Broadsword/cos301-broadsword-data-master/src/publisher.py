###############################################################################

import nsq
import json
import tornado

def publish(src, dest, msgType, content):
    
    data = {}
    data['MacAddress'] = content['MacAddress']
    data['longitude'] = content['longitude']
    data['latitude'] = content['latitude']
    data['distance'] = content['distance']
    
    #should any filtering take place?
    
    json_location = json.loads(json.dumps(data))


    def publish_destination():
        writer.pub(dest, json.dumps(json_location), finish_pub)
    
    
    def finish_pub(conn, data):
        print(data)
        tornado.ioloop.IOLoop.current().stop()

    
    
    writer = nsq.Writer(['127.0.0.1:4150'])
    tornado.ioloop.PeriodicCallback(publish_destination,1000).start()
    nsq.run()
    
###############################################################################









#Example - this just demonstrates how the publish fucntion will be called 
data = {}
data['MacAddress'] = "1e:u6:4a:r4:4e:34"
data['queryType'] = "requestLocation"
data['longitude'] = "-123.0342"
data['latitude'] = "33.4129"
data['distance'] = "20"


json_obj = json.loads(json.dumps(data))

publish('network', 'user', 'request', json_obj)




    
