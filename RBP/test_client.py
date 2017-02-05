#A client for testing the bluetooth server running on the pi
import socket

serverMACAddress = 'b8:27:eb:f1:bb:a6'
port = 3
s = socket.socket(socket.AF_BLUETOOTH, socket.SOCK_STREAM, socket.BTPROTO_RFCOMM)
s.connect((serverMACAddress,port))
s.send("Hello")
while 1:
    #client, address == s.accept()
    #data = s.recv(1024) 
    data = "Hello"
    s.send(data)
    #print(data)
s.close()
