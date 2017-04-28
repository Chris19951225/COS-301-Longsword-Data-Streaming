import re
from datetime import datetime
import logging
logging.basicConfig(filename='error.log',level=logging.DEBUG)

class validateRequest(object):
	"""class for validating request"""
	def __init__(self):
		print "Hello"
		self.pattern = re.compile("""^{\"src\":\"(gis|navigation|data|access|users|notifications)\",\"dest\":\"(gis|navigation|data|access|users|notifications)\",\"msgType\":\"request\",\"queryType\":\"getCurrentLocation\",\"content\":{\"mac\":\"[0-9 A-F a-f]{1,2}:[0-9 A-F a-f]{1,2}:[0-9 A-F a-f]{1,2}:[0-9 A-F a-f]{1,2}:[0-9 A-F a-f]{1,2}:[0-9 A-F a-f]{1,2}\"}}$""")
	
	"""Created class for this function for speed. regular expressions are slow to compile 
		but now it is only compiled once and not every fucntion call"""
	def validate(self, request):
		request = request.replace(" ", "")
		#print request
		result = self.pattern.match(request)

		if result:
			print "passed"
			return True
		else:
			errorString = "[%s] [Malformed Data] [%s]" % (str(datetime.now() ), request)
			print errorString
			logging.error(errorString)
			return False	

#x = validateRequest()
#while True:
	#Currently invalid request due to gi5s
	#x.validate("{\"src\"  : \"gis\",\"dest\" : \"gis\",\"msgType\" : \"request\",\"queryType\":\"getCurrentLocation\", \"content\" : {\"mac\" : \"bf:f0:c4:AB:1f:e2\"} }")
