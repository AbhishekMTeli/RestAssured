package api.test.api.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

public class ExcelUtils {

    private String filePath;
    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtils(String filePath, String sheetName) throws IOException {
        this.filePath = filePath;
        File file = new File(filePath);

        if (!file.exists()) {
            workbook = new XSSFWorkbook(); // Create new workbook if file doesn't exist
            workbook.createSheet(sheetName);
            saveFile();
        } else {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            fis.close();
        }

        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
    }

    // Get total number of rows
    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    // Get total number of columns in a row
    public int getColumnCount(int rowNum) {
        Row row = sheet.getRow(rowNum);
        return (row != null) ? row.getPhysicalNumberOfCells() : 0;
    }

    // Read data from cell with multiple data types support
    public Object getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return null;

        Cell cell = row.getCell(colNum);
        if (cell == null) return null;

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() : cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return "UNKNOWN";
        }
    }

    // Write data to cell
    public void setCellData(int rowNum, int colNum, String data) throws IOException {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }

        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        cell.setCellValue(data);
        saveFile();
    }

    // Write multiple rows of dummy test data
    public void writeTestData() throws IOException {
        String[][] data = {
            {"1", "user1", "John", "Doe", "john.doe@email.com", "password123", "1234567890", "0"},
            {"2", "user2", "Jane", "Smith", "jane.smith@email.com", "password456", "9876543210", "0"},
            {"3", "user3", "Alice", "Johnson", "alice.j@email.com", "password789", "5556667777", "0"}
        };

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                setCellData(i, j, data[i][j]);
            }
        }
    }

    // Read data into a HashMap (first row is treated as header)
    public HashMap<String, String> readRowData(int rowNum) {
        HashMap<String, String> rowData = new HashMap<>();
        Row headerRow = sheet.getRow(0);
        Row dataRow = sheet.getRow(rowNum);

        if (headerRow != null && dataRow != null) {
            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                String key = headerRow.getCell(i).getStringCellValue();
                String value = (dataRow.getCell(i) != null) ? dataRow.getCell(i).getStringCellValue() : "";
                rowData.put(key, value);
            }
        }
        return rowData;
    }

    // Delete a specific row
    public void deleteRow(int rowNum) throws IOException {
        if (rowNum >= 0 && rowNum < sheet.getPhysicalNumberOfRows()) {
            int lastRow = sheet.getLastRowNum();
            sheet.shiftRows(rowNum + 1, lastRow, -1);
        }
        saveFile();
    }

    // Clear entire sheet data
    public void clearSheet() throws IOException {
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            rowIterator.next();
            rowIterator.remove();
        }
        saveFile();
    }

    // Save file after modification
    private void saveFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    // Close workbook
    public void closeWorkbook() throws IOException {
        workbook.close();
    }

    // Main method to test functionalities
    public static void main(String[] args) {
        try {
            String filePath = "./TestData/dummy_data.xlsx";
            String sheetName = "Name";
            
            ExcelUtils excel = new ExcelUtils(filePath, sheetName);

            // Write dummy test data
            excel.writeTestData();
            System.out.println("Dummy test data written!");

            // Read & print data from Excel
            System.out.println("Reading Data:");
            for (int i = 0; i < excel.getRowCount(); i++) {
                for (int j = 0; j < excel.getColumnCount(i); j++) {
                    System.out.print(excel.getCellData(i, j) + "\t");
                }
                System.out.println();
            }

            // Read a specific row as HashMap
            System.out.println("Row 1 Data as HashMap: " + excel.readRowData(1));

            // Delete a row
            excel.deleteRow(1);
            System.out.println("Row 1 deleted!");

            // Clear all data from sheet
            excel.clearSheet();
            System.out.println("Sheet cleared!");

            excel.closeWorkbook();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
