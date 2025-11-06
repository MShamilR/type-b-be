package com.typeb.controller;

import com.typeb.exception.EmptyNameException;
import com.typeb.exception.InvalidNameException;
import com.typeb.core.response.ErrorResponse;
import com.typeb.core.response.HelloResponse;
import com.typeb.usecase.NameValidationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class HelloWorldController {

    private final NameValidationUseCase nameValidationUseCase;

    public HelloWorldController(NameValidationUseCase nameValidationUseCase) {
        this.nameValidationUseCase = nameValidationUseCase;
    }

    @GetMapping("/hello-world")
    public ResponseEntity<?> helloWorld(@RequestParam(required = false) String name) {
        try {
            // Validate and format the name
            nameValidationUseCase.validateName(name);
            String formattedName = nameValidationUseCase.formatName(name);

            // Return success response with formatted name
            HelloResponse response = HelloResponse.builder()
                    .message("Hello, " + formattedName + "!")
                    .build();

            return ResponseEntity.ok(response);

        } catch (EmptyNameException e) {
            // Return 400 Bad Request with exact error format required
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid Input"));

        } catch (InvalidNameException e) {
            // Return 400 Bad Request with exact error format required
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid Input"));

        } catch (Exception e) {
            // Handle any unexpected exceptions with 500 Internal Server Error
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal Server Error"));
        }
    }
}
