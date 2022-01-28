package com.fincarebank.demouser.export;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fincarebank.demouser.model.UserDetails;

public class UserExporter 
{
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<UserDetails> listOfUsers;
	
	public UserExporter(List<UserDetails> listOfUsers)
	{
		this.listOfUsers = listOfUsers;
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLines()
	{
		sheet = workbook.createSheet("UserDetails");
		
		Row row = sheet.createRow(0);
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(20);
//		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		
		createCell(row, 0, "User_ID", style);
		createCell(row, 1, "User_Name", style);
		createCell(row, 2, "User_Address", style);
		createCell(row, 3, "User_Pic", style);
		createCell(row, 4, "User_EmailId", style);
		createCell(row, 5, "User_Password", style);
		createCell(row, 6, "User_Mobile", style);
		createCell(row, 7, "User_RegisterDate", style);
		createCell(row, 8, "User_Verification", style);
	}
	
	private void createCell(Row row, int ColumnCount, Object value, CellStyle style)
	{
		sheet.autoSizeColumn(ColumnCount);
		Cell cell = row.createCell(ColumnCount);
		
		if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} 
		else if(value instanceof LocalDateTime)	{
			cell.setCellValue((LocalDateTime) value);
		}
		else if(value instanceof Boolean)	{
			cell.setCellValue((Boolean) value);
		}
		else if(value instanceof String){
			cell.setCellValue((String) value);
		}
		
		cell.setCellStyle(style);
	}
	
	private void writeDataLines()
	{
		int rowCount = 1;
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
//		style.setFont(font);
		
		for(UserDetails user : listOfUsers)
		{
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, user.getUserId(), style);
			createCell(row, columnCount++, user.getFullName(), style);
			createCell(row, columnCount++, user.getAddress(), style);
			createCell(row, columnCount++, user.getProfilePicture(), style);
			createCell(row, columnCount++, user.getEmailId(), style);
			createCell(row, columnCount++, user.getPassword(), style);
			createCell(row, columnCount++, user.getMobile(), style);
			createCell(row, columnCount++, user.getRegisteredDate(), style);
			createCell(row, columnCount++, user.isVerify(), style);
		}
	}
	
	
	public void export(HttpServletResponse response) throws IOException
	{
		writeHeaderLines();
		writeDataLines();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		
		outputStream.close();
	}
}
