package ru.job4j.passport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.passport.Job4jRestPassportApplication;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.service.PassportService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Job4jRestPassportApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PassportControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PassportService passportService;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void resetDb() {
        passportService.deleteAll();
    }

    @Test
    public void createPassportTest() throws Exception {

        Calendar date = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        date.set(2022, Calendar.JANUARY, 14);

        Passport newPassport = Passport.of(1234, 123456, "user1", "user1", date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        mockMvc.perform(post("/passport/save")
                        .content(objectMapper.writeValueAsString(newPassport))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.series").value(newPassport.getSeries()))
                .andExpect(jsonPath("$.number").value(newPassport.getNumber()))
                .andExpect(jsonPath("$.name").value(newPassport.getName()))
                .andExpect(jsonPath("$.surName").value(newPassport.getSurName()))
                .andExpect(jsonPath("$.expires").value(dateFormat.format(newPassport.getExpires().getTime())));

    }

}