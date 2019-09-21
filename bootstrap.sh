#!/bin/bash


echo "Installing Ubuntu updates..."
apt-get update -yqq
apt-get dist-upgrade -yq


### Node.js

cd /home/vagrant/web/node

echo "Installing Node.js..."
apt-get install -yq nodejs npm
npm init --yes
npm install express cookie-parser pug --save


### Tomcat

echo "Installing Java... this can take several minutes, please don't kill me"
apt-get install -yq default-jdk
groupadd tomcat
useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat

echo "Installing Tomcat..."
cd /tmp

# download and unpack Tomcat
TOMCAT_DOWNLOAD=`curl -s https://tomcat.apache.org/download-90.cgi | egrep -o 'http.*apache-tomcat-9\.[0-9]*\.[0-9]*\.tar\.gz' | head -1`
curl -s -O "$TOMCAT_DOWNLOAD"
mkdir -p /opt/tomcat
tar xzf apache-tomcat-9*tar.gz -C /opt/tomcat --strip-components=1

# copy and compile ideabox
cd /opt/tomcat
mkdir -p webapps/ideabox
cp -a /home/vagrant/web/java/* webapps/ideabox/
cd webapps/ideabox/WEB-INF/classes
export CLASSPATH=/opt/tomcat/lib/servlet-api.jar:.
javac *.java

# set permissions
chgrp -R tomcat /opt/tomcat
cd /opt/tomcat
chmod -R g+r conf
chmod g+x conf
chown -R tomcat webapps/ work/ temp/ logs/

# remove unneeded applications
cd /opt/tomcat/webapps
rm -rf docs/ examples/ host-manager/

# create restart script
echo "sudo /opt/tomcat/bin/shutdown.sh; sleep 5; sudo /opt/tomcat/bin/startup.sh" > ~vagrant/tomcat-restart
chmod +x ~vagrant/tomcat-restart

# add admin user and enable admin from another host
sed -i 's/^<\/tomcat-users>/<role rolename="manager-gui"\/><user username="admin" password="admin" roles="manager-gui"\/><\/tomcat-users>/' /opt/tomcat/conf/tomcat-users.xml
egrep -v '<Valve|allow=' /opt/tomcat/webapps/manager/META-INF/context.xml > /tmp/context.xml
mv /tmp/context.xml /opt/tomcat/webapps/manager/META-INF/context.xml


### Flask

cd /home/vagrant/web/python
echo "Installing Python 3..."
apt-get install -yq python3-venv gunicorn netcat-traditional
python3 -m venv venv
. venv/bin/activate
pip install Flask
pip install gunicorn
