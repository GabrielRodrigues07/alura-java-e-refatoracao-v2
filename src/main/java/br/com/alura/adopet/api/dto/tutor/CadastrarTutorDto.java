package br.com.alura.adopet.api.dto.tutor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CadastrarTutorDto(@NotBlank String nome,
                                @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}") @NotBlank String telefone,
                                @Email @NotBlank String email) {
}
