package com.travelbuddy.service;

import java.util.List;

import com.travelbuddy.dto.UtenteCreateDTO;
import com.travelbuddy.dto.UtenteUpdateDTO;
import com.travelbuddy.entity.Utente;

public interface UtenteService {
	/*
	 * in un'API REST, l'ID rappresenta chi stai modificando, mentre l'oggetto Utente rappresenta 
	 * cosa vuoi cambiare 
	 * Esempio: Se vuoi cambiare solo il nickname, invierai una richiesta PATCH /utenti/5 con un 
	 * JSON che contiene solo il nuovo nickname. Il Service non ha bisogno dell'intero 
	 * oggetto Utente (che potrebbe essere incompleto), ma solo dell'ID 5 e del DTO (o oggetto) 
	 * con le modifiche. 
	 * Se il metodo disattiva(Long id) richiede solo l'ID, non costringi chi chiama 
	 * il metodo (il Controller) a doversi preoccupare di istanziare un oggetto Utente completo. 
	 * Il Service si occupa di recuperare l'entità dal database tramite il Repository, 
	 * modificarne lo stato e salvarla.
	 */
	
	List<Utente> findAllUtenti();
	Utente findUtenteById(Long id); 
	Utente findUtenteByNickname(String nickname); //nickname è unique
	Utente findUtenteByEmail(String email); //email è unique
	Utente replaceUtenteById(UtenteUpdateDTO utenteDTO, Long id);
	Utente updateUtenteById(UtenteUpdateDTO utenteDTO, Long id); 
	Utente RegistrazioneNuovoUtente(UtenteCreateDTO utenteDTO); //l'utente dev'essere un guest
	String loginUtente(Utente utente); //il guest non può loggarsi 
	//disattivazione account 
	void disattivazioneAccountUtente(Long id); //lo status passa da attivo a disattivo
	//riattivazione account 
	void riattivazioneAccountUtente(Long id); //lo status passa da disattivo a attivo
	//ban dell'account 
	void banAccount(Long id); //solo l'admin --> status diventa bannato
	//FACOLTATIVO: segnalazione utente da parte di moderator 
	//eliminazione account 
	void deleteUtenteById(Long id); 
	
	/*
	 * Ho rimosso l'oggetto Utente dai metodi disattiva, riattiva e ban.
	 * Se il Service è ben progettato, all'interno del metodo caricherai l'utente dal database 
	 * tramite l'ID, verificherai lo stato e applicherai la modifica. Passare l'intero oggetto 
	 * è inutile e potenzialmente pericoloso (potresti passare un oggetto Utente con dati 
	 * obsoleti o incoerenti).
	 */
	
}
