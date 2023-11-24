package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.CadastrarAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private PetRepository petRepository;

    public void cadastrar(CadastrarAbrigoDto dto) {
        boolean abrigoJaCadastrado = abrigoRepository
                .existsByEmailOrNomeOrTelefone(dto.email(), dto.nome(), dto.telefone());

        if (abrigoJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }

        Abrigo abrigo = new Abrigo(dto.nome(), dto.telefone(), dto.email());

        abrigoRepository.save(abrigo);
    }

    public List<Abrigo> listar() {
        return abrigoRepository.findAll();
    }

    public List<Pet> listarPets(String idOuNome) {
        Abrigo abrigo = carregarAbrigo(idOuNome);

        return petRepository.findByAbrigo(abrigo);
    }

    public Abrigo carregarAbrigo(String idOuNome) {
        Optional<Abrigo> optional;
        try {
            Long id = Long.parseLong(idOuNome);
            optional = abrigoRepository.findById(id);
        } catch (NumberFormatException e) {
            optional = abrigoRepository.findByNome(idOuNome);
        }
        return optional.orElseThrow(() -> new ValidacaoException("Abrigo não encontrado"));
    }

}
