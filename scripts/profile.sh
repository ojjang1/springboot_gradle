#!/usr/bin/env bash

# 쉬고있는 profile 찾기: real1이 사용중이면 real2가 쉬고있고, 반대면 real1이 쉬고있음

function find_idle_profile() {
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)
    # 현재 엔진엑스가 바라보고있는 스프링 부트가 정상적으로 수행중인지 확인
    # 응답값을 HttpStatus 로 받음
    # 정상이면 200 오류면 400~503 사이 , 예외면 real2를 현재 profile로 사용

    if [ ${RESPONSE_CODE} -ge 400 ] # 400보다 크면(즉, 40x/50x 에러 모두 포함)
    then
      CURRENT_PROFILE=real2
    else
      CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
      IDLE_PROFILE=real2  # 엔진엑스와 연결되지 않은 프로파일. 즉 새로 배포할 프로파일
    else
      IDLE_PROFILE=real1
    fi

    echo "${IDLE_PROFILE}"   # bash 라는 스크립트는 값을 반환하는 기능이 없어 마지막줄에 프로파일을 출력해서 이 값을 잡아서 사용
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port() {
    IDLE_PROFILE=$(find_idle_profile)  # 위에서 나온 출력값을 잡아서 표시

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}