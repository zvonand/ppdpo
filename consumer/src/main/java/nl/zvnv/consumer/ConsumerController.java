package nl.zvnv.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@EnableScheduling
public class ConsumerController {
    Logger logger = LoggerFactory.getLogger(ConsumerController.class);
    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    public ConsumerController(WebClient.Builder webClientBuilder,
                               ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.loadBalancedWebClientBuilder = webClientBuilder;
        this.lbFunction = lbFunction;
    }

    @RequestMapping("/test")
    public String test() {
        return loadBalancedWebClientBuilder.build().get().uri("http://producer/")
                .retrieve().toEntity(String.class)
                .block().getBody();
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduled() {
        String result = loadBalancedWebClientBuilder.build().get()
                .uri("http://producer/convert/from/USD/to/RUB?value=10")
                .retrieve().toEntity(String.class).block().getBody();
        logger.info(result);
    }
}
