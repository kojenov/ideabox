# Python app

If you don't want to create a Vagrant machine (see the top level README.md), you can set up this app manually as follows. These steps were tested on Ubuntu 18.04.

Install Python 3 and stuff
```
apt-get install -yq python3-venv
```

Initialize venv
```
python3 -m venv venv
. venv/bin/activate
```

Install Flask
```
pip install Flask
```

Run the application
```
export FLASK_APP=rce.py
flask run
```

