import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelManager {

/*	private FileInputStream file; // = new FileInputStream(new File("/Users/thomasdoutre/Desktop/BilanTSP.xls"));
	//Get the workbook instance for XLS file 
	private HSSFWorkbook workbook; // = new HSSFWorkbook(file);
	 
	//Get first sheet from the workbook
	private HSSFSheet sheet; //= workbook.getSheetAt(0);
	 */
	//Get iterator to all the rows in current sheet
	//Iterator<Row> rowIterator = sheet.iterator();
	
// Row row = rowIterator.next();
	//Get iterator to all cells of current row
	//Iterator<Cell> cellIterator = row.cellIterator();
	public ExcelManager(String filePath) throws IOException{
	/*	this.file = new FileInputStream(new File(filePath));
		this.workbook = new HSSFWorkbook(file);
		this.sheet = workbook.getSheetAt(0);*/
	}
	
	
	public void affiche(String fileName){
		
		 try {
	            //Create the input stream from the xlsx/xls file
	            FileInputStream fis = new FileInputStream(fileName);
	             
	            //Create Workbook instance for xlsx/xls file input stream
	            Workbook workbook = null;
	            /*if(fileName.toLowerCase().endsWith("xlsx")){
	                workbook = new XSSFWorkbook(fis);
	            }else if(fileName.toLowerCase().endsWith("xls")){*/
	                workbook = new HSSFWorkbook(fis);
	            //}
	             
	            //Get the number of sheets in the xlsx file
	            int numberOfSheets = workbook.getNumberOfSheets();
	             System.out.println("number of sheets :" + numberOfSheets);
	            //loop through each of the sheets
	            for(int i=0; i < numberOfSheets; i++){
	                 
	                //Get the nth sheet from the workbook
	                Sheet sheet = workbook.getSheetAt(i);
	                 
	                //every sheet has rows, iterate over them
	                Iterator<Row> rowIterator = sheet.iterator();
	                while (rowIterator.hasNext()) 
	                {

	                    Row row = rowIterator.next();
	                     
	                    //Every row has columns, get the column iterator and iterate over them
	                    Iterator<Cell> cellIterator = row.cellIterator();
	                      
	                    while (cellIterator.hasNext()) 
	                    {
	                        //Get the Cell object
	                        Cell cell = cellIterator.next();
	                         
	                        //check the cell type and process accordingly
	                        switch(cell.getCellType()){
	                        case Cell.CELL_TYPE_STRING:
	                           /* if(shortCode.equalsIgnoreCase("")){
	                                shortCode = cell.getStringCellValue().trim();
	                            }else if(name.equalsIgnoreCase("")){
	                                //2nd column
	                                name = cell.getStringCellValue().trim();
	                            }else{
	                                //random data, leave it
*/	                                System.out.println("Random data::"+cell.getStringCellValue());
	                            //}
	                            break;
	                        case Cell.CELL_TYPE_NUMERIC:
	                            System.out.println("Random data::"+cell.getNumericCellValue());
	                        }
	                    } //end of cell iterator
	                  // Country c = new Country(name, shortCode);
	                   // countriesList.add(c);
	                } //end of rows iterator
	                 
	                 
	            } //end of sheets for loop
	             
	            //close file input stream
	            fis.close();
	             
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		
	}
	     
	
	
		
	
	public void modifierCellule(String fileName, int i, int j, double value) throws IOException{
         
        FileInputStream input_document = new FileInputStream(new File(fileName));
        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document); 
        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
        
        Cell cell = null; 
        cell = my_worksheet.getRow(i).getCell(j);
        cell.setCellValue(value);
        
        input_document.close();
        
        FileOutputStream output_file =new FileOutputStream(new File(fileName));
        my_xls_workbook.write(output_file);
        output_file.close();
        
        
		    }
	
	public String lireCellule(String fileName, int i, int j) throws IOException{
         
		String chaine = ""; 
		

        FileInputStream input_document = new FileInputStream(new File(fileName));
        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document); 
        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
        

        Cell cell = null; 
        cell = my_worksheet.getRow(i).getCell(j);

        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            System.out.println("Type String");
            chaine = cell.getRichStringCellValue().getString();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                System.out.println(cell.getDateCellValue());
            } else {
                System.out.println(cell.getNumericCellValue());
            }
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            System.out.println(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_FORMULA:
            System.out.println(cell.getCellFormula());
            break;
        default:
            System.out.println();
    }
        input_document.close();
        FileOutputStream output_file =new FileOutputStream(new File(fileName));
        my_xls_workbook.write(output_file);
        output_file.close();
        
        return chaine;
        
        
		    }
	public void setColor (String fileName, int i, int j, double percent){

       /* FileInputStream input_document = new FileInputStream(new File(fileName));
        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document); 
        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
        
        Cell cell = null;
        cell = my_worksheet.getRow(i).getCell(j);
        
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        row.getCell(0).setCellStyle(style);
        
        input_document.close();
        
        FileOutputStream output_file =new FileOutputStream(new File(fileName));
        my_xls_workbook.write(output_file);
        output_file.close();*/
		
	}
	
	public Color getColor(double power)
	{
	    double H = power * 0.4; // Hue (note 0.4 = Green, see huge chart below)
	    double S = 0.9; // Saturation
	    double B = 0.9; // Brightness

	    return Color.getHSBColor((float)H, (float)S, (float)B);
	}
}

