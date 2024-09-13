package com.absentapp.project.api.controller.presenca;

import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.domain.entity.Presenca;
import com.absentapp.project.domain.model.presenca.PresencaRegisterResponse;
import com.absentapp.project.domain.service.presenca.IPresencaService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequestMapping(value = "/presenca", produces = {"application/json"})
@Tag(name = "Presenca", description = "Controle dos registros de presença.")
public class PresencaController {

    private final IPresencaService service;

    public PresencaController(final IPresencaService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    @Transactional(rollbackFor = Exception.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Presença registrada.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PresencaRegisterResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Registro de presença duplicado.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DataIntegrityViolationException.class))
            })
    })
    public ResponseEntity<?> registrarPresenca(@RequestParam String codigoAcesso, @RequestParam String salaId) throws DomainException, DataIntegrityViolationException {
        Presenca presenca = getService().registrarPresenca(codigoAcesso, salaId);

        String nomeParticipante = presenca.getParticipante().getNome();
        String nomeSala = presenca.getSala().getNome();

        return ResponseEntity.ok(new PresencaRegisterResponse("Presença confirmada com sucesso!", presenca.getDataRegistro(), nomeParticipante, nomeSala));
    }
}
