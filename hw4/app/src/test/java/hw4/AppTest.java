import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileOutputStream;

import report.MsExcelReport;
import report.MsExcelReportGenerator;

import comparator.MsExcelComparator;

class Mom {
    public final String hello;
    public Mom() {
        this.hello = "Hello";
    }
}

class Son extends Mom {
    public final String world;
    public Son() {
        this.world = "World";
    }
}

class AppTest {
    @Test 
    void checkInheritance() {
        var reportGenerator = new MsExcelReportGenerator<Son>();
        var sons = new ArrayList<Son>();
        sons.add(new Son());

        var report = reportGenerator.generate(sons, "checkInheritance");
        var refFile = "/home/adam/Study/7thSem/Java/FifthHwRepo/hw4/app/src/test/resources/checkInheritanceRes.xls";

        assertTrue(MsExcelComparator.areEqual((MsExcelReport)report, refFile));
    }

    @Test
    void checkWriteTo() {
        var reportGenerator = new MsExcelReportGenerator<Son>();
        var sons = new ArrayList<Son>();
        sons.add(new Son());

        MsExcelReport report = null;

        try {
            report = (MsExcelReport)reportGenerator.generate(sons, "checkWriteTo");
        } catch (Exception e) {
            System.out.println("Generation error!");
            assertTrue(false);
        }
        
        try {
            report.writeTo(new FileOutputStream("tmp.xls"));
        } catch (Exception e) {
            System.out.println("Write error!");
            assertTrue(false);
        }

        assertTrue(MsExcelComparator.areEqual(report, "tmp.xls"));
    }

    @Test
    void checkAsBytes() {
        var reportGenerator = new MsExcelReportGenerator<Son>();
        var sons = new ArrayList<Son>();
        sons.add(new Son());

        MsExcelReport report = null;

        try {
            report = (MsExcelReport)reportGenerator.generate(sons, "checkAsBytes");
        } catch (Exception e) {
            System.out.println("Generation error!");
            assertTrue(false);
        }
        
        try {
            var stream = new FileOutputStream("tmp.xls");
            stream.write(report.asBytes());
        } catch (Exception e) {
            System.out.println("Write error!");
            assertTrue(false);
        }

        assertTrue(MsExcelComparator.areEqual(report, "tmp.xls"));
    }
}
