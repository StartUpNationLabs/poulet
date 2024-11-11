# Quickstart

This project is composed of 3 parts :
- Sensors
- Gateway
- WebApp

Launch the Webapp by deploying the 2 docker compose :
-  [Prometheus stack](../../docker-compose)
-  [Services](../../cloud/backend/docker-compose.yml)


Launch the gatway by deploying the following docker compose :

-  [Gateway](../../gateway/docker-compose.yml)
  

After this two steps you can execute the simulate.py script that will emulate data to the gateway adapter as if you were using sensors. The gateway will then compute the measures and send it to the prometheus server.


You can now explore you're metrics directly on the prometheus server : 
[http://localhost:9090](http://localhost:9090)