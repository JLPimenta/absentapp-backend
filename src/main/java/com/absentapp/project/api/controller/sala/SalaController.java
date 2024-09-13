package com.absentapp.project.api.controller.sala;

import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.api.config.exception.ResponseError;
import com.absentapp.project.domain.entity.Sala;
import com.absentapp.project.domain.model.sala.*;
import com.absentapp.project.domain.service.sala.ISalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Getter
@RestController
@RequestMapping(value = "/sala", produces = {"application/json"})
@Tag(name = "Sala", description = "Controle de criação, edição e remoção de Salas na aplicação.")
@CrossOrigin
public class SalaController {
    private final ISalaService service;
    private final ISalaMapper mapper;

    protected SalaController(ISalaMapper mapper, ISalaService service) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(description = "Busca todas as salas, sem os participantes", operationId = "id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com paginação retornada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de passagem do parâmetro de ordenação.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })})
    public List<SalaBasicResponse> findAll(@RequestParam Boolean situacao) throws DomainException {
        return mapper.toListBasicResponse(getService().findAll(situacao));
    }

    @GetMapping("/all")
    @Operation(description = "Busca todas as salas, sem os participantes e com paginação", operationId = "id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com paginação retornada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de passagem do parâmetro de ordenação.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })})
    public Page<SalaBasicResponse> findAll(@RequestParam Boolean situacao, Pageable pageable) throws DomainException {
        return getService().findAll(situacao, pageable).map(getMapper()::toBasicReponse);
    }

    @GetMapping("/{id}")
    @Operation(description = "Busca registro específico por id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Item não encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public SalaResponse findById(@PathVariable String id) throws DomainException {
        return getMapper().toResponse(getService().findById(id));
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    @Operation(description = "Cria um novo registro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Erro ao realizar criação do novo registro.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<SalaResponse> create(@RequestBody @Valid SalaRequest salaRequest, UriComponentsBuilder uriBuilder) throws DomainException {
        Sala sala = getService().create(getMapper().fromRequest(salaRequest));

        URI uri = uriBuilder.path("/sala/{id}").buildAndExpand(sala.getId()).toUri();

        return ResponseEntity.created(uri).body(getMapper().toResponse(sala));
    }

    @PutMapping("/{id}")
    @Transactional(rollbackFor = DomainException.class)
    @Operation(description = "Atualiza um registro na base de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro criado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Registro não encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public SalaBasicResponse update(@RequestBody @Valid SalaBasicRequest basicRequest, @PathVariable String id) throws DomainException {
        return getMapper().toBasicReponse(getService().update(getMapper().fromBasicRequest(basicRequest), id));
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = DomainException.class)
    @Operation(description = "Deleta um registro da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro deleta com sucesso."),
            @ApiResponse(responseCode = "409", description = "Registro não encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        getService().delete(id);

        return ResponseEntity.noContent().build();
    }

}
