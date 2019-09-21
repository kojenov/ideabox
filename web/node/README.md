# Node.js app

If you don't want to create a Vagrant machine (see the top level README.md), you can set up this app manually as follows. These steps were tested on Ubuntu 18.04.

Install Node.js and npm
```
apt-get install -yq nodejs npm
```

Initialize the app
```
npm init --yes
```

Install Express framework and required libraries
```
npm install express cookie-parser --save
```

Run the application
```
nodejs app.js
```
