# !/bin/bash
RUNNING_APPLICATION=$(docker ps | grep blue)
DEFAULT_CONF="/etc/nginx/conf.d/default.conf"

REPOSITORY=/home/ubuntu/app/zip
cd $REPOSITORY

if [ -n "$RUNNING_APPLICATION"  ];then
	echo "green Deploy..."
	docker compose build green
	docker compose up -d green

	while [ 1 == 1 ]; do
		echo "green health check...."
		REQUEST=$(docker exec nginx curl http://green:8080)
		echo $REQUEST
		if [ -n "$REQUEST" ]; then
			break ;
		fi
		sleep 3
	done;

	sed -i 's/blue/green/g' $DEFAULT_CONF
	docker exec nginx nginx -s reload
	#docker-compose restart web-server
	docker compose stop blue
else
	echo "blue Deploy..."
	echo "> 전환할 Port: $RUNNING_APPLICATION"
	docker compose build blue
	docker compose up -d blue

	while [ 1 == 1 ]; do
		echo "blue health check...."
                		REQUEST=$(docker exec nginx curl http://blue:8080)
                echo $REQUEST
		if [ -n "$REQUEST" ]; then
            break ;
        fi
		sleep 3
    done;

	sed -i 's/green/blue/g' $DEFAULT_CONF
	docker exec nginx nginx -s reload
	#docker-compose restart web-server
	docker compose stop green
fi