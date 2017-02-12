#!/bin/bash
# A script used by ServoController to control a servo

sudo killall servod
sudo ./servod --p1pins=22

