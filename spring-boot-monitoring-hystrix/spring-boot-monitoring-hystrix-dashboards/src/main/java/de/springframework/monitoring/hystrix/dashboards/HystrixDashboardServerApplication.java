package de.springframework.monitoring.hystrix.dashboards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableTurbine
@SpringBootApplication
public class HystrixDashboardServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardServerApplication.class, args);
    }

}
