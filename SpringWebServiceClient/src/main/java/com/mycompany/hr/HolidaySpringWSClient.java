package com.mycompany.hr;

import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.mycompany.hr.schemas.EmployeeType;
import com.mycompany.hr.schemas.HolidayRequest;
import com.mycompany.hr.schemas.HolidayType;
import com.mycompany.hr.schemas.ObjectFactory;

public class HolidaySpringWSClient {
	private static String URI = "http://localhost:8080/SpringWebService/holidayService/";
	private static ApplicationContext context = null;
	private static WebServiceTemplate wsGatewaySupport ;

	static{
		if (context == null) {
			context = new ClassPathXmlApplicationContext(
					new String[] {
					"classpath:holiday-application-context.xml" });
		}
		wsGatewaySupport = (WebServiceTemplate)context.getBean("wsGatewaySupport");
	}
	public static void main(String[] args) {
		HolidayRequest holidayRequest = getHolidayRequest();
		wsGatewaySupport.setDefaultUri(URI);
		wsGatewaySupport.marshalSendAndReceive(holidayRequest);
	}

	public static HolidayRequest getHolidayRequest(){
		ObjectFactory factory = new ObjectFactory();
		HolidayRequest holidayRequest = factory.createHolidayRequest();
		EmployeeType employeeType = factory.createEmployeeType();
		employeeType.setFirstName("Tanushree");
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

		return holidayRequest;
	}

}
