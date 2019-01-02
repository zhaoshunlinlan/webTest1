package common;

import java.io.File;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Excel文件的读取和写入方法.
 * 
 * @author betty.shi
 * 
 */
public class Excel {
	public static void writeExcel(int s, int row, int column, String content, String filepath) {
		try {
		Workbook wb = Workbook.getWorkbook(new File(filepath)); 
		WritableWorkbook book = Workbook.createWorkbook(new File(filepath),wb);  
		WritableSheet sheet2 = book.getSheet(s);   
		WritableCell cell = sheet2.getWritableCell(row, column);    
		if (cell.getType() == CellType.LABEL) {
			Label l = (Label) cell;
			l.setString(content);
		}  
		
		book.write();
		book.close();  
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public static String readExcel(int s, int row, int column, String filepath) {
		String result = null;
		try {
        Workbook book = Workbook.getWorkbook(new File(filepath));
	    Sheet sheet=book.getSheet(s);  
	    Cell cell1=sheet.getCell(row, column);  
        result=cell1.getContents();       
        book.close(); 
		}catch(Exception e){
			System.out.println(e);
		}
		return result;
	}

}
