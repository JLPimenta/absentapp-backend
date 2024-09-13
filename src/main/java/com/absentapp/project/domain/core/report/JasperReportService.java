package com.absentapp.project.domain.core.report;

import com.absentapp.project.domain.core.entity.BaseEntity;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class JasperReportService<T extends BaseEntity> implements IJasperReportService<T> {

    @Override
    public byte[] generateReport(List<T> entities, String fileFormat, String resourceLocation) throws JRException, IOException {
        JasperPrint jasperPrint = getJasperPrint(entities, resourceLocation);

        if (fileFormat.equalsIgnoreCase("pdf")) {
            return exportPdf(jasperPrint);
        }

        if (fileFormat.equalsIgnoreCase("csv")) {
            return exportCsv(jasperPrint);
        }

        throw new IllegalArgumentException("Formato de arquivo inválido: " + fileFormat);
    }

    private JasperPrint getJasperPrint(List<T> entities, String resourceLocation) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile(resourceLocation);
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(entities);

        return JasperFillManager.fillReport(jasperReport, null, dataSource);
    }

    private byte[] exportPdf(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
    }

    private byte[] exportCsv(JasperPrint jasperPrint) throws JRException {
        JRCsvExporter exporter = new JRCsvExporter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream, "UTF-8"));
        exporter.exportReport();

        return outputStream.toByteArray();
    }
}
