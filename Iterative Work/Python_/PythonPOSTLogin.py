
import requests

url = 'http://www.facebook.com'
values = {'username': 'EMAIL',
          'password': 'PASSWORD'}

r = requests.post(url, data=values)

print(r.content)
