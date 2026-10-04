package es.iesquevedo.domain.services;

import es.iesquevedo.dao.repositories.MedicoRepository;
import es.iesquevedo.domain.dto.MedicoDTO;
import es.iesquevedo.domain.mappers.MedicoDTOMapper;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final MedicoDTOMapper mapper = new MedicoDTOMapper();

    @Inject
    public MedicoService(MedicoRepository medicoRepository){
        this.medicoRepository = medicoRepository;
    }

    public List<MedicoDTO>getAllMedicos(){
        return medicoRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    };

    public Optional<MedicoDTO> getMedicoById(Long id) {
        return medicoRepository.findById(id)
                .map(mapper::toDTO);
    }
}
