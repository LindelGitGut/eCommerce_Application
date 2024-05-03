package com.example.demo.security;


import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class JWTTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistrationAndLogin() throws Exception {

        // RegisterTest
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Testuser\", \"password\": \"password\", \"confirmPassword\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

         // LoginTest
        ResultActions authorization = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Testuser\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Authorization"));

        //get token from Login Request
        String token = authorization.andReturn().getResponse().getHeader("Authorization");

        // Access Restricted Page
        mockMvc.perform(MockMvcRequestBuilders.get("/api/item")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                ).andExpect(MockMvcResultMatchers.status().isOk());

        //Test Logout
        //is found is is statuscode after login because auf Springboot default redirect after logout
        mockMvc.perform(MockMvcRequestBuilders.get("/logout").contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isFound());

        // Test Restricted Access with old Token, should work because Authorization is Stateless
        mockMvc.perform(MockMvcRequestBuilders.get("/api/item")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        // Test Restricted Access without Token, should give back forbidden
        mockMvc.perform(MockMvcRequestBuilders.get("/api/item").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
