package ru.job4j.passport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${passportURL}")
    private String passportURL;

    @Autowired
    public PassportControllerApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        Passport rsl = restTemplate.postForObject(passportURL + "/save", passport, Passport.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Passport passport) {
        restTemplate.put(passportURL + "/update", passport);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam int id) {
        restTemplate.delete(passportURL + "/delete?id={id}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find-all")
    public List<Passport> findAll() {
        return restTemplate.exchange(
                passportURL + "/find-all",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }
        ).getBody();
    }

    @GetMapping("/find")
    public List<Passport> findBySeries(@RequestParam int series) {
        return restTemplate.exchange(
                passportURL + "/find?series={series}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }, series
        ).getBody();
    }

    @GetMapping("/unavailable")
    public List<Passport> findUnAvailable() {
        return restTemplate.exchange(
                passportURL + "/unavailable",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }
        ).getBody();
    }

    @GetMapping("/find-replaceable")
    public List<Passport> findReplaceable() {
        return restTemplate.exchange(
                passportURL + "/find-replaceable",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Passport>>() { }
        ).getBody();
    }

}