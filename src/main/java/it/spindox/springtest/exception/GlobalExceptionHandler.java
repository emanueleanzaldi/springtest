package it.spindox.springtest.exception;

import jakarta.persistence.EntityNotFoundException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getAllErrors().stream().map(error -> {
            Map<String, String> errorMap = new HashMap<>();
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put("fieldName", fieldName);
            errorMap.put("errorMessage", errorMessage);
            return errorMap;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<List<Map<String, String>>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getRootCause();
        List<Map<String, String>> errors = new ArrayList<>();

        if (rootCause instanceof PSQLException sqlEx) {
            String constraintName = Objects.requireNonNull(sqlEx.getServerErrorMessage()).getConstraint();
            // assert constraintName != null; // Non è necessario se usi Objects.requireNonNull sopra
            String fieldName = mapConstraintNameToFieldName(constraintName);
            String message = mapConstraintNameToMessage(constraintName);

            Map<String, String> error = new HashMap<>();
            error.put("fieldName", fieldName);
            error.put("errorMessage", message);
            errors.add(error);
        }

        if (errors.isEmpty()) {
            // Se non è stato possibile determinare il campo, aggiungi un errore generico
            Map<String, String> error = new HashMap<>();
            error.put("fieldName", "generic");
            error.put("errorMessage", "Errore di integrità dei dati.");
            errors.add(error);
        }

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT); // HttpStatus.CONFLICT = 409
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("errorMessage", ex.getMessage());
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }
    private String mapConstraintNameToFieldName(String constraintName) {
        return switch (constraintName) {
            case "student_model_email_key" -> "email";
            default -> "generic";
        };
    }

    private String mapConstraintNameToMessage(String constraintName) {
        return switch (constraintName) {
            case "student_model_email_key" ->
                    "L'email inserita è già presente nel sistema. Per favore, utilizza un'altra email.";
            default -> "Errore di integrità dei dati durante l'operazione. Per favore, controlla i dati inseriti.";
        };
    }
}
