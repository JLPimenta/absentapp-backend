package com.absentapp.project.domain.repository.user;

import com.absentapp.project.domain.core.repository.BaseRepository;
import com.absentapp.project.domain.entity.ConfirmationToken;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends BaseRepository<ConfirmationToken> {
    ConfirmationToken findByToken(String confirmationToken);
}
