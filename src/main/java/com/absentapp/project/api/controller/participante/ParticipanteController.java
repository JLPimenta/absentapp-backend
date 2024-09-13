package com.absentapp.project.api.controller.participante;

import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.api.config.exception.ResponseError;
import com.absentapp.project.domain.core.service.ICsvImporterService;
import com.absentapp.project.domain.entity.Participante;
import com.absentapp.project.domain.model.participante.IParticipanteMapper;
import com.absentapp.project.domain.model.participante.ParticipanteRequest;
import com.absentapp.project.domain.model.participante.ParticipanteResponse;
import com.absentapp.project.domain.model.participante.filter.ParticipanteRequestFilter;
import com.absentapp.project.domain.service.participante.IParticipanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import net.sf.jasperreports.engine.JRException;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/participante", produces = {"application/json"})
@Tag(name = "Participante", description = "Controle de criação, edição e remoção de Participantes na aplicação.")
@CrossOrigin
public class ParticipanteController {
    private final IParticipanteMapper mapper;
    private final IParticipanteService service;
    @Getter
    private final ICsvImporterService<Participante> uploader;

    protected ParticipanteController(final IParticipanteMapper mapper, final IParticipanteService service, ICsvImporterService<Participante> uploader) {
        this.mapper = mapper;
        this.service = service;
        this.uploader = uploader;
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    @Operation(description = "Cria um novo registro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Erro ao realizar criação do novo registro.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ResponseEntity<ParticipanteResponse> create(@RequestBody @Valid ParticipanteRequest participanteRequest, @RequestParam String salaId, UriComponentsBuilder uriBuilder) throws DomainException {
        Participante participante = getService().create(getMapper().fromRequest(participanteRequest), salaId);

        URI uri = uriBuilder.path("/sala/{id}").buildAndExpand(participante.getId()).toUri();

        return ResponseEntity.created(uri).body(getMapper().toResponse(participante));
    }

    @PostMapping(value = "/file/read", consumes = {"multipart/form-data"})
    @Operation(description = "Realiza a leitura de um documento CSV e retorna o conteúdo para o client. Colunas: nome participante | codigo de acesso")
    public Set<ParticipanteResponse> readParticipantes(@RequestPart("file") MultipartFile file) throws IOException {
        return getUploader().convertToModel(file).stream().map(mapper::toResponse).collect(Collectors.toSet());
    }

    @PostMapping(value = "/report")
    public ResponseEntity<byte[]> export(@RequestParam(name = "format") String format, @RequestParam(name = "id") String salaId) throws JRException, IOException {
        byte[] reportBytes = service.export(salaId, format);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "report." + format);

        return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    @Operation(description = "Atualiza um registro na base de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro criado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Registro não encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))
            })
    })
    public ParticipanteResponse update(@RequestBody @Valid ParticipanteRequest participante, @PathVariable(name = "id") String id) throws DomainException {
        return getMapper().toResponse(getService().update(getMapper().fromRequest(participante), id));
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    @Operation(description = "Deleta um registro da base de dados")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        getService().delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    @PageableAsQueryParam
    @Operation(description = "Consulta Participantes de acordo com os filtros.")
    public Page<ParticipanteResponse> filter(Pageable pageable, @RequestParam String salaId, ParticipanteRequestFilter requestFilter) {
        return getService().filter(requestFilter, salaId, pageable).map(mapper::toResponse);
    }

    protected IParticipanteMapper getMapper() {
        return this.mapper;
    }

    protected IParticipanteService getService() {
        return this.service;
    }
}