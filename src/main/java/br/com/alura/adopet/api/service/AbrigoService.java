package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.CadastrarAbrigoDto;
import br.com.alura.adopet.api.dto.abrigo.CadastrarPetAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    public void cadastrar(CadastrarAbrigoDto dto) {
        boolean abrigoJaCadastrado = abrigoRepository
                .existsByEmailAndNomeAndTelefone(dto.email(), dto.nome(), dto.telefone());

        Abrigo abrigo = new Abrigo();
        abrigo.setNome(dto.nome());
        abrigo.setTelefone(dto.telefone());
        abrigo.setEmail(dto.email());

        if (abrigoJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        } else {
            abrigoRepository.save(abrigo);
        }
    }

    public List<Abrigo> listar() {
        return abrigoRepository.findAll();
    }

    public List<Pet> listarPets(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            List<Pet> pets = abrigoRepository.getReferenceById(id).getPets();
            return pets;
        } catch (EntityNotFoundException enfe) {
            throw new EntityNotFoundException("Não encontrado abrigo com esse id");
        } catch (NumberFormatException e) {
            try {
                List<Pet> pets = abrigoRepository.findByNome(idOuNome).getPets();
                return pets;
            } catch (NullPointerException enfe) {
                throw new EntityNotFoundException("Não encontrado abrigo com esse nome");
            }
        }
    }

    public void cadastrarPet(String idOuNome, CadastrarPetAbrigoDto dto) {
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = abrigoRepository.findById(id).get();
            Pet pet = new Pet();
            pet.setTipo(Objects.equals(TipoPet.CACHORRO.name(), dto.tipo().toUpperCase()) ? TipoPet.CACHORRO : TipoPet.GATO);
            pet.setNome(dto.nome());
            pet.setRaca(dto.raca());
            pet.setIdade(dto.idade());
            pet.setCor(dto.cor());
            pet.setPeso(dto.peso());
            pet.setAdotado(false);
            abrigo.getPets().add(pet);
            abrigoRepository.save(abrigo);
        } catch (EntityNotFoundException enfe) {
            throw new EntityNotFoundException("Não encontrado abrigo com esse id");
        } catch (NumberFormatException nfe) {
            try {
                Abrigo abrigo = abrigoRepository.findByNome(idOuNome);
                Pet pet = new Pet();
                pet.setTipo(Objects.equals(TipoPet.CACHORRO.name(), dto.tipo().toUpperCase()) ? TipoPet.CACHORRO : TipoPet.GATO);
                pet.setNome(dto.nome());
                pet.setRaca(dto.raca());
                pet.setIdade(dto.idade());
                pet.setCor(dto.cor());
                pet.setPeso(dto.peso());
                pet.setAbrigo(abrigo);
                pet.setAdotado(false);
                abrigo.getPets().add(pet);
                abrigoRepository.save(abrigo);
            } catch (NullPointerException enfe) {
                throw new EntityNotFoundException("Não encontrado abrigo com esse nome");
            }
        }
    }
}
