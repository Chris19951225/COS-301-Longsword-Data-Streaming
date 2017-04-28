#!/bin/bash

nohup ./nsq-1.0.0-compat.linux-amd64.go1.8/bin/nsqlookupd &
nohup ./nsq-1.0.0-compat.linux-amd64.go1.8/bin/nsqd --lookupd-tcp-address=127.0.0.1:4160 &
nohup ./nsq-1.0.0-compat.linux-amd64.go1.8/bin/nsqadmin --lookupd-http-address=127.0.0.1:4161 &
