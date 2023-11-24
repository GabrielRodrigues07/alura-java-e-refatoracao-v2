package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.tutor.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.tutor.CadastrarTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastrarTutorDto dto) {
        try {
            tutorService.cadastrar(dto);
            return ResponseEntity.ok().build();
        }catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(("/{nomeTutor}"))
    @Transactional
    public ResponseEntity<String> atualizar(@PathVariable String nomeTutor, @RequestBody @Valid AtualizarTutorDto dto) {
        try {
            tutorService.atualizar(nomeTutor, dto);
            return ResponseEntity.ok().build();
        }catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
