package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.tutor.CadastrarTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    public void atualizar(String nomeTutor, AtualizarTutorDto dto) {
        Tutor tutor = repository.findByNome(nomeTutor);
        tutor.setTelefone(dto.telefone());
        tutor.setEmail(dto.email());
        repository.save(tutor);
    }

    public void cadastrar(CadastrarTutorDto dto) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(dto.telefone());
        boolean emailJaCadastrado = repository.existsByEmail(dto.email());

        Tutor tutor = new Tutor();
        tutor.setNome(dto.nome());
        tutor.setTelefone(dto.telefone());
        tutor.setEmail(dto.email());

        if (telefoneJaCadastrado || emailJaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");
        } else {
            repository.save(tutor);
        }
    }
}
