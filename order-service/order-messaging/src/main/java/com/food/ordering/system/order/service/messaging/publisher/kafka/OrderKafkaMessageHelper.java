package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class OrderKafkaMessageHelper {

    public <T> CompletableFuture<SendResult<String, T>> getKafkaCallback(String paymentResponseTopicName, T requestAvroModel, String orderId, String requestAvroModelName) {
        CompletableFuture<SendResult<String, T>> future = new CompletableFuture<>();
        future.thenAccept(result -> {
                    RecordMetadata metadata = result.getRecordMetadata();
                    log.info("Received successful response from kafka for order id: {}"
                                    + "Topic: {} partition {}, offset: {}, Timestamp: {}"
                            , orderId,
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset(),
                            metadata.timestamp()
                    );

                })
                .whenComplete((result, ex) -> {
                    if(ex != null) {
                        log.error("Error while sending " + requestAvroModelName +
                                " message {} to topic Error {}", requestAvroModel.toString(), paymentResponseTopicName, ex);
                    }
                });
        return future;
    }
}
