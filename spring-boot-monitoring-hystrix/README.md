## Links

### Eureka
* [Dashboard](http://localhost:8761)

### Producer
* [Actuator hystrix.stream](http://localhost:8091/actuator/hystrix.stream)

### Consumer
* [Demo UI](http://localhost:8080)
* [Actuator hystrix.stream](http://localhost:8090/actuator/hystrix.stream)

### Dashboards
* [Turbine turbine.stream](http://localhost:9080/turbine.stream?cluster=HYSTRIX-CLUSTER)
* [Turbine Dashboard](http://localhost:9080/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A9080%2Fturbine.stream%3Fcluster%3DHYSTRIX-CLUSTER&delay=1000&title=hystrix-cluster)
* [Hystrix Dashboard (producer-service)](http://localhost:9080/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8091%2Factuator%2Fhystrix.stream&delay=1000&title=producer-service)
* [Hystrix Dashboard (consumer-service)](http://localhost:9080/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8090%2Factuator%2Fhystrix.stream&delay=1000&title=consumer-service)

### Monitoring
* [Grafana Dashboard (User: admin / Password: admin)](http://localhost:3000)
* [Prometheus Dashboard](http://localhost:9090)
