package com.typeb.controller;

import com.typeb.usecase.NameValidationUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloWorldController.class)
class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public NameValidationUseCase nameValidationUseCase() {
            return new NameValidationUseCase();
        }
    }

    @Test
    @DisplayName("Should return 200 OK with greeting for valid names A-M")
    void testValidNames() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "Alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Alice!"));

        mockMvc.perform(get("/hello-world").param("name", "Bob"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Bob!"));

        mockMvc.perform(get("/hello-world").param("name", "Mary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Mary!"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request for invalid names N-Z")
    void testInvalidNames() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "Nathan"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));

        mockMvc.perform(get("/hello-world").param("name", "Olivia"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));

        mockMvc.perform(get("/hello-world").param("name", "Zachary"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request for null name")
    void testNullName() throws Exception {
        mockMvc.perform(get("/hello-world"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request for empty name")
    void testEmptyName() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request for whitespace-only name")
    void testWhitespaceName() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "   "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    @DisplayName("Should handle case insensitivity - lowercase valid names")
    void testCaseInsensitivityValid() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Alice!"));

        mockMvc.perform(get("/hello-world").param("name", "bob"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Bob!"));
    }

    @Test
    @DisplayName("Should handle case insensitivity - lowercase invalid names")
    void testCaseInsensitivityInvalid() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "nathan"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));

        mockMvc.perform(get("/hello-world").param("name", "zachary"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    @DisplayName("Should handle boundary case - M (valid)")
    void testBoundaryCaseM() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "Mary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Mary!"));

        mockMvc.perform(get("/hello-world").param("name", "michael"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Michael!"));
    }

    @Test
    @DisplayName("Should handle boundary case - N (invalid)")
    void testBoundaryCaseN() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "Nathan"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));

        mockMvc.perform(get("/hello-world").param("name", "nancy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    @DisplayName("Should format names correctly - capitalize first letter")
    void testNameFormatting() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Alice!"));

        mockMvc.perform(get("/hello-world").param("name", "BOB"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Bob!"));

        mockMvc.perform(get("/hello-world").param("name", "cHaRLiE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Charlie!"));
    }

    @Test
    @DisplayName("Should handle single character valid names")
    void testSingleCharacterValid() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, A!"));

        mockMvc.perform(get("/hello-world").param("name", "M"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, M!"));
    }

    @Test
    @DisplayName("Should handle single character invalid names")
    void testSingleCharacterInvalid() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "N"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));

        mockMvc.perform(get("/hello-world").param("name", "Z"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Input"));
    }

    @Test
    @DisplayName("Should handle names with leading/trailing whitespace")
    void testNamesWithWhitespace() throws Exception {
        mockMvc.perform(get("/hello-world").param("name", "  Alice  "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Alice!"));
    }

    @Test
    @DisplayName("Should test all valid letters A through M")
    void testAllValidLetters() throws Exception {
        String[] validNames = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank",
                               "Grace", "Henry", "Iris", "Jack", "Kate", "Liam", "Mary"};

        for (String name : validNames) {
            mockMvc.perform(get("/hello-world").param("name", name))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Hello, " + name + "!"));
        }
    }

    @Test
    @DisplayName("Should test all invalid letters N through Z")
    void testAllInvalidLetters() throws Exception {
        String[] invalidNames = {"Nathan", "Olivia", "Peter", "Quinn", "Rachel", "Steve",
                                "Tina", "Uma", "Victor", "Wendy", "Xavier", "Yolanda", "Zachary"};

        for (String name : invalidNames) {
            mockMvc.perform(get("/hello-world").param("name", name))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Invalid Input"));
        }
    }
}
