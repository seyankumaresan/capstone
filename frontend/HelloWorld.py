from bottle import route, run 
@route('/')
def hello():
	f = open('/FrontEnd/map.html', 'r')
	msg = f.read()
	return msg
run(host='localhost', port=8080, debug=True)
