package com.mycompany.hr;

import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.mycompany.hr.definitions.HumanResource;
import com.mycompany.hr.definitions.HumanResourceService;
import com.mycompany.hr.schemas.EmployeeType;
import com.mycompany.hr.schemas.HolidayRequest;
import com.mycompany.hr.schemas.HolidayType;
import com.mycompany.hr.schemas.ObjectFactory;

public class HolidayWSClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectFactory factory = new ObjectFactory();
		
		HolidayRequest holidayRequest = factory.createHolidayRequest();
		EmployeeType employeeType = factory.createEmployeeType();
		employeeType.setFirstName("Jayram");
		employeeType.setLastName("Rout");
		employeeType.setNumber(new BigInteger("100"));
		
		HolidayType holidayType = factory.createHolidayType();
		
		XMLGregorianCalendar date1 = null ;
		XMLGregorianCalendar date2 = null;
		try {
			GregorianCalendar c1 = new GregorianCalendar();
			c1.setTime(new Date());
			date1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
			
			GregorianCalendar c2 = new GregorianCalendar();
			c2.setTime(new Date());
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		
		holidayType.setStartDate(date1);
		holidayType.setEndDate(date2);
		holidayRequest.setEmployee(employeeType);
		holidayRequest.setHoliday(holidayType);
		
		HumanResourceService service = new HumanResourceService();
		HumanResource resource = service.getHumanResourceSoap11();
		
		resource.holiday(holidayRequest);

	}

}
