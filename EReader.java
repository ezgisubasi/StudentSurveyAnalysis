import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class EReader {

    public String filePath;
    Sheet page;

    EReader(){


    }

    EReader(String filePath){
        this.filePath = filePath;
    }

    public void readExcelFile(ArrayList<ArrayList<String>> Data) throws IOException, InvalidFormatException{
        //Workbook workbook = WorkbookFactory.create(new File(filePath));
        FileInputStream excelFile = new FileInputStream(new File(filePath));
        Workbook survey=null;
        if(filePath.endsWith("xlsx")) {
            survey = new XSSFWorkbook(excelFile);
        }
        if(filePath.endsWith("xls")){
            survey = new HSSFWorkbook(excelFile);
        }
        Sheet page = survey.getSheetAt(0); //Selecting spreadsheet
        Iterator<Row> iterator = page.iterator();

        DataFormatter dataFormatter = new DataFormatter();

        for (Row row: page) {
            ArrayList<String> cellData = new ArrayList<>();
            for(Cell cell: row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                cellData.add(cellValue);
            }
            Data.add(cellData);
        }
    }


}