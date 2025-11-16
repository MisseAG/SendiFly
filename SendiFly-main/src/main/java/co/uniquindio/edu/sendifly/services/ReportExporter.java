package co.uniquindio.edu.sendifly.services;

import java.util.List;

public interface ReportExporter<T> {
    boolean export(List<T> items, String filePath);
}
