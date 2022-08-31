#!/bin/bash
CHAIN_CODE_NAME=chaincode
CHANNEL_NAME=mychannel
./network.sh down
./network.sh up createChannel -c ${CHANNEL_NAME} -ca
./network.sh deployCC -ccn ${CHAIN_CODE_NAME} -ccp ~/VTS/vecert-network/chaincode -ccl java   

# peer channel fetch newest mychannel.block -o localhost:7050 --ordererTLSHostnameOverride orderer.com -c mychannel --tls --cafile /home/khanh/VTS/vecert-network/organizations/ordererOrganizations/tlsca/tlsca.example.com-cert.pem
exec bash
