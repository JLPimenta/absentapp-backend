package com.absentapp.project.domain.core.report;

import com.absentapp.project.domain.core.entity.BaseEntity;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;


public interface IJasperReportService<T extends BaseEntity> {

    byte[] generateReport(List<T> entities, String fileFormat, String resourceLocation) throws JRException, IOException;
}
