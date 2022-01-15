package ru.job4j.passport.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.passport.model.Passport;

import java.util.Calendar;
import java.util.List;

public interface PassportRepository extends CrudRepository<Passport, Integer> {

    @Override
    List<Passport> findAll();

    List<Passport> findBySeries(int series);

    @Query("from Passport where expires < current_timestamp ")
    List<Passport> findUnAvailable();

    @Query("from Passport where expires between current_timestamp and :nextThreeMonths ")
    List<Passport> findReplaceable(@Param("nextThreeMonths") Calendar nextThreeMonths);

}