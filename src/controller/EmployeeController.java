package controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import base.base;
import dao.DBManager;
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
		
		return null;
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
	
}
