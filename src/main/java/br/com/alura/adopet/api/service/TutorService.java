package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.tutor.CadastrarTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    public void atualizar(String nomeTutor, AtualizarTutorDto dto) {
        Tutor tutor = repository.findByNome(nomeTutor);
        if (Objects.nonNull(tutor)) {
            tutor.setTelefone(dto.telefone());
            tutor.setEmail(dto.email());
            repository.save(tutor);
        }else {
            throw new ValidacaoException("Tutor não encontrado");
        }
    }

    public void cadastrar(CadastrarTutorDto dto) {
        boolean tutorCadastrado = repository.existsByEmailOrTelefone(dto.email(), dto.telefone());

        Tutor tutor = new Tutor(dto.nome(), dto.telefone(), dto.email());

        if (tutorCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        } else {
            repository.save(tutor);
        }
    }
}
