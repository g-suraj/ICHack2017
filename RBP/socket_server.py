import socket
import RPi.GPIO as GPIO
import time

chan_list = [6, 13, 19, 21, 20, 5, 16, 26]
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

# try:
#   client, address = s.accept()
#   while 1:
#     recieved = client.recv(size)
#     if recieved:
#       print(recieved)
#       data = "{"
#       first = True
#       for i in chan_list:
#         if not first:
#           data += ","
#         data += "\"" + `pin_map[i]` + "\" : " 
#         data += `GPIO.input(i)`
#         first = False
#       data += "}"
#       print(data)
#       client.sendall(data)
# except:
#   print("Closing socket")
#   client.close()
#   s.close()

def exitRoutine(client):
  print("Closing socket")
  client.close()
  s.close()
  
try:
  client, address = s.accept()
  client.setblocking(0)
  currentData = [0] * 8
  numSample = 0
  while True:
    try:
      # Phone requested
      recieved = client.recv(size)
      if recieved:
        print(recieved)
        toSend = `numSample`
        for i in currentData:
          toSend += " " + `i`
        client.sendall(toSend)
        currentData = [0] * 8
        numSample = 0
        print(toSend)
        print("done")
      else:
        exitRoutine(client)
    except:
      # no request from phone
      # print("No data!!!")
      for i, pin in enumerate(chan_list):
        currentData[i] += GPIO.input(pin)
      numSample += 1
except:
  print("Closing socket")
  s.close()
