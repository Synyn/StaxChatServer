import socket
def testConnect():
    hostname = "127.0.0.1"
    port = 8080

    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((hostname, port))
        s.sendall(b"Hello World !")
        s.close()


if __name__ == '__main__':
    testConnect()