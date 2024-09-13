package com.absentapp.project.domain.model.auth;

import com.absentapp.project.domain.core.mapper.IBaseMapper;
import com.absentapp.project.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAuthMapper extends IBaseMapper<User, SignUpRequest, LoggedUser> {
}
