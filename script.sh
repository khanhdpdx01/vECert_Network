#!/bin/bash
CHAIN_CODE_NAME=diploma-management-chaincode
CHANNEL_NAME=mychannel
./network.sh down
./network.sh up createChannel -c ${CHANNEL_NAME} -ca
./network.sh deployCC -ccn ${CHAIN_CODE_NAME} -ccp ~/IdeaProjects/${CHAIN_CODE_NAME} -ccl java   
exec bash

