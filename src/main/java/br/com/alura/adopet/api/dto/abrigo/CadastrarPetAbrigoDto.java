package br.com.alura.adopet.api.dto.abrigo;

import br.com.alura.adopet.api.model.TipoPet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarPetAbrigoDto(@NotNull TipoPet tipo,
                                    @NotBlank String nome,
                                    @NotBlank String raca,
                                    @NotNull Integer idade,
                                    @NotBlank String cor,
                                    @NotNull Float peso) {
}
