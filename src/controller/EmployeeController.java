package controller;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.web.bind.annotation.RestController;

import base.base;
import dao.DBManager;
import models.Dictionary;
import models.Employee;
import models.Result;
import models.User;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class EmployeeController {
	
	@RequestMapping(value = "/getEmployees", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getAllEmployees()
	{
		return employees();
	}

	@RequestMapping(value = "/getEmployeeById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private Employee getEmployeebyId(@PathVariable("id") int id){
		List<Employee> emps = employees();
		
		for(Employee emp:emps)
		{
			if(emp.getId() == id)
				return emp;
		}
		
		return new Employee();
	}
	
	private List<Employee> employees(){
		Employee emp1 = new Employee(1, "Бат", "БЗД", "Багш");
		Employee emp2 = new Employee(2, "Болд", "БГД", "Эмч");
		Employee emp3 = new Employee(3, "Дорж", "СБД", "Жолоочdfsdfds");
		
		List<Employee> emps = new ArrayList<>();
		
		emps.add(emp1);
		emps.add(emp2);
		emps.add(emp3);
		
		return emps;
	}
	
	@RequestMapping(value = "/testFunction", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private void testFunction() throws IOException, JAXBException
	{
		System.out.println("Ehellee");
		String uri = "http://172.18.1.28:8080/TestM/searchWordMon";
			URL url = new URL(uri);
			HttpURLConnection connection = 
			    (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
			connection.setRequestProperty("word", "Морь");
			
			JAXBContext jc = JAXBContext.newInstance(Result.class);
			InputStream xml = connection.getInputStream();
			Result res = 
			    (Result) jc.createUnmarshaller().unmarshal(xml);
			if(res != null){
				List<Dictionary> dic = (List<Dictionary>)res.getItem();
				System.out.println(dic.get(0).getDescEng());
			}
			else {
				System.out.println("Null irsen bna");
			}
			 
			connection.disconnect();
	}
}
