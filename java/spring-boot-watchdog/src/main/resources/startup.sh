#!/bin/bash

# crontab -e
# */10 * * * * /project/watchdog/watchdog.sh
# sudo systemctl reload crond.service

ps -ef | grep "demo-app-0.0.1-SNAPSHOT.jar" | grep -v "grep"

if [ "$?" -eq 0 ]
then

# sleep
echo $(date "+%Y-%m-%d %H:%M:%S") "process already started!"

else

nohup java -jar -server /project/watchdog/demo-app-0.0.1-SNAPSHOT.jar &
echo $(date "+%Y-%m-%d %H:%M:%S") "process has been started!"

fi
