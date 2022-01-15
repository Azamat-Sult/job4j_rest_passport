package ru.job4j.passport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.passport.model.Passport;

import java.util.List;

@RestController
@RequestMapping("/passport/api")
public class PassportControllerApi {

    private final RestTemplate restTemplate;

    private static final String API_PASSPORT_SAVE = "http://localhost:8080/passport/save";
    private static final String API_PASSPORT_UPDATE = "http://localhost:8080/passport/update";
    private static final String API_PASSPORT_DELETE = "http://localhost:8080/passport/delete?id={id}";
    private static final String API_PASSPORT_FIND_ALL = "http://localhost:8080/passport/find-all";
    private static final String API_PASSPORT_FIND_BY_SERIES = "http://localhost:8080/passport/find?series={series}";
    private static final String API_PASSPORT_FIND_UNAVAILABLE = "http://localhost:8080/passport/unavailable";
    private static final String API_PASSPORT_FIND_REPLACEABLE = "http://localhost:8080/passport/find-replaceable";

    @Autowired
    public PassportControllerApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        Passport rsl = restTemplate.postForObject(API_PASSPORT_SAVE, passport, Passport.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Passport passport) {
        restTemplate.put(API_PASSPORT_UPDATE, passport);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePerson(@RequestParam int id) {
        restTemplate.delete(API_PASSPORT_DELETE, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find-all")
    public List<Passport> findAllPerson() {
        return restTemplate.exchange(
                API_PASSPORT_FIND_ALL,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }
        ).getBody();
    }

    @GetMapping("/find")
    public List<Passport> findBySeries(@RequestParam int series) {
        return restTemplate.exchange(
                API_PASSPORT_FIND_BY_SERIES,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }, series
        ).getBody();
    }

    @GetMapping("/unavailable")
    public List<Passport> findUnAvailable() {
        return restTemplate.exchange(
                API_PASSPORT_FIND_UNAVAILABLE,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }
        ).getBody();
    }

    @GetMapping("/find-replaceable")
    public List<Passport> findReplaceable() {
        return restTemplate.exchange(
                API_PASSPORT_FIND_REPLACEABLE,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }
        ).getBody();
    }

}