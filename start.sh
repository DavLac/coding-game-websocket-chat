#!/bin/sh

echo "Building docker images..."

./mvnw package -Pprod verify jib:dockerBuild

echo -e "\nStarting containers...\n"

docker-compose -p container -f docker-compose.yml up -d

read -n 1 -s -r -p "
----------------------------------------------------------
Application 'Action monitor app' is running! Access URLs:
Local 1 : 		http://localhost:8081/
Local 2 : 		http://localhost:8082/
ActiveMq :  	http://localhost:8161/
----------------------------------------------------------

Please wait 10 sec application is starting. Press any keys to continue...
"
