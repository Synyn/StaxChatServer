import socket
import sys
from traceback import print_tb

HOST = "127.0.0.1"  # The server's hostname or IP address
PORT = 8080  # The port used by the server

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    mess = b"""{
"messageType" : "LOGIN",
"body" : {"username" : "admin", "password" : "test123"}

}"""
    s.sendall(mess)
    # print(str(data))
    while True:
        data = s.recv(1024)
        if data:
            print("Data -> " + str(data))
        else:
            sys.exit(0)