package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {
    // DataProvider method 1:
    @DataProvider(name = "LoginData")
    public String [][] getData() throws IOException {
        String path = ".\\testData\\OpenCartLoginData.xlsx";    // Excel file location in testData folder

        ExcelUtility xlUtil = new ExcelUtility(path);   // Initializing an object of ExcelUtility class

        int totalNoOfRows = xlUtil.getRowCount("Sheet1");
        int totalNoOfCols = xlUtil.getCellCount("Sheet1", 1);

        String loginData[][] = new String[totalNoOfRows][totalNoOfCols];    // created two-dimensional array with same dimensions
                                                                            // as the data in the Excel sheet

        // Read the data Excel file and storing it in the 2-dimensional array at run-time:-
        // i - rows, j - cols
        for (int i = 1; i <= totalNoOfRows; i++) {  // to ignore header part in Excel sheet start from 1
            for (int j = 0; j < totalNoOfCols; j++) {   // starting from 0
                loginData[i - 1][j] = xlUtil.getCellData("Sheet1", i, j);
            }
        }

        return loginData;   // returning the two-dimensional array to the relevant test method
    }

    // DataProvider method 2

    // DataProvider method 3

    // DataProvider method 4
}
