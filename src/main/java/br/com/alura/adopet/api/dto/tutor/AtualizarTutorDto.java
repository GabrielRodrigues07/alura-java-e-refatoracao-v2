package br.com.alura.adopet.api.dto.tutor;

import jakarta.validation.constraints.NotBlank;

public record AtualizarTutorDto(@NotBlank String telefone, @NotBlank String email) {
}
