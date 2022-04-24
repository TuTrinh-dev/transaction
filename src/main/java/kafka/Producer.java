package kafka;

import com.example.grpcclient.entity.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Producer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void postTransaction(String topic, String groupId, Transaction transaction) {
        try {
            log.info("Sending data to kafka = '{}' with topic '{}'", transaction.getWallet().getName(), topic);
            ObjectMapper mapper = new ObjectMapper();
            kafkaTemplate.send(topic, groupId, mapper.writeValueAsString(transaction));
        } catch (Exception e) {
            log.error("An error occurred! '{}'", e.getMessage());
        }
    }

}
