#!/bin/bash
# A script used by ServoController to control a servo

value=200
pinNo=0
echo $pinNo=$1 > /dev/servoblaster
