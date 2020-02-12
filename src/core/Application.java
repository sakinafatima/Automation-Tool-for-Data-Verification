package core;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.testng.TestListenerAdapter;
import org.testng.annotations.*;

import com.beust.testng.TestNG;

import listener.FailedTestListener;
import specifications.ExcelData;
import specifications.Webdata;

import static org.testng.Assert.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;

// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 */
@SuppressWarnings("deprecation")

@Listeners({FailedTestListener.class})
public class Application {
	
	/** The manager. */
	private ScriptEngineManager manager = new ScriptEngineManager();
	
	/** The engine. */
	private ScriptEngine engine = manager.getEngineByName("JavaScript");
	
	/** The rules. */
	private ArrayList<String> rules =new ArrayList<>();
	
	/** The result. */
	private Object result;
	
	/** The current rule. */
	private static String currentRule;
	
	/** The current row. */
	private static String[] currentRow;
	
	/** The sale. */
	static double sale=0;
	
	/** The quantity. */
	static double quantity = 0;
	
	/** The spreadsheet. */
	static String spreadsheet;
	
	/** The rulesfile. */
	static String rulesfile;
	
	/** The webdata. */
	public static String webdata;
	
	/** The webdata 1. */
	public static String webdata1;
	
	/** The spreadsheet 2. */
	static String spreadsheet2;

	/**
	 * Setup.
	 *
	 * @throws Exception the exception
	 */
	@BeforeTest
	public void setup() throws Exception {

		File f=new File(rulesfile);
		try {
			BufferedReader br=new BufferedReader(new java.io.FileReader(f));
			String line="";
			while((line = br.readLine()) != null){
				rules.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String script = "function  useVariable(){var tempValue = webdata;}";
		engine.eval(script);
	}
	
	/**
	 * Execute webpage.
	 *
	 * @return the string
	 */
	public String executeWebpage()
	{
		return webdata;
	}
	
	/**
	 * Execute webpage 1.
	 *
	 * @return the string
	 */
	public String executeWebpage1()
	{
		return webdata1;
	}

	/**
	 * Test rules.
	 *
	 * @param row the row
	 * @throws Exception the exception
	 */
	@Test(dataProvider="data")
	public void testRules(String[] row) throws Exception {
		Application r=new Application();
		ExcelData e=new ExcelData();
		engine.put("row", row);
		engine.put("reader", r);
		engine.put("excel", e);
		currentRow = row;
		for (String rule:rules) {		
			result=engine.eval(rule);
			currentRule = rule;
			assertTrue((Boolean)engine.eval(rule));

		}
	}
	
	/**
	 * Gets the provider.
	 *
	 * @return the provider
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@DataProvider(name="data")
	public Iterator<Object[]> getProvider() throws IOException {
		return new SpreadsheetIterator();
	}

	/**
	 * The Class SpreadsheetIterator.
	 */
	static class SpreadsheetIterator implements Iterator<Object[]> {

		/** The table. */
		String[][] table;
		
		/** The next row. */
		int nextRow=0;

		/**
		 * Instantiates a new spreadsheet iterator.
		 *
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		SpreadsheetIterator() throws IOException {


			File excel = new File (spreadsheet);
			FileInputStream fis = new FileInputStream(excel);

			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);

			int rowNum = sheet.getLastRowNum()+1;
			int colNum = sheet.getRow(0).getLastCellNum();
			table = new String[rowNum][colNum];
			for (int i=0; i<rowNum; i++){



				XSSFRow row = sheet.getRow(i);
				for (int j=0; j<colNum; j++){


					XSSFCell  cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
					int type = cell.getCellType();
					if (type== cell.CELL_TYPE_FORMULA)
					{
						System.out.println("Can not execute the formula record");

						System.exit(0);
					}
					else {
						String value = String.valueOf(cell);                             

						table[i][j] = value;
					}
				}            
			}    
			wb.close();
			fis.close();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return nextRow<table.length;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Object[] next() {
			return table[nextRow++];
		}

	}
	
	/**
	 * Gets the current rule.
	 *
	 * @return the current rule
	 */
	public static String getCurrentRule() {
		return currentRule;
	}

	/**
	 * Gets the current row.
	 *
	 * @return the current row
	 */
	public static String[] getCurrentRow() {

		System.out.println("Error Row: ID, Country, Product, Discount Band, Unit Sold, Sale Price, Gross Sale, Discount Given, Sales, Date, Month Name, Year :");
		return currentRow;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void main(String []args) throws ClassNotFoundException
	{   
		Scanner input = new Scanner(System.in);
		System.out.println("This is an automation technique to test the data in excel sheets\n\n");
		System.out.println("Please provide path to the excel sheet you want to test: ");
		spreadsheet= input.nextLine();
		System.out.println("Please provide path to the rules file:");
		rulesfile=input.nextLine();
		System.out.println("do you want to get any data from a web page? y/n");
		if (input.nextLine().equals("y"))
		{
			String link;
			String value1;
			String value2;
			System.out.println("Provide Link to the webpage: ");
			link=input.nextLine();
			System.out.println("Provide name of the element1: ");
			value1=input.nextLine();
			System.out.println("Provide name of the element2: ");
			value2=input.nextLine();
			Webdata d= new Webdata();
			d.execute(link, value1, value2);
		}
		System.out.println("do you want to get any data from any other excel sheet? y/n");
		if (input.nextLine().equals("y"))
		{
			String link;
			int sheet=0;
			int col;
			int row;
			System.out.println("Provide path of the excel sheet: ");
			link=input.nextLine();
			System.out.println("provide the sheet number ");
			col=input.nextInt();
			System.out.println("provide the row number ");
			row=input.nextInt();
			System.out.println("provide the column number ");
			col=input.nextInt();
			ExcelData d= new ExcelData();
			try {
				d.exe(link, sheet, col, row);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		TestNG t= new TestNG();
		Class<?>[] classlist= new Class<?>[1];
		classlist[0]=Class.forName("core.Application");
		t.setTestClasses(classlist);
		t.run();
	}
}

