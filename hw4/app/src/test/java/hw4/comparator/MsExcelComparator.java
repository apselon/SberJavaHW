package comparator;

import java.util.*;
import java.io.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import report.MsExcelReport;

public class MsExcelComparator {
    public static boolean areEqual(MsExcelReport report, String filename) {
        FileInputStream file = null;
        XSSFWorkbook reference = null;

        try {
            file = new FileInputStream(filename);
            reference = new XSSFWorkbook(file);
        } catch (Exception e) {
            System.out.println("Problem during report analysis");
            return false;
        }

        var repSheet = report.getWorkbook().getSheetAt(0);
        var refSheet = reference.getSheetAt(0);

        if (repSheet.getPhysicalNumberOfRows() != refSheet.getPhysicalNumberOfRows()) {
            return false;
        }

        var repRows = repSheet.rowIterator();
        var refRows = refSheet.rowIterator();

        while (repRows.hasNext() && refRows.hasNext()) {
            if (!rowsAreEqual(repRows.next(), refRows.next())) {
                return false;
            }
        }

        return true;
    }

    static boolean rowsAreEqual(Row repRow, Row refRow) {
        if (repRow.getPhysicalNumberOfCells() != refRow.getPhysicalNumberOfCells()) {
            return false;
        }

        var repCells = repRow.cellIterator();
        var refCells = refRow.cellIterator();

        while (repCells.hasNext() && refCells.hasNext()) {
            if (!repCells.next().getStringCellValue().equals(refCells.next().getStringCellValue())) {
                return false;
            }
        }

        return true;
    }
}
