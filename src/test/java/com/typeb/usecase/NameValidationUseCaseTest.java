package com.typeb.usecase;

import com.typeb.exception.EmptyNameException;
import com.typeb.exception.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class NameValidationUseCaseTest {

    private NameValidationUseCase nameValidationUseCase;

    @BeforeEach
    void setUp() {
        nameValidationUseCase = new NameValidationUseCase();
    }

    @Test
    @DisplayName("Should accept valid names starting with A-M")
    void testValidNames() {
        // Test all valid letters A through M
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Alice"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Bob"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Charlie"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("David"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Emma"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Frank"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Grace"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Henry"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Iris"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Jack"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Kate"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Liam"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Mary"));
    }

    @Test
    @DisplayName("Should reject invalid names starting with N-Z")
    void testInvalidNames() {
        // Test all invalid letters N through Z
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Nathan"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Olivia"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Peter"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Quinn"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Rachel"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Steve"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Tina"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Uma"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Victor"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Wendy"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Xavier"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Yolanda"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Zachary"));
    }

    @Test
    @DisplayName("Should throw EmptyNameException for null name")
    void testNullName() {
        assertThrows(EmptyNameException.class, () -> nameValidationUseCase.validateName(null));
    }

    @Test
    @DisplayName("Should throw EmptyNameException for empty string")
    void testEmptyName() {
        assertThrows(EmptyNameException.class, () -> nameValidationUseCase.validateName(""));
    }

    @Test
    @DisplayName("Should throw EmptyNameException for whitespace-only string")
    void testWhitespaceName() {
        assertThrows(EmptyNameException.class, () -> nameValidationUseCase.validateName("   "));
    }

    @Test
    @DisplayName("Should handle case insensitivity - lowercase valid names")
    void testCaseInsensitivityValid() {
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("alice"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("bob"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("mary"));
    }

    @Test
    @DisplayName("Should handle case insensitivity - lowercase invalid names")
    void testCaseInsensitivityInvalid() {
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("nathan"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("zachary"));
    }

    @Test
    @DisplayName("Should handle boundary case - M (valid)")
    void testBoundaryCaseM() {
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("Mary"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("michael"));
    }

    @Test
    @DisplayName("Should handle boundary case - N (invalid)")
    void testBoundaryCaseN() {
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Nathan"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("nancy"));
    }

    @Test
    @DisplayName("Should format name correctly - capitalize first letter")
    void testFormatName() {
        assertEquals("Alice", nameValidationUseCase.formatName("alice"));
        assertEquals("Bob", nameValidationUseCase.formatName("BOB"));
        assertEquals("Charlie", nameValidationUseCase.formatName("cHaRLiE"));
        assertEquals("David", nameValidationUseCase.formatName("DAVID"));
        assertEquals("Emma", nameValidationUseCase.formatName("emma"));
    }

    @Test
    @DisplayName("Should format name with leading/trailing whitespace")
    void testFormatNameWithWhitespace() {
        assertEquals("Alice", nameValidationUseCase.formatName("  alice  "));
        assertEquals("Bob", nameValidationUseCase.formatName(" bob "));
    }

    @Test
    @DisplayName("Should handle single character valid names")
    void testSingleCharacterValid() {
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("A"));
        assertDoesNotThrow(() -> nameValidationUseCase.validateName("M"));
    }

    @Test
    @DisplayName("Should handle single character invalid names")
    void testSingleCharacterInvalid() {
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("N"));
        assertThrows(InvalidNameException.class, () -> nameValidationUseCase.validateName("Z"));
    }
}
