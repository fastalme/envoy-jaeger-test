# Distributed Tracing with Envoy service mesh & Jaeger (Spring Boot)
Inspired in [dnivra26/envoy_distributed_tracing] repository, this is a demo project that implements exact the same idea but with Spring Boot services and the latest versions of Envoy and Jaeger at the time this repository was published. I have to do this as part of my learning path and to understand every piece of the original demo project.

Basically, this project have three [Spring Boot] services that works together simulating a very basic service architecture. It uses [Envoy] as an proxy for every service converting the entire project in a very basi service mesh. Envoy gives this deployment a way to have tracing data generated automatically with very few source code modifications (passing HTTP headers from one service to other). This generated tracing data is collected by an [Jaeger] service (using a [Zipkin] compatible endpoint) and it is also visible through its web front end. Finally, all this componentes are tied up with an Docker Compose file that put them running in a very fast way.

*Disclaimer: Even when Spring Boot has its own smooth way to integrate to a Jaeger Service (with Spring Cloud Zipkin starters and Sleuth), the purpose of this example is to show how almost any service architecture can be integrated to Jaeger using Envoy proxies.*

## Technologies and versions

- Spring Boot 2.4.3
- Envoy 1.17.1 (API v3)
- Jaeger 1.21

## Deployment information

The deployment strategy is also very similar, but I added extra port information to this diagram so it can be better undertood at first sight:

![deployment]

| Component | Description |
| ------ | ------ |
| Front Envoy | Acts as an gateway from the exterior of the application. |
| Service 1 Envoy | Acts as an gateway to reach the Service 1 from other components. Also acts as an gateway for reaching service 2 and service 3 envoys from Service 1.  |
| Service 1 | Internally, consumes Service 2 and Service 3 and returns a list of strings, as each response of the other services. |
| Service 2 Envoy | Acts as an gateway to reach the Service 2 from other components. |
| Service 2 | Identifies itself returning a simple greeting. |
| Service 3 Envoy | Acts as an gateway to reach the Service 3 from other components. |
| Service 3 | Identifies itself returning a simple greeting. |
| Jaeger | Jaeger server that is capable of recieve tracing data in Zipkin format. |

## Steps to run this example project:
1. `docker-compose build`  
2. `docker-compose up`  
3. Hit http://localhost/hello to generate some tracing data  
4. Visit http://localhost:16686 in your browser to reach the Jeager UI and see the tracing data generated

This is what the output should look like:

![tracing data]

[dnivra26/envoy_distributed_tracing]: <https://github.com/dnivra26/envoy_distributed_tracing>
[deployment]: <https://github.com/fastalme/envoy-jaeger-test/blob/master/deployment.png?raw=true>
[tracing data]: <https://github.com/fastalme/envoy-jaeger-test/blob/master/tracing-data.png?raw=true>
[envoy]: <https://www.envoyproxy.io/>
[jaeger]: <https://www.jaegertracing.io/>
[zipkin]: <https://zipkin.io/>
[spring boot]: <https://spring.io/projects/spring-boot>