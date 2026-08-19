#!/bin/bash
echo "Stopping Software Development Document Environment..."
PID=$(lsof -t -i:8080)
if [ -n "$PID" ]; then
    kill -9 $PID
    echo "Process $PID terminated."
else
    echo "No running SDM application found on port 8080."
fi
