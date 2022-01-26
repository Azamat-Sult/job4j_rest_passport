package ru.job4j.passport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.util.*;

@Service
@EnableScheduling
public class PassportService {

    private final PassportRepository repository;

    private final KafkaTemplate<Integer, Passport> kafkaTemplate;

    @Autowired
    public PassportService(PassportRepository repository,
                           KafkaTemplate<Integer, Passport> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Passport save(Passport passport) {
        passport = repository.save(passport);
        return passport;
    }

    public void deleteById(int id) {
        validateId(id);
        Optional<Passport> passport = repository.findById(id);
        if (passport.isPresent()) {
            repository.delete(passport.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Passport not found. Please, check id");
        }
    }

    public List<Passport> findAll() {
        return repository.findAll();
    }

    public List<Passport> findBySeries(int series) {
        validateSeries(series);
        return repository.findBySeries(series);
    }

    public List<Passport> findUnAvailable() {
        return repository.findUnAvailable();
    }

    public List<Passport> findReplaceable() {
        Calendar nextThreeMonths = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        nextThreeMonths.roll(Calendar.MONTH, 3);
        return repository.findReplaceable(nextThreeMonths);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    private void validateId(int id) {
        if (id < 1) {
            throw new NullPointerException("Passport id can`t be less than 1");
        }
    }

    private void validateSeries(int series) {
        if (series < 0 || series > 9999) {
            throw new NullPointerException("Passport series should be 0 - 9999");
        }
    }

    @Scheduled(fixedDelayString = "${sendInterval}")
    public void sendUnAvailableViaKafka() {
        List<Passport> unAvailable = findUnAvailable();
        if (!unAvailable.isEmpty()) {
            for (Passport passport : unAvailable) {
                kafkaTemplate.send("unAvailable", passport);
            }
        }
    }

}