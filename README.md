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
   1. Open browser developer tools and analyze the `user` cookie
      1. What does it look like?
      2. Is it encoded in some way?
      3. What do you see if you decode it?
   2. Can you modify the cookie and elevate privileges?
3. Go to *secure* and check the cookie
   1. Can you spoof it now? Why not?
   2. Look at the source code in `web/node` to understand why.

### Denial of service (Java, native serialization)

1. Go to http://localhost:3003/ideabox
2. Go to *Factory* 
   1. Download an object and store it on disk.
   2. What does it look like? (native Java serialized object).
3. Go to *Store*
   1. Upload an object and see what happens (all should be good)
4. Go to *Hacktory* and get the payloads
   1. Go back to *Store* and upload.
   2. What's going on?
5. Go to *Secure Store* and try the payloads
   1. The application rejects malicious stuff.
   2. Look at the code in `web/java/WEB-INF/classes` to understand why.

*Note: you can access Tomcat Manager at http://localhost:3003/manager Of course it's secure! The user name is 'admin', and the password is 'admin' :)*

### Remote code execution (Python, pickle)

1. Go to http://localhost:3005
2. Look at the `session` cookie. Does it look like, maybe, Python serialized data?
3. Go to `web/python` and generate a payload: `python3 payload.py`
4. Fire up a local netcat listener: `nc -nlvp 1337`
5. In the browser, replace the `session` cookie with the payload and reload the page
6. Did netcat get a connection? It's a reverse shell! Run a few shell command and see.
7. Look at the code in `web/python` to understand why
