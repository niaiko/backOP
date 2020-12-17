#!/bin/bash
#changer "reglement.jar" jar par le nom dans le conteneur

PROCESS="$(ps ax | grep reglement.jar | grep -v grep | wc -l)"
CHECK="$(ls | grep reglement.jar | grep -v grep | wc -l)"
if [ $PROCESS -eq 0 ]
then
	echo "no process"
	if [ $CHECK -eq 1 ]
	then
		nohup java -XX:+UseSerialGC -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:MinHeapFreeRatio=20 -XX:MaxHeapFreeRatio=40 -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -Xss228k -XX:MaxRAM=2g -jar reglement.jar 1> output.file 2>&1 </dev/null &
		echo "launch jar with success"
	fi
else
	echo "java in process"
	ps -ef | grep reglement.jar | grep -v grep | awk '{print $2}' | xargs kill
	nohup java -XX:+UseSerialGC -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:MinHeapFreeRatio=20 -XX:MaxHeapFreeRatio=40 -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -Xss228k -XX:MaxRAM=2g -jar reglement.jar 1> output.file 2>&1 </dev/null &
	echo "relaunch jar with success"
fi
