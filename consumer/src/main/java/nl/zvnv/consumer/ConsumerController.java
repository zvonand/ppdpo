package nl.zvnv.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@RestController
@EnableScheduling
public class ConsumerController {
    Logger logger = LoggerFactory.getLogger(ConsumerController.class);
    private final WebClient.Builder loadBalancedWebClientBuilder;

    public ConsumerController(WebClient.Builder webClientBuilder,
                               ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.loadBalancedWebClientBuilder = webClientBuilder;
    }

    @RequestMapping("/test")
    public String test() {
        return Objects.requireNonNull(loadBalancedWebClientBuilder.build().get().uri("http://producer/")
                .retrieve().toEntity(String.class)
                .block()).getBody();
    }

    @Retryable(backoff = @Backoff(delay = 300))
    @Scheduled(fixedDelay = 5000)
    public void scheduled() {
        String result = Objects.requireNonNull(loadBalancedWebClientBuilder.build().get()
                .uri("http://producer/convert/from/USD/to/RUB?value=10")
                .retrieve().toEntity(String.class).block()).getBody();
        logger.info(result);
    }

    @Recover
    void recover(Exception e, String s){
        logger.error("Failed to connect to producer: " + e.getMessage());
    }
}
