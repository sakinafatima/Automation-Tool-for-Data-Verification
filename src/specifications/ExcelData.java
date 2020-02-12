package specifications;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// TODO: Auto-generated Javadoc
/**
 * The Class ExcelData.
 */
public class ExcelData {
	
	/** The exceldata. */
	static String exceldata;

	/**
	 * Execute.
	 *
	 * @param spreadsheet2 the spreadsheet 2
	 * @param sheet the sheet
	 * @param row the row
	 * @param col the col
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void exe(String spreadsheet2, int sheet, int row ,int col) throws IOException{	



		File excel = new File (spreadsheet2);
		FileInputStream fis = new FileInputStream(excel);

		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheetAt(sheet);


		XSSFCell cell = sh.getRow(row).getCell(col);
		exceldata = String.valueOf(cell);
		wb.close();
		fis.close();


	}
	
	/**
	 * Read excel.
	 *
	 * @return the string
	 */
	public String readExcel()
	{
		return exceldata;
	}
}
