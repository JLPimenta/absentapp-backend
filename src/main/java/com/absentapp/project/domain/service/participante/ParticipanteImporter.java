package com.absentapp.project.domain.service.participante;

import com.absentapp.project.domain.core.service.ICsvImporterService;
import com.absentapp.project.domain.entity.Participante;
import com.absentapp.project.domain.model.participante.csv.ParticipanteCsvRep;
import com.absentapp.project.domain.repository.participante.ParticipanteRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class ParticipanteImporter implements ICsvImporterService<Participante> {

    private final ParticipanteRepository repository;

    /**
     * @param file
     * @return Quantidade de itens na listagem
     */
    @Override
    public Integer upload(MultipartFile file) throws IOException {
        Set<Participante> participantes = convertToModel(file);

        getRepository().saveAll(participantes);
        return participantes.size();
    }

    /**
     * @param file
     * @return Lista de Participantes
     * @throws IOException
     */
    @Override
    public Set<Participante> convertToModel(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<ParticipanteCsvRep> csvToBean = new CsvToBeanBuilder<ParticipanteCsvRep>(reader)
                    .withSeparator(';')
                    .withType(ParticipanteCsvRep.class)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse().stream()
                    .map(line -> Participante.builder()
                            .nome(line.getNome())
                            .codigoAcesso(line.getCodigoAcesso())
                            .sala(null)
                            .build())
                    .collect(Collectors.toSet());

        } catch (Exception ex) {
            log.error("Erro ao analisar o arquivo CSV: {}", ex.getMessage(), ex);
            throw new IllegalArgumentException("Erro ao analisar o arquivo CSV: " + ex.getMessage());
        }
    }
}
