#!/bin/bash
#
# Run JMeter Docker image with options

NAME="jmeter"
JMETER_VERSION=${JMETER_VERSION:-"latest"}
IMAGE="anasoid/jmeter:${JMETER_VERSION}"
WORK_DIR=$(dirname $(realpath -s "$@"))
TEST_PLAN_PATH=${WORK_DIR}/$@

# Get the default network interface
default_interface=$(ipconfig | grep -A 4 "Ethernet adapter vEthernet (Default Switch)" | grep "IPv4 Address" | awk '{print $NF}')

# Use this ip address in test plan
echo "Default Ethernet Switch IPv4 Address: $default_interface"

# Finally run
MSYS_NO_PATHCONV=1 docker run --rm --name ${NAME} -i  -v ${WORK_DIR}:/jmeter/project -w ${TEST_PLAN_PATH} ${IMAGE} -t $@
