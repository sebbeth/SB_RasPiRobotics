# SB_RaspPiRobotics



Run command:
sudo java -classpath .:classes:lib/'*' SB_RasPRobotics.PathFinder

# To compile using Intellij:
 Make sure raspberry pi fileshare is connected, then do Build => Make Project.

# To compile on the raspberry pi:
Go to the project root directory
Make sure a ./build directory exists.
run ```sudo javac -classpath .:classes:/opt/pi4j/lib/'*' -d ./build SB_RasPi_Robotics/*.java```






# To run:
```sudo java -classpath .:classes:/opt/pi4j/lib/'*' SB_RasPi_Robotics.<name of main class>

e.g.
sudo java -classpath .:classes:/opt/pi4j/lib/'*' SB_RasPi_Robotics.PathFinder```



