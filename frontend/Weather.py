#!/usr/bin/python
import MySQLdb
import pygeoj
import json

from bottle import route, run, get, post, request
from urlparse import urlparse, parse_qs

CURRENT_TIME = '2017-02-12 21:00:00'

db = MySQLdb.connect(host="localhost", user="root",
			 		passwd="pass", db="black_ice_project")

cur = db.cursor()

def mapSnow(x):
	if x < -2:
		return 'Y'
	else:
		return 'N'

@route('/')
def hello():
	f = open('/FrontEnd/map_new.html', 'r')
	msg = f.read()
	return msg

# @route('http://localhost:8080/query*', 'GET')
# def temp_inquiry():

# 	cur.execute()
# 	return json.dumps("Hello World")

@route('/neighbourhoods.geojson')
def send_geojson():

	testfile = pygeoj.load(filepath="neighbourhoods.geojson");
	

	for feature in testfile:
		hood = feature.properties["HOOD"]
		time_local = CURRENT_TIME
		cur.execute('SELECT temp_c from raw_data where neighbourhood=%s', (hood,));

		#this will get list of temperature for certain hood for 24 hours
		temps = cur.fetchall();
		parsed_obj = feature.properties

		snow = [ch_temp[0] for ch_temp in temps]
		parsed_obj['SNOW'] = [mapSnow(temp)for temp in snow]

	testfile.save("./neighbourhoods.geojson")

	f = open('./neighbourhoods.geojson')
	msg = f.read()
	return msg

# @route('/neighbourhoods_1.geojson')
# def send_geojson():


# 	testfile = pygeoj.load(filepath="neighbourhoods_1.geojson");
# 	print len(testfile);

# 	for feature in testfile:
# 		hood = feature.properties["HOOD"]
# 		time_local = CURRENT_TIME
# 		cur.execute('SELECT temp_c from raw_data where neighbourhood=%s and time_local=%s', (hood, time_local,));
# 		temps = cur.fetchall();
# 		if temps:
# 			if temps[0][0] < -1:
# 				print temps[0][0]
# 				feature.properties["SNOW"] = 'Y';
# 			else:
# 				feature.properties["SNOW"] = 'N';


# 	testfile.save("./neighbourhoods_1.geojson")

# 	f = open('./neighbourhoods_1.geojson')
# 	msg = f.read()
# 	return msg

# The purpose of this function is to update the "SNOW" properties of neighbourhoods.geojson 
# file. The snow property shall be a character array that has Y/N for 24 hours starting 
# current time.
# def updateGeoJson():
# 	testfile = pygeoj.load(filepath="neighbourhoods.geojson");
# 	print len(testfile);

# 	for feature in testfile:
# 		hood = feature.properties["HOOD"]
# 		time_local = CURRENT_TIME #this time should be changed based on current time
# 		cur.execute('SELECT temp_c from raw_data where neighbourhood=%s and time_local=%s', (hood, time_local,));
# 		temps = cur.fetchall();
# 		if temps:
# 			if temps[0][0] < -1:
# 				print temps[0][0]
# 				feature.properties["SNOW"] = 'Y';
# 			else:
# 				feature.properties["SNOW"] = 'N';


# 	testfile.save("./neighbourhoods.geojson")

# 	f = open('./neighbourhoods.geojson')
# 	msg = f.read()
# 	return msg


run(host='localhost', port=8080, debug=True)

	#cur.execute()
	#return "Hello World!"
