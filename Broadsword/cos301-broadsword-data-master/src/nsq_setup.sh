#!/bin/bash

wget https://s3.amazonaws.com/bitly-downloads/nsq/nsq-1.0.0-compat.linux-amd64.go1.8.tar.gz
tar -xf nsq-1.0.0-compat.linux-amd64.go1.8.tar.gz
sudo cp nsq-1.0.0-compat.linux-amd64.go1.8/bin/{nsqd,nsqlookupd} /usr/local/bin
sudo apt-get install python-pip
sudo pip install setuptools
sudo pip install pynsq

