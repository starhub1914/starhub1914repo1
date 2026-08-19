#!/bin/bash
echo "Starting Software Development Document Environment..."
nohup java -jar target/software-development-document-environment-1.0.0-SNAPSHOT.jar --spring.profiles.active=h2 > app.log 2>&1 &
echo "Application started in background with PID $!. Log file: app.log"
