package ru.job4j.passport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.util.*;

@Service
public class PassportService {

    private final PassportRepository repository;

    @Autowired
    public PassportService(PassportRepository repository) {
        this.repository = repository;
    }

    public Passport save(Passport passport) {
        passport = repository.save(passport);
        return passport;
    }

    public boolean deleteById(int id) {
        Optional<Passport> passport = repository.findById(id);
        if (passport.isPresent()) {
            repository.delete(passport.get());
            return true;
        }
        return false;
    }

    public List<Passport> findAll() {
        return repository.findAll();
    }

    public List<Passport> findBySeries(int series) {
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

}