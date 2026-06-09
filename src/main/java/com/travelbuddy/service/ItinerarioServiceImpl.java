package com.travelbuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelbuddy.dto.DestinazioneDTO;
import com.travelbuddy.dto.GiornoDTO;
import com.travelbuddy.dto.ItinerarioCreateDTO;
import com.travelbuddy.dto.ItinerarioUpdateDTO;
import com.travelbuddy.dto.TappaDTO;
import com.travelbuddy.entity.Destinazione;
import com.travelbuddy.entity.Giorno;
import com.travelbuddy.entity.Itinerario;
import com.travelbuddy.entity.Tappa;
import com.travelbuddy.exception.BadRequestException;
import com.travelbuddy.exception.NotFoundException;
import com.travelbuddy.repository.DestinazioneRepository;
import com.travelbuddy.repository.GiornoRepository;
import com.travelbuddy.repository.ItinerarioRepository;
import com.travelbuddy.repository.TappaRepository;

@Service
public class ItinerarioServiceImpl implements ItinerarioService {
	
	
	private final ItinerarioRepository itinerarioRepository;
	private final DestinazioneRepository destinazioneRepository;
	private final GiornoRepository giornoRepository;
	private final TappaRepository tappaRepository;
	

	public ItinerarioServiceImpl(ItinerarioRepository itinerarioRepository,
			DestinazioneRepository destinazioneRepository,
			GiornoRepository giornoRepository,
			TappaRepository tappaRepository) {
		this.itinerarioRepository = itinerarioRepository;
		this.destinazioneRepository = destinazioneRepository;
		this.giornoRepository = giornoRepository;
		this.tappaRepository = tappaRepository;
		
	}

	@Override
	public List<Itinerario> findAllItinerari() {
		return itinerarioRepository.findAll();
	}

	@Override
	public Itinerario findItinerarioById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("id passato nullo");
		}
		Optional<Itinerario> itinerarioOpt = itinerarioRepository.findById(id);
		return itinerarioOpt.orElseThrow(() -> new NotFoundException("Itinerario con id " + id + " non trovato"));
	}

	@Override
	@Transactional
	public Itinerario creaItinerario(ItinerarioCreateDTO itinerarioDTO,
			DestinazioneDTO destinazioneDTO) {
		if (itinerarioDTO == null) {
			throw new IllegalArgumentException("Itinerario nullo");
		}
		if (destinazioneDTO == null) {
			throw new IllegalArgumentException("destinazione nullo");
		}

		Itinerario nuovoItinerario = new Itinerario();
		nuovoItinerario.setTitoloViaggio(itinerarioDTO.getTitoloViaggio());
		nuovoItinerario.setVisibilita(itinerarioDTO.getVisibilita());
		if (itinerarioDTO.getDataInizioViaggio().isAfter(itinerarioDTO.getDataFineViaggio())) {
			throw new BadRequestException("La data di fine viaggio non può essere antecedente la data di inizio viaggio");
		}
		nuovoItinerario.setDataInizioViaggio(itinerarioDTO.getDataInizioViaggio());
		nuovoItinerario.setDataFineViaggio(itinerarioDTO.getDataFineViaggio());
		nuovoItinerario.setBudgetPianificato(itinerarioDTO.getBudgetPianificato());

		itinerarioRepository.save(nuovoItinerario);

		Destinazione nuovaDestinazione = new Destinazione();
		nuovaDestinazione.setNomeDestinazione(destinazioneDTO.getNomeDestinazione());
		nuovaDestinazione.setItinerario(nuovoItinerario);
		destinazioneRepository.save(nuovaDestinazione);

		return nuovoItinerario;
	}

	// ============================================================
	//  QUESTO È IL METODO CHE USI DAL FRONTEND.
	//  Ora salva: itinerario + destinazione + GIORNI + TAPPE.
	//  (prima ignorava le tappe)
	// ============================================================
	@Override
	@Transactional   // <-- AGGIUNTO: o tutto si salva, o niente (no dati a metà)
	public Itinerario creaItinerarioConGiorni(ItinerarioCreateDTO itinerarioDTO,
			DestinazioneDTO destinazioneDTO,
			List<GiornoDTO> listaGiorni) {
		if (itinerarioDTO == null) {
			throw new IllegalArgumentException("Itinerario nullo");
		}
		if (destinazioneDTO == null) {
			throw new IllegalArgumentException("destinazione nullo");
		}
		if (listaGiorni == null) {
			throw new IllegalArgumentException("giorno nullo");
		}

		Itinerario nuovoItinerario = new Itinerario();
		nuovoItinerario.setTitoloViaggio(itinerarioDTO.getTitoloViaggio());
		nuovoItinerario.setVisibilita(itinerarioDTO.getVisibilita());
		if (itinerarioDTO.getDataInizioViaggio().isAfter(itinerarioDTO.getDataFineViaggio())) {
			throw new BadRequestException("La data di fine viaggio non può essere antecedente la data di inizio viaggio");
		}
		nuovoItinerario.setDataInizioViaggio(itinerarioDTO.getDataInizioViaggio());
		nuovoItinerario.setDataFineViaggio(itinerarioDTO.getDataFineViaggio());
		nuovoItinerario.setBudgetPianificato(itinerarioDTO.getBudgetPianificato());

		// like a 0 di partenza (evita problemi se la colonna è not null)
		nuovoItinerario.setLikes(0);
		
//		Optional<Utente> utente = utenteRepository.findById();
//		nuovoItinerario.setUtente();

		itinerarioRepository.save(nuovoItinerario);

		Destinazione nuovaDestinazione = new Destinazione();
		nuovaDestinazione.setNomeDestinazione(destinazioneDTO.getNomeDestinazione());
		nuovaDestinazione.setItinerario(nuovoItinerario);
		destinazioneRepository.save(nuovaDestinazione);

		for (GiornoDTO gDto : listaGiorni) {
			Giorno giorno = new Giorno();
			giorno.setDescrizioneAttivitaGiorno(gDto.getDescrizioneAttivitaGiorno());
			giorno.setData(gDto.getData());
			giorno.setItinerario(nuovoItinerario);
			giornoRepository.save(giorno);

			// ---- AGGIUNTA: salvo le TAPPE di QUESTO giorno ----
			if (gDto.getTappe() != null) {
				for (TappaDTO tDto : gDto.getTappe()) {
					Tappa tappa = new Tappa();
					tappa.setNomeTappa(tDto.getNomeTappa());
					// salvo la DESCRIZIONE (non il nome): qui c'era il bug classico
					tappa.setDescrizioneTappa(tDto.getDescrizioneTappa());
					tappa.setGiorno(giorno);
					tappaRepository.save(tappa);
				}
			}
		}

		return nuovoItinerario;
	}

	@Override
	@Transactional
	public Itinerario creaItinerarioConGiorniTappe(ItinerarioCreateDTO itinerarioDTO,
			DestinazioneDTO destinazioneDTO,
			List<GiornoDTO> listaGiorni,
			List<TappaDTO> listaTappe) {

		if (itinerarioDTO == null) {
			throw new IllegalArgumentException("Itinerario nullo");
		}
		if (destinazioneDTO == null) {
			throw new IllegalArgumentException("destinazione nullo");
		}
		if (listaGiorni == null) {
			throw new IllegalArgumentException("lista dei giorni nulla");
		}

		Itinerario nuovoItinerario = new Itinerario();
		nuovoItinerario.setTitoloViaggio(itinerarioDTO.getTitoloViaggio());
		nuovoItinerario.setVisibilita(itinerarioDTO.getVisibilita());
		if (itinerarioDTO.getDataInizioViaggio().isAfter(itinerarioDTO.getDataFineViaggio())) {
			throw new BadRequestException("La data di fine viaggio non può essere antecedente la data di inizio viaggio");
		}
		nuovoItinerario.setDataInizioViaggio(itinerarioDTO.getDataInizioViaggio());
		nuovoItinerario.setDataFineViaggio(itinerarioDTO.getDataFineViaggio());
		nuovoItinerario.setBudgetPianificato(itinerarioDTO.getBudgetPianificato());
		nuovoItinerario.setLikes(0);

		itinerarioRepository.save(nuovoItinerario);

		Destinazione nuovaDestinazione = new Destinazione();
		nuovaDestinazione.setNomeDestinazione(destinazioneDTO.getNomeDestinazione());
		nuovaDestinazione.setItinerario(nuovoItinerario);
		destinazioneRepository.save(nuovaDestinazione);

		for (GiornoDTO gDto : listaGiorni) {
			Giorno giorno = new Giorno();
			giorno.setDescrizioneAttivitaGiorno(gDto.getDescrizioneAttivitaGiorno());
			giorno.setData(gDto.getData());
			giorno.setItinerario(nuovoItinerario);
			giornoRepository.save(giorno);

			if (gDto.getTappe() != null) {
				for (TappaDTO tDto : gDto.getTappe()) {
					Tappa tappa = new Tappa();
					tappa.setNomeTappa(tDto.getNomeTappa());
					tappa.setDescrizioneTappa(tDto.getDescrizioneTappa());
					tappa.setGiorno(giorno);
					tappaRepository.save(tappa);
				}
			}
		}
		return nuovoItinerario;
	}

	// ============================================================
	//  MODIFICA con strategia "fotografia": cancella i vecchi
	//  giorni (e tappe) e riscrive quelli nuovi.
	//  RICEVE GiornoDTO (non entity Giorno).
	// ============================================================
	@Override
	@Transactional
	public Itinerario modificaItinerario(ItinerarioUpdateDTO itinerarioDTO, Long id,
			List<GiornoDTO> listaGiorni) {
		if (itinerarioDTO == null) {
			throw new IllegalArgumentException("Itinerario passato nullo");
		}
		if (listaGiorni == null) {
			throw new IllegalArgumentException("Lista giorni nulla");
		}

		Itinerario itinerario = itinerarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Itinerario con id " + id + " non trovato"));

		// controllo date
		if (itinerarioDTO.getDataInizioViaggio().isAfter(itinerarioDTO.getDataFineViaggio())) {
			throw new BadRequestException("La data di fine viaggio non può essere antecedente la data di inizio viaggio");
		}

		// aggiorno i campi dell'itinerario
		itinerario.setTitoloViaggio(itinerarioDTO.getTitoloViaggio());
		itinerario.setVisibilita(itinerarioDTO.getVisibilita());
		itinerario.setDataInizioViaggio(itinerarioDTO.getDataInizioViaggio());
		itinerario.setDataFineViaggio(itinerarioDTO.getDataFineViaggio());
		itinerario.setBudgetPianificato(itinerarioDTO.getBudgetPianificato());
		if (itinerarioDTO.getRecensioneItinerario() != null) {
			itinerario.setRecensioneItinerario(itinerarioDTO.getRecensioneItinerario());
		}
		itinerarioRepository.save(itinerario);

		// ===== CANCELLA E RISCRIVI =====
		// 1) cancello le tappe di ogni vecchio giorno, poi i giorni
		List<Giorno> giorniVecchi = giornoRepository.findGiornoByItinerarioId(id);
		for (Giorno g : giorniVecchi) {
			tappaRepository.deleteAll(tappaRepository.findByGiornoId(g.getId()));
		}
		giornoRepository.deleteAll(giorniVecchi);

		// 2) riscrivo i giorni nuovi con le loro tappe
		for (GiornoDTO gDto : listaGiorni) {
			Giorno giorno = new Giorno();
			giorno.setDescrizioneAttivitaGiorno(gDto.getDescrizioneAttivitaGiorno());
			giorno.setData(gDto.getData());
			giorno.setItinerario(itinerario);
			giornoRepository.save(giorno);

			if (gDto.getTappe() != null) {
				for (TappaDTO tDto : gDto.getTappe()) {
					Tappa tappa = new Tappa();
					tappa.setNomeTappa(tDto.getNomeTappa());
					tappa.setDescrizioneTappa(tDto.getDescrizioneTappa());
					tappa.setGiorno(giorno);
					tappaRepository.save(tappa);
				}
			}
		}

		return itinerario;
	}

	@Override
	public void aggiuntaLike(Itinerario itinerario) {
		if (itinerario == null) {
			throw new IllegalArgumentException("Itinerario nullo");
		}
		itinerario.setLikes(itinerario.getLikes() + 1);
		itinerarioRepository.save(itinerario);
	}

	@Override
	public void rimuoviLike(Itinerario itinerario) {
		if (itinerario == null) {
			throw new IllegalArgumentException("Itinerario nullo");
		}
		itinerario.setLikes(itinerario.getLikes() - 1);
		itinerarioRepository.save(itinerario);
	}

	@Override
	@Transactional
	public void deleteItinerarioById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("id nullo");
		}
		Itinerario itinerario = itinerarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Itinerario con id " + id + " non trovato"));

		// cancello prima i figli per non violare i vincoli del DB
		List<Giorno> giorni = giornoRepository.findGiornoByItinerarioId(id);
		for (Giorno g : giorni) {
			tappaRepository.deleteAll(tappaRepository.findByGiornoId(g.getId()));
		}
		giornoRepository.deleteAll(giorni);
		destinazioneRepository.deleteAll(destinazioneRepository.findByItinerarioId(id));

		itinerarioRepository.delete(itinerario);
	}

}