
Vagrant.configure(2) do |config|
  config.vm.box = "ubuntu/bionic64"

  # node.js
  config.vm.network "forwarded_port", guest: 3000, host: 3001

  # Tomcat
  config.vm.network "forwarded_port", guest: 8080, host: 3002

  # Flask
  config.vm.network "forwarded_port", guest: 5000, host: 3004

  config.vm.provider "virtualbox" do |vb|
    vb.memory = "2048"
    vb.cpus = 2
    vb.name = "ideabox"
  end

  # Place the files
  config.vm.provision "file", source: "web",   destination: "$HOME/web"

  # Initial provisioning
  config.vm.provision "shell", path: "bootstrap.sh"

  # Start services
  config.vm.provision "shell", run: "always", inline: <<-EOF
    cd /home/vagrant/web/node
    nohup nodejs app.js &
  EOF

  config.vm.provision "shell", inline: "/opt/tomcat/bin/startup.sh", run: "always"

  config.vm.provision "shell", run: "always", inline: <<-EOF
    cd /home/vagrant/web/python
    . venv/bin/activate
    gunicorn -b 0.0.0.0:5000 rce:app -D
  EOF

  # Claim success
  config.vm.provision "shell", run: "always", inline: <<-EOF
    echo "Node   : http://localhost:3002"
    echo "Java   : http://localhost:3003/ideabox"
    echo "Python : http://localhost:3005"
  EOF


end
