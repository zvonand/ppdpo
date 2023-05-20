package nl.zvnv.producer;

import nl.zvnv.producer.utils.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    @Value("${spring.cloud.zookeeper.discovery.instance-id}")
    private String instanceId;

    @GetMapping("/")
    public String test() {
        return String.format("%s started", instanceId);
    }

    @GetMapping(value="/convert/from/{from}/to/{to}", produces="application/json")
    public String convert (@PathVariable String from, @PathVariable String to, double value) {
        Currency fromCurrency = Currency.valueOf(from);
        Currency toCurrency = Currency.valueOf(to);

        double res = value / fromCurrency.unitsPerUSD * toCurrency.unitsPerUSD;
        return String.format("{\"%s\": \"%f\", \"instanceId\": \"%s\"}", toCurrency.name(), res, instanceId);
    }
}
