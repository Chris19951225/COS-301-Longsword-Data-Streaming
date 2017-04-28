import urllib2
import logging

class Aruba:

    logging.basicConfig(filename='error.log',level=logging.WARNING)
    
    def __init__(self, host_name, port, username, password):
        self.host_name = host_name
        self.port = port
        self.username=username
        self.password=password

    "TODO: add exception handling"
    def get(self,query_string):
        
        try:
            url=self.host_name+"/"+query_string
            p = urllib2.HTTPPasswordMgrWithDefaultRealm()
            p.add_password(None, url, self.username, self.password)
            handler = urllib2.HTTPBasicAuthHandler(p)
            opener = urllib2.build_opener(handler)
            urllib2.install_opener(opener)
            return urllib2.urlopen(url).read()
        
        except Exception as e:
            logging.warning('Unable to retrieve Aruba Location information')
