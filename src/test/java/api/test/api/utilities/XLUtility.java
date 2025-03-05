package api.test.api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class XLUtility {
    private String path;
    private FileInputStream fi;
    private FileOutputStream fo;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;

    // Constructor to initialize file path
    public XLUtility(String path) {
        this.path = path;
    }

    // Get row count of a sheet
    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum(); // Returns last row index (0-based)
        workbook.close();
        fi.close();
        return rowCount;
    }

    // Get column count in a row
    public int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        int cellCount = row.getLastCellNum(); // Returns number of cells (1-based)
        workbook.close();
        fi.close();
        return cellCount;
    }

    // Get cell data as string
    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        String data;
        if (cell.getCellType() == CellType.STRING)
            data = cell.getStringCellValue();
        else if (cell.getCellType() == CellType.NUMERIC)
            data = String.valueOf((int) cell.getNumericCellValue());
        else
            data = "";

        workbook.close();
        fi.close();
        return data;
    }

    // Set cell data
    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        if (sheet.getRow(rownum) == null)
            sheet.createRow(rownum);
        row = sheet.getRow(rownum);

        if (row.getCell(colnum) == null)
            row.createCell(colnum);
        cell = row.getCell(colnum);
        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
    }

    // Get all values from a specific column (supports getUserNames)
    public String[] getColumnData(String sheetName, int colnum) throws IOException {
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();
        String[] data = new String[rowCount];

        for (int i = 1; i <= rowCount; i++) { // Skipping header row
            row = sheet.getRow(i);
            cell = row.getCell(colnum);
            data[i - 1] = (cell != null) ? cell.getStringCellValue() : "";
        }

        workbook.close();
        fi.close();
        return data;
    }
}
