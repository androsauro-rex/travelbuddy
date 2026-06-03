package com.travelbuddy.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/*
 * Questa classe - ApiErrorDto - è un DTO usato per la gestione centralizzata delle eccezioni. 
 * Immagina che il tuo server incontri un errore (ad esempio, un utente prova a registrarsi con 
 * un nickname già esistente). Se non gestissi l'errore, Spring Boot restituirebbe una pagina HTML 
 * di errore brutta o uno stack trace tecnico che non dice nulla all'utente.
Con ApiErrorDto, tu costruisci un messaggio di errore "pulito" e standardizzato in formato JSON 
che il frontend (React, Angular, o un'app mobile) può leggere e mostrare facilmente all'utente.

Analisi dei campi:
timestamp: Dice quando è avvenuto l'errore (fondamentale per il log).
status: Il codice HTTP (es. 400 per Bad Request, 404 per Not Found).
error: Una breve descrizione del tipo di errore (es. "Bad Request").
message: Un messaggio leggibile dall'umano (es. "Il nickname scelto è già in uso").

errors (il Map): Questo è il campo più interessante. Serve per gli errori di validazione. 
Se un utente invia un form con 3 campi sbagliati (es. email vuota, nickname troppo corto, 
età negativa), puoi inserire in questa mappa:
Chiave: "email" -> Valore: "L'email è obbligatoria"
Chiave: "nickname" -> Valore: "Il nickname deve avere almeno 3 caratteri"
 * 
 */

public class ApiErrorDto {
	
	private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> errors = new HashMap<>();

    public ApiErrorDto() {
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

	@Override
	public String toString() {
		return "ApiErrorDto [timestamp=" + timestamp + ", status=" + status + ", error=" + error + ", message="
				+ message + ", errors=" + errors + "]";
	}
	
	
}
