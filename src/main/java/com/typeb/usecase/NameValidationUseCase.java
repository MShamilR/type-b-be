package com.typeb.usecase;

import com.typeb.exception.EmptyNameException;
import com.typeb.exception.InvalidNameException;
import org.springframework.stereotype.Service;

@Service
public class NameValidationUseCase {

    /**
     * Validates that the name starts with a letter between A and M (case-insensitive).
     *
     * @param name the name to validate
     * @throws EmptyNameException if name is null or empty
     * @throws InvalidNameException if name starts with N-Z
     */
    public void validateName(String name) {
        // Check if name is null or empty
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyNameException("Name cannot be null or empty");
        }

        // Extract first character and convert to lowercase for case-insensitive comparison
        char firstLetter = Character.toLowerCase(name.trim().charAt(0));

        // Check if the letter is between 'n' and 'z' (invalid range)
        if (firstLetter >= 'n' && firstLetter <= 'z') {
            throw new InvalidNameException("Name must start with a letter between A and M");
        }

        // Check if the first character is between 'a' and 'm' (valid range)
        if (firstLetter < 'a' || firstLetter > 'm') {
            throw new InvalidNameException("Name must start with a letter between A and M");
        }
    }

    /**
     * Formats the name by capitalizing the first letter and lowercasing the rest.
     *
     * @param name the name to format
     * @return formatted name (e.g., "alice" becomes "Alice")
     */
    public String formatName(String name) {
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            return trimmedName;
        }

        return Character.toUpperCase(trimmedName.charAt(0)) +
               trimmedName.substring(1).toLowerCase();
    }
}
