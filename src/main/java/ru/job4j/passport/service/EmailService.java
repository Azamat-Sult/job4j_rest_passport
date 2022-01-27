package ru.job4j.passport.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.job4j.passport.model.Passport;

import java.util.GregorianCalendar;

@EnableKafka
@Service
public class EmailService {

    @KafkaListener(topics = "unAvailable")
    public void unAvailableListener(ConsumerRecord<Long, Passport> record) {
        System.out.print("E-mail service: "
                + new GregorianCalendar().getTime()
                + " - Expired passport received: ");
        System.out.println(record.value());
    }

}