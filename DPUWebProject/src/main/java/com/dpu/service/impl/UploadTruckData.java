package com.dpu.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.dpu.entity.Division;
import com.dpu.entity.Status;
import com.dpu.entity.Truck;
import com.dpu.service.DivisionService;
import com.dpu.service.StatusService;
import com.dpu.service.TypeService;
import com.dpu.util.FileReaderUtility;

@Component
public class UploadTruckData {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	DivisionService divisionService;

	@Autowired
	TypeService typeService;

	@Autowired
	StatusService statusService;
	/*
	 * @Autowired private PaymentDao paymentDao;
	 */

	@Autowired
	private FileReaderUtility fileReaderUtility;

	public static void main(String[] args) throws IOException {

		ApplicationContext context = new ClassPathXmlApplicationContext("dpu-servlet.xml");
		UploadTruckData uploadDriverData = context.getBean(UploadTruckData.class);
		uploadDriverData.readExcelData();
		System.out.println("completed");
		
	}

	private void readExcelData() throws IOException {
		String excelFilePath = "src/main/resources/Trucks_List.xls";
		Session session = null;
		Transaction tx = null;
		try{
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
			Workbook workbook = new HSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				if (nextRow.getRowNum() > 0) {
					System.out.println("row number :" + nextRow.getRowNum());
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					
					Truck truck = new Truck();
					int ColoumnCount = 0;
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						if (ColoumnCount == 0) {
							if (cell != null) {
								DataFormatter formatter = new DataFormatter();
								String val = formatter.formatCellValue(cell);
								truck.setUnitNo(val);
							}
						}
						if (ColoumnCount == 1) {
							if (cell != null) {
								Division division = divisionService.getDivisionByName(cell.getStringCellValue());
								truck.setDivision(division);
							}
						}
						if (ColoumnCount == 4) {

							if (cell != null) {
								Status status = statusService.getByName(cell.getStringCellValue());
								truck.setStatus(status);
							}
						}

						ColoumnCount++;
					}

					session.save(truck);
				}
			}
			
			tx.commit();
			workbook.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
	}

}
