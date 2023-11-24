package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.CadastrarPetAbrigoDto;
import br.com.alura.adopet.api.dto.pet.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public List<PetDto> listarPets() {
        return repository.findByAdotadoFalse()
                .stream()
                .map(PetDto::new)
                .toList();
    }

    public void cadastrarPet(Abrigo abrigo, CadastrarPetAbrigoDto dto) {
        Pet pet = new Pet(dto.tipo(),
                dto.nome(),
                dto.raca(),
                dto.idade(),
                dto.cor(),
                dto.peso(),
                abrigo);
        repository.save(pet);
    }
}
