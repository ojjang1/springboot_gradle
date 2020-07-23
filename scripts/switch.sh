#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)  #현재 stop.sh가 속해있는 경로를 찾음
source ${ABSDIR}/profile.sh    # java의 일종에 import 구문

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
    echo "> 엔진엑스 Reload"
    sudo service nginx reload
}