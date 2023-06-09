package nl.zvnv.consumer;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableRetry
//@LoadBalancerClient(name = "producer", configuration = LBConfig.class)
@LoadBalancerClient(name = "producer")
public class WebClientConfig {
//    @LoadBalanced
    @Bean
    @LoadBalanced
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}