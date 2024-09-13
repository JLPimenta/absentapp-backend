package com.absentapp.project.domain.core.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface ICsvImporterService<T> {

    Integer upload(MultipartFile file) throws IOException;

    Set<T> convertToModel(MultipartFile file) throws IOException;
}
