package report;

import java.io.*;
import org.apache.poi.ss.usermodel.Workbook;

public class MsExcelReport implements Report {
    private final Workbook workbook;

    public MsExcelReport(Workbook workbook) {
        this.workbook = workbook;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    @Override
    public void writeTo(OutputStream os) throws IOException {
        workbook.write(os);
    }

    @Override
    public byte[] asBytes() throws IOException {
        var stream = new ByteArrayOutputStream();
        this.writeTo(stream);
        return stream.toByteArray();
    }
}
