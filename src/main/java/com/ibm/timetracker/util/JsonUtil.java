package com.ibm.timetracker.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.timetracker.model.ComputerConfig;
import com.ibm.timetracker.model.IpAddress;
import com.ibm.timetracker.model.MacAddress;

public class JsonUtil {
	
	public static void main(String[] args) {
		//Jackson2Example obj = new Jackson2Example();
		//obj.run();
		ComputerConfig comp = createDummyObject();
		createJSON(comp, null);
		
		comp = (ComputerConfig)getObjectFromJson("c:/tmp/comp.json");
		System.out.println(comp);
	}

	public static void createJSON(Object comp,String path) {
		ObjectMapper mapper = new ObjectMapper();
		if(path == null)
		{
			path = "c:\\tmp\\comp.json";
		}
		//mapper.
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		try {
			// Convert object to JSON string and save into a file directly
			mapper.writeValue(new File(path), comp);

			// Convert object to JSON string
			String jsonInString = mapper.writeValueAsString(comp);
			System.out.println(jsonInString);

			// Convert object to JSON string and pretty print
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(comp);
			System.out.println(jsonInString);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static ComputerConfig createDummyObject() {

		ComputerConfig comp = new ComputerConfig();

		comp.setComputerId(1);
		comp.setComputerName("ssaurabh");
		
		MacAddress macIP = new MacAddress();
		macIP.setComputerName(comp.getComputerName());
		macIP.setMacAddress("a-b-c-d");
		
		IpAddress ip = new IpAddress();
		ip.setIpAddress("192.168.1.3");
		ip.setParentMac(macIP.getMacAddress());
		macIP.addIpAddress(ip, false);
		//comp.getListMacs().add(macIP);
		comp.addMacAddress(macIP, false);

		return comp;

	}
	
	public static Object getObjectFromJson(String jsonFile) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		ComputerConfig comp = null;
		if(jsonFile == null)
		{
			jsonFile = "c:\\tmp\\comp.json";
		}
		try {

			// Convert JSON string from file to Object
			comp = mapper.readValue(new File(jsonFile), ComputerConfig.class);
			System.out.println(comp);

			// Convert JSON string to Object
			//String jsonInString = "{\"name\":\"mkyong\",\"salary\":7500,\"skills\":[\"java\",\"python\"]}";
			StringBuffer jsonInStringBuf = new StringBuffer();
			for(String str:CsvReader.getAllLines(jsonFile))
			{
				jsonInStringBuf.append(str);
			}
			String jsonInString = jsonInStringBuf.toString();		
			ComputerConfig staff1 = mapper.readValue(jsonInString, ComputerConfig.class);
			System.out.println(staff1);

			//Pretty print
			String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff1);
			System.out.println(prettyStaff1);
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return comp;
	}


}

