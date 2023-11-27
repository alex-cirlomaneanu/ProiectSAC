import csv
import requests
import json

#add user
url = 'http://localhost:8080/api/v1/auth/register'
payload = {'firstname': "Alex", 'lastname': "Razvan", 'email': "razvanalex949@gmail.com", 'password': "1234"}

response = requests.post(url, json=payload)
print(response.text)

url = 'http://localhost:8080/api/v1/auth/authenticate'
payload = {'email': "razvanalex947@gmail.com", 'password': "1234"}

response = requests.post(url, json=payload)
data = json.loads(response.text)
token = data['token']
print(token)

map = {}

with open('resources/brand-models.txt', 'r') as file:
    for line in file:
        brand, value = line.strip().split(':')
        models = [x.strip() for x in value[1:-1].split(',')]
        map[brand] = models

map = dict(sorted(map.items()))

url = 'http://localhost:8080/api/v1/vehicles/brands/addModel'
urlBrand = 'http://localhost:8080/api/v1/vehicles/addBrand'

for brand in map:
    payload = {'brand': brand}
    response = requests.post(urlBrand, data=payload)
    print(response.text)
    for model in map[brand]:
        if model == "Altul":
            continue
        payload = {'brand': brand, 'model': model}
        response = requests.post(url, data=payload)
        print(response.text)

