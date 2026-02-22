package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {
    // access modifiers kept as public and static for variables as these same variables can be referred to any of
    // user-defined methods in this util class
    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook wb;
    public XSSFSheet wsh;
    public XSSFRow row;
    public XSSFCell cell;
    public XSSFCellStyle style;
    public XSSFFont fontStyle;
    String xlsxFilePath;

    public ExcelUtility(String path) {
        this.xlsxFilePath = path;
    }

    // access modifiers kept as public and static as this allows the user-defined methods to be accessed
    // from anywhere in the project directly using ExcelUtil class name without need of instantiating its objects first
    public int getRowCount(String xlsxSheet) throws IOException {
        fi = new FileInputStream(xlsxFilePath);
        wb = new XSSFWorkbook(fi);
        wsh = wb.getSheet(xlsxSheet);

        int rowCount = wsh.getLastRowNum();

        wb.close();
        fi.close();

        return rowCount;
    }

    public int getCellCount(String xlsxSheet, int rowNum) throws IOException {
        fi = new FileInputStream(xlsxFilePath);
        wb = new XSSFWorkbook(fi);
        wsh = wb.getSheet(xlsxSheet);
        row = wsh.getRow(rowNum);

        int cellCount = row.getLastCellNum();

        wb.close();
        fi.close();

        return cellCount;
    }

    // Reads data from one specific cell in the excel sheet:
    public String getCellData(String xlsxSheet, int rowNum, int cellNum) throws IOException {
        fi = new FileInputStream(xlsxFilePath);
        wb = new XSSFWorkbook(fi);
        wsh = wb.getSheet(xlsxSheet);
        row = wsh.getRow(rowNum);
        cell = row.getCell(cellNum);

        DataFormatter formatter = new DataFormatter();
        String data;

        // Reason behind try-catch usage is to assign data variable with empty string value if
        // exception is encountered cause there is definitely a decent chance getCell() returns
        // a null value cause the cell in the excel sheet holds nothing
        try {
//            Approach # 1 to format cell object value into a String:
//            data = cell.toString();

            // OR Approach # 2: use DataFormatter class object variable's formatCellValue method from Apache POI to format cell as a
            //               String regardless of the original data type of the cell in the Excel Sheet:
            data = formatter.formatCellValue(cell);

        } catch(Exception e) {
            data = "";
        }

        wb.close();
        fi.close();

        return data;
    }

    public void setCellData (
            String xlsxSheet,
            int rowNum,
            int cellNum,
            String data
    ) throws IOException {

        File xlsxFile = new File(xlsxFilePath);
        if (!xlsxFile.exists()) {   // If file does not exist then create a new Excel file
            wb = new XSSFWorkbook();
            fo = new FileOutputStream(xlsxFilePath);
            wb.write(fo);
        }

        fi = new FileInputStream(xlsxFilePath);
        wb = new XSSFWorkbook(fi);

        if (wb.getSheetIndex(xlsxSheet) == -1) {   // If Excel sheet does not exist then create a new one
            wb.createSheet(xlsxSheet);
        }
        wsh = wb.getSheet(xlsxSheet);

        if (wsh.getRow(rowNum) == null) {   // If row does not exist in the Excel sheet, then create a new one
            wsh.createRow(rowNum);
        }
        row = wsh.getRow(rowNum);

        cell = row.createCell(cellNum);
        cell.setCellValue(data);

        fo = new FileOutputStream(xlsxFilePath);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();
    }

    public void applyStyleWhenPass (String xlsxSheet, int rowNum, int cellNum) throws IOException {
        fi = new FileInputStream(xlsxFilePath);
        wb = new XSSFWorkbook(fi);
        wsh = wb.getSheet(xlsxSheet);
        row = wsh.getRow(rowNum);
        cell = row.getCell(cellNum);

        style = wb.createCellStyle();
        fontStyle = wb.createFont();

        fontStyle.setBold(true);
        fontStyle.setColor(IndexedColors.WHITE.getIndex());

        style.setFont(fontStyle);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());

        cell.setCellStyle(style);

        fo = new FileOutputStream(xlsxFilePath);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();
    }

    public void applyStyleWhenFail (String xlsxSheet, int rowNum, int cellNum) throws IOException {
        fi = new FileInputStream(xlsxFilePath);
        wb = new XSSFWorkbook(fi);
        wsh = wb.getSheet(xlsxSheet);
        row = wsh.getRow(rowNum);
        cell = row.getCell(cellNum);

        style = wb.createCellStyle();
        fontStyle = wb.createFont();

        fontStyle.setBold(true);
        fontStyle.setColor(IndexedColors.WHITE.getIndex());

        style.setFont(fontStyle);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());

        cell.setCellStyle(style);

        fo = new FileOutputStream(xlsxFilePath);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();
    }
}
