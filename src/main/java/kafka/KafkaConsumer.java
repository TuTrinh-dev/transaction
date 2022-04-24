package kafka;

import com.example.grpcclient.dto.TransactionRequest;
import com.example.grpcclient.dto.TransactionResp;
import com.example.grpcclient.entity.Transaction;
import com.example.grpcclient.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@Slf4j
public class KafkaConsumer {

    @Autowired
    TransactionService transactionService;

    private Transaction transactionFromKafka = new Transaction();

    @KafkaListener(topics = "kafka.post.brand", groupId = "inventories")
    public void processPostTransaction(String brandJSON) {
        log.info("received content = '{}'", brandJSON);
        try {
            ObjectMapper mapper = new ObjectMapper();
            TransactionRequest transactionRequest = mapper.readValue(brandJSON, TransactionRequest.class);
            TransactionResp transactionResp = transactionService.addTransactionByWalletId(transactionRequest);
            log.info("Success process brand '{}' with topic '{}'", transactionResp.getAmount(), "inventories.kafka.post.brand");
        } catch (Exception e) {
            log.error("An error occurred! '{}'", e.getMessage());
        }
    }
}
