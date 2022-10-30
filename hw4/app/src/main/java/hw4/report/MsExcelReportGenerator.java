package report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.lang.reflect.Field;
import java.lang.Class;
import java.util.function.*;
import java.util.*;

import pseudoname.ColumnPseudoName;

public class MsExcelReportGenerator<T> implements ReportGenerator<T> {
    private static List<Field> collectFields(Class<?> cur) {
        var fields = new ArrayList<Field>();

        while (cur != Object.class) {
            fields.addAll(Arrays.asList(cur.getDeclaredFields()));
            cur = cur.getSuperclass();
        }

        return fields;
    }

    @Override
    public Report generate(List<T> objects, String sheetName) {
        var workbook = new HSSFWorkbook();
        var sheet = workbook.createSheet(sheetName);

        var namesRow = sheet.createRow(sheet.getLastRowNum() + 1);

        var fields = collectFields(objects.get(0).getClass());

        /* 1. Get names */
        fields.stream().map(field -> getFieldName(field))
                       .forEach(name -> addCellToRow(namesRow, name));


        /* 2. Get values */
        objects.forEach(object -> {
            var row = sheet.createRow(sheet.getLastRowNum() + 1);
            fields.stream().map(field -> getFieldValue(field, object))
                           .forEach(value -> addCellToRow(row, value));
        });

        return new MsExcelReport(workbook);
    }

    private static void addCellToRow(Row row, String value) {
        row.createCell(Math.max(row.getLastCellNum(), 0)).setCellValue(value);
    }

    private static String getFieldName(Field field) {
        if (field.isAnnotationPresent(ColumnPseudoName.class)) {
            return field.getAnnotation(ColumnPseudoName.class).name();
        }

        return field.getName();
    }

    private String getFieldValue(Field field, T object) {
        field.setAccessible(true);
        try {
            return String.valueOf(field.get(object));
        } catch (IllegalAccessException e) {
            System.out.println("Illegal access;");
            throw new RuntimeException(e);
        }
    }
}
