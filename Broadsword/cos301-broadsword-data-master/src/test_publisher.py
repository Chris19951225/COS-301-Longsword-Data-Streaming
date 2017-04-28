import nsq
import tornado.ioloop
import time

def pub_message():
  writer.pub('Data', '{"src"  : "GIS","dest" : "Data","msgType" : "request","queryType" : "give_mac", "content" : {"mac" : "aa:aa:aa:aa"} }', finish_pub)

def finish_pub(conn, data):
  print(data)

writer = nsq.Writer(['127.0.0.1:4150'])
tornado.ioloop.PeriodicCallback(pub_message, 1000).start()
nsq.run()
