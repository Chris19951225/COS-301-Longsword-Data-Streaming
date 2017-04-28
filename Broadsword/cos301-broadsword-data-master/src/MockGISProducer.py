import nsq
import tornado.ioloop
import time

def pub_message():
  writer.pub('data', '{"src"  : "data","dest" : "navigation","msgType" : "request","queryType":"getCurrentLocation", "content" : {"mac" : "b6:f0:c4:ab:1f:e2"} }', finish_pub)

def finish_pub(conn, data):
  print(data)

writer = nsq.Writer(['127.0.0.1:4150'])
tornado.ioloop.PeriodicCallback(pub_message, 1000).start()
nsq.run()
