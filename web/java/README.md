# Java app

If you don't want to create a Vagrant machine (see the top level README.md), you can set up this app manually as follows. These steps were tested on Ubuntu 18.04.

Install JDK
```
apt-get install -yq default-jdk
```

Add tomcat user
```
groupadd tomcat
useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
```

Install Tomcat
```
TOMCAT_DOWNLOAD=`curl -s https://tomcat.apache.org/download-90.cgi | egrep -o 'http.*apache-tomcat-9\.[0-9]*\.[0-9]*\.tar\.gz' | head -1`
curl -s -O "$TOMCAT_DOWNLOAD"
mkdir -p /opt/tomcat
tar xzf apache-tomcat-9*tar.gz -C /opt/tomcat --strip-components=1
```

Create a symlink to the current directory in Tomcat webapps

```
ln -s $PWD /opt/tomcat/webapps/ideabox
```

Compile the code

```
cd WEB-INF/classes
javac *.java
```

Change ownership and permissions in `/opt/tomcat`

```
chgrp -R tomcat /opt/tomcat
cd /opt/tomcat
chmod -R g+r conf
chmod g+x conf
chown -R tomcat webapps/ work/ temp/ logs/
```

Optional: add admin user

```
sed -i 's/^<\/tomcat-users>/<role rolename="manager-gui"\/><user username="admin" password="admin" roles="manager-gui"\/><\/tomcat-users>/' /opt/tomcat/conf/tomcat-users.xml
```

Start Tomcat

```
/opt/tomcat/bin/startup.sh
```

