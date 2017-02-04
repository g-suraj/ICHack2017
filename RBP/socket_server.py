import socket
import RPi.GPIO as GPIO
import time

chan_list = [5,6,13,19,26,16,20,21]
pin_map = {5 : 'D2', 6 : 'U1', 13 : 'U2', 19 : 'U3', 26 : 'D4',16 : 'D3', 20 : 'D1', 21 : 'U4'}
GPIO.setmode(GPIO.BCM)
GPIO.setup(chan_list, GPIO.IN)


macAddr = 'b8:27:eb:f1:bb:a6'
port = 3
backlog = 1
size = 1024

s = socket.socket(socket.AF_BLUETOOTH, socket.SOCK_STREAM, socket.BTPROTO_RFCOMM)
s.bind((macAddr, port))
s.listen(backlog)
try:
	client, address = s.accept()
	while 1:
		data = "{"
		first = true
		for pin in chan_list:
			if not first:
				data += ","
	        	data += "\"" + pin_map[pin] + "\" : "		
			data += GPIO.input(pin)
			first = false
		data += "}"
		client.send(data)
		time.wait(1)
except:
	print("Closing socket")
	client.close()
	s.close()
