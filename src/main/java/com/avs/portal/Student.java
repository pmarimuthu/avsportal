package com.avs.portal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.avs.portal.enums.LogStatusEnum;
import com.avs.portal.util.Logger;

public class Student {
	private Integer rollNumber;
	private String name;
	
	public Integer getRollNumber() {
		return rollNumber;
	}
	public Student setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
		return this;
	}
	public String getName() {
		return name;
	}
	public Student setName(String name) {
		this.name = name;
		return this;
	}
	
	public static void main(String[] args) {
		Student anil = new Student().setName("Anil").setRollNumber(101);
		Student ravi = new Student().setName("Ravi").setRollNumber(201);
		Student raju = new Student().setName("Raju").setRollNumber(301);
		
		List<Student> students = Arrays.asList(anil, ravi, raju);
		
		List<Integer> rollNumbers = students.stream().map(Student :: getRollNumber).collect(Collectors.toList());
		Logger.log(LogStatusEnum.INFO, "Sudent > main >", rollNumbers.toString());
		
	}
}
