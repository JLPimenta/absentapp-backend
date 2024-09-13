package com.absentapp.project.domain.service.sala;

import com.absentapp.project.api.config.exception.DomainException;
import com.absentapp.project.domain.core.service.BaseService;
import com.absentapp.project.domain.entity.Participante;
import com.absentapp.project.domain.entity.Sala;
import com.absentapp.project.domain.entity.User;
import com.absentapp.project.domain.repository.sala.SalaRepository;
import com.absentapp.project.domain.repository.sala.SalaSpec;
import com.absentapp.project.domain.service.participante.IParticipanteService;
import com.absentapp.project.domain.service.presenca.IPresencaService;
import com.absentapp.project.domain.service.user.IUserService;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class SalaService extends BaseService<Sala> implements ISalaService {

    private final IParticipanteService participanteService;
    private final IPresencaService presencaService;
    private final IUserService userService;

    protected SalaService(SalaRepository repository, IParticipanteService participanteService, IPresencaService presencaService, IUserService userService) {
        super(repository);

        this.participanteService = participanteService;
        this.presencaService = presencaService;
        this.userService = userService;
    }

    @Override
    public void validate(Sala sala) throws DomainException {
        String nome = sala.getNome();

        if (this.getRepository().existsByNomeIgnoreCase(nome)) {
            throw new DomainException("Já existe uma sala nomeada como " + nome);
        }

        getParticipanteService().validate(sala.getParticipantes());
    }

    @Override
    public Sala create(final Sala sala) throws DomainException {
        validate(sala);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sala.setUser(user);

        for (Participante participante : sala.getParticipantes()) {
            participante.setSala(sala);
        }

        return this.getRepository().save(sala);
    }

    @Override
    public Sala update(Sala sala, String id) throws DomainException {
        Sala existent = this.getRepository().findById(id).orElseThrow(() -> new DomainException("Item não encontrado"));

        bind(existent, sala);

        return this.getRepository().save(existent);
    }

    @Override
    public void bind(Sala entity, Sala update) {
        BeanUtils.copyProperties(update, entity, "id", "dataCriacao", "dataAtualizacao", "participantes", "user");
    }

    @Override
    public Page<Sala> findAll(Boolean situacao, Pageable pageable) {
        final SalaSpec spec = SalaSpec.builder()
                .userId(userService.getContextUser().getId())
                .situacao(situacao)
                .build();

        return this.getRepository().findAll(spec, pageable);
    }

    @Override
    public List<Sala> findAll(Boolean situacao) {
        final SalaSpec spec = SalaSpec.builder()
                .userId(userService.getContextUser().getId())
                .situacao(situacao)
                .build();

        return this.getRepository().findAll(spec);
    }

    @Override
    public void delete(String id) {
        getPresencaService().deleteAllBySala(id);
        super.delete(id);
    }

    @Override
    public SalaRepository getRepository() {
        return (SalaRepository) super.getRepository();
    }
}
