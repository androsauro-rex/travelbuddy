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

//quando si creerà l'itinerario, bisognerà verificare che la data di inizio viaggio 
//sia antecedente la data di fine viaggio 
@Service
public class ItinerarioServiceImpl implements ItinerarioService{
	
	//Dependency Injection 
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
		if(id == null) {
			throw new IllegalArgumentException("id passato nullo");
		}
		Optional<Itinerario> itinerarioOpt = itinerarioRepository.findById(id); 
		
		return itinerarioOpt.orElseThrow(() -> new NotFoundException("Itinerario con id " + id + " non "
				+ "trovato"));
	}

	@Override
	@Transactional
	public Itinerario creaItinerario(ItinerarioCreateDTO itinerarioDTO, 
			DestinazioneDTO destinazioneDTO) {
		if(itinerarioDTO == null) {
			throw new IllegalArgumentException("Itinerario nullo"); 
		}
		if(destinazioneDTO == null) {
			throw new IllegalArgumentException("destinazione nullo"); 
		}
		
		Itinerario nuovoItinerario = new Itinerario(); 
		
		nuovoItinerario.setTitoloViaggio(itinerarioDTO.getTitoloViaggio());
		nuovoItinerario.setVisibilita(itinerarioDTO.getVisibilita());
		if(itinerarioDTO.getDataInizioViaggio().isAfter(itinerarioDTO.getDataFineViaggio())) {
			throw new BadRequestException("La data di fine viaggio non può essere antecedente "
					+ "la data di fine viaggio"); 
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
	
	@Override
	public Itinerario creaItinerarioConGiorni(ItinerarioCreateDTO itinerarioDTO, 
			DestinazioneDTO destinazioneDTO,
			List<GiornoDTO> listaGiorni) {
		if(itinerarioDTO == null) {
			throw new IllegalArgumentException("Itinerario nullo"); 
		}
		if(destinazioneDTO == null) {
			throw new IllegalArgumentException("destinazione nullo"); 
		}
		if(listaGiorni == null) {
			throw new IllegalArgumentException("giorno nullo"); 
		}
		
		Itinerario nuovoItinerario = new Itinerario(); 
		
		nuovoItinerario.setTitoloViaggio(itinerarioDTO.getTitoloViaggio());
		nuovoItinerario.setVisibilita(itinerarioDTO.getVisibilita());
		if(itinerarioDTO.getDataInizioViaggio().isAfter(itinerarioDTO.getDataFineViaggio())) {
			throw new BadRequestException("La data di fine viaggio non può essere antecedente "
					+ "la data di fine viaggio"); 
		} 
		nuovoItinerario.setDataInizioViaggio(itinerarioDTO.getDataInizioViaggio());
		nuovoItinerario.setDataFineViaggio(itinerarioDTO.getDataFineViaggio());
		nuovoItinerario.setBudgetPianificato(itinerarioDTO.getBudgetPianificato());
		
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
		}
		
		return nuovoItinerario;
	}

	@Override
	@Transactional
	public Itinerario creaItinerarioConGiorniTappe(ItinerarioCreateDTO itinerarioDTO, 
			DestinazioneDTO destinazioneDTO,
			List<GiornoDTO> listaGiorni, 
			List<TappaDTO> listaTappe) {
		
		if(itinerarioDTO == null) {
			throw new IllegalArgumentException("Itinerario nullo"); 
		}
		if(destinazioneDTO == null) {
			throw new IllegalArgumentException("destinazione nullo"); 
		}
		if(listaGiorni == null) {
			throw new IllegalArgumentException("lista dei giorni nulla"); 
		}
		if(listaTappe == null) {
			throw new IllegalArgumentException("lista delle tappe nulla"); 
		}
		
		Itinerario nuovoItinerario = new Itinerario(); 
		
		nuovoItinerario.setTitoloViaggio(itinerarioDTO.getTitoloViaggio());
		nuovoItinerario.setVisibilita(itinerarioDTO.getVisibilita());
		if(itinerarioDTO.getDataInizioViaggio().isAfter(itinerarioDTO.getDataFineViaggio())) {
			throw new BadRequestException("La data di fine viaggio non può essere antecedente "
					+ "la data di fine viaggio"); 
		} 
		nuovoItinerario.setDataInizioViaggio(itinerarioDTO.getDataInizioViaggio());
		nuovoItinerario.setDataFineViaggio(itinerarioDTO.getDataFineViaggio());
		nuovoItinerario.setBudgetPianificato(itinerarioDTO.getBudgetPianificato());
		
		itinerarioRepository.save(nuovoItinerario); 
		
		Destinazione nuovaDestinazione = new Destinazione(); 
		nuovaDestinazione.setNomeDestinazione(destinazioneDTO.getNomeDestinazione());
		
		nuovaDestinazione.setItinerario(nuovoItinerario);
		destinazioneRepository.save(nuovaDestinazione);
		
		
		// Gestione Giorni (se la lista non è nulla)
	        for (GiornoDTO gDto : listaGiorni) {
	            Giorno giorno = new Giorno();
	            giorno.setDescrizioneAttivitaGiorno(gDto.getDescrizioneAttivitaGiorno());
	            giorno.setData(gDto.getData());
	            giorno.setItinerario(nuovoItinerario);
	            giornoRepository.save(giorno);
	            
	            // Gestione Tappe (se appartengono a questo specifico giorno)
	            for (TappaDTO tDto : gDto.getTappe()) {
	                  Tappa tappa = new Tappa();
	                  tappa.setNomeTappa(tDto.getNomeTappa());
	                  tappa.setGiorno(giorno); // Colleghi la tappa al giorno appena salvato
	                  tappaRepository.save(tappa);
	               }
	           }
	    return nuovoItinerario;
	}

	@Override
	public Itinerario modificaItinerario(ItinerarioUpdateDTO itinerarioDTO, Long id) {
		if(itinerarioDTO == null) {
			throw new IllegalArgumentException("Itinerario passato nullo"); 
		}
		Optional<Itinerario> itinerarioOpt = itinerarioRepository.findById(id); 
		Itinerario itinerario = itinerarioOpt.orElseThrow(()-> new NotFoundException("Itinerario "
				+ " con id " + id + " non trovato" ));
		
		itinerario.setTitoloViaggio(itinerarioDTO.getTitoloViaggio());
		itinerario.setVisibilita(itinerarioDTO.getVisibilita());
		if(itinerarioDTO.getDataInizioViaggio().isAfter(itinerarioDTO.getDataFineViaggio())) {
			throw new BadRequestException("La data di fine viaggio non può essere antecedente "
					+ "la data di fine viaggio"); 
		} 
		itinerario.setDataInizioViaggio(itinerarioDTO.getDataInizioViaggio());
		itinerario.setDataFineViaggio(itinerarioDTO.getDataFineViaggio());
		itinerario.setBudgetPianificato(itinerarioDTO.getBudgetPianificato());
		
		itinerarioRepository.save(itinerario); 
		//DA FINIRE: GIORNO E TAPPE NON LE MODIFICHI UNA VOLTA CHE CAMBI DATA INIZIO E DATA FINE VIAGGIO?
		//MA CERTO CHE SI' !!
		
		return itinerario;
	}

	@Override
	public void aggiuntaLike(Itinerario itinerario) {
		if(itinerario == null) {
			throw new IllegalArgumentException("Itinerario nullo"); 
		}
		int likes = itinerario.getLikes(); 
		itinerario.setLikes(likes + 1);
		itinerarioRepository.save(itinerario); 
	}

	@Override
	public void rimuoviLike(Itinerario itinerario) {
		if(itinerario == null) {
			throw new IllegalArgumentException("Itinerario nullo"); 
		}
		int likes = itinerario.getLikes(); 
		itinerario.setLikes(likes - 1);
		itinerarioRepository.save(itinerario); 
	}

	@Override
	public void deleteItinerarioById(Long id) {
		if(id == null) {
			throw new IllegalArgumentException("id nullo"); 
		}
		Optional<Itinerario> itinerarioOpt = itinerarioRepository.findById(id); 
		Itinerario itinerario = itinerarioOpt.orElseThrow(()-> new NotFoundException("Itinerario "
				+ " con id " + id + " non trovato" ));
		itinerarioRepository.delete(itinerario);
	}

	

	

}
