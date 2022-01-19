package ru.job4j.passport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.service.PassportService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/passport")
public class PassportController {

    private final PassportService passportService;

    @Autowired
    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@Valid @RequestBody Passport passport) {
        passport = passportService.save(passport);
        return new ResponseEntity<>(passport, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@Valid @RequestBody Passport passport) {
        passportService.save(passport);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(@RequestParam int id) {
        passportService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public List<Passport> findAll() {
        return passportService.findAll();
    }

    @GetMapping("/find")
    public List<Passport> findBySeries(@RequestParam int series) {
        return passportService.findBySeries(series);
    }

    @GetMapping("/unavailable")
    public List<Passport> findUnAvailable() {
        return passportService.findUnAvailable();
    }

    @GetMapping("/find-replaceable")
    public List<Passport> findReplaceable() {
        return passportService.findReplaceable();
    }

}