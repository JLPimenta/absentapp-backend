package com.absentapp.project.domain.model.User;

import com.absentapp.project.domain.core.mapper.IBaseMapper;
import com.absentapp.project.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper extends IBaseMapper<User, UserRequest, UserResponse> {
}
