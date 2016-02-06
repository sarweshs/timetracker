package com.ibm.timetracker.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.timetracker.model.ComputerConfig;
import com.ibm.timetracker.model.v2.ComputerConfigV2;
import com.ibm.timetracker.model.v2.MacAndIpV2;

public class JsonUtil {
	
	public static void main(String[] args) {
		//Jackson2Example obj = new Jackson2Example();
		//obj.run();
		/*ComputerConfig comp = createDummyObject();
		createJSON(comp, null);
		
		comp = (ComputerConfig)getObjectFromJson("c:/tmp/comp.json");
		System.out.println(comp);*/
		
		ComputerConfigV2 comp = createDummyObject();
		createEncryptedJSON(comp, null);
		
		comp = (ComputerConfigV2)getObjectFromEncryptedJson(null);
		System.out.println(comp);
	}

	public static String createJSON(Object comp,String path) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = null;
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
			jsonInString = mapper.writeValueAsString(comp);
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
		return jsonInString;
	}
	
	public static String createEncryptedJSON(Object comp,String path) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = null;
		if(path == null)
		{
			path = "c:\\tmp\\encryptedcomp.json";
		}
		//mapper.
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		try {
			// Convert object to JSON string and save into a file directly
			//mapper.writeValue(new File(path), comp);

			// Convert object to JSON string
			jsonInString = mapper.writeValueAsString(comp);
			String encryptedJsonInString = EncryptionDecryptionUtil.encrypt(jsonInString, null);
			FileUtility.writeToFile(path, encryptedJsonInString);
			//System.out.println(jsonInString);

			// Convert object to JSON string and pretty print
			//jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(comp);
			//System.out.println(jsonInString);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonInString;
	}

	private static ComputerConfigV2 createDummyObject() {

		ComputerConfigV2 comp = new ComputerConfigV2();

		comp.setComputerName("ssaurabh");
		
		MacAndIpV2 macIP = new MacAndIpV2();
		macIP.setComputerName(comp.getComputerName());
		macIP.setMacAddress("a-b-c-d");
		
		
		//comp.getListMacs().add(macIP);
		comp.addMacAddress(macIP, false);

		return comp;

	}
	
	public static Object getObjectFromJson(String jsonFile) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		ComputerConfigV2 comp = null;
		if(jsonFile == null)
		{
			jsonFile = "c:\\tmp\\comp.json";
		}
		try {

			// Convert JSON string from file to Object
			comp = mapper.readValue(new File(jsonFile), ComputerConfigV2.class);
			System.out.println(comp);

			// Convert JSON string to Object
			//String jsonInString = "{\"name\":\"mkyong\",\"salary\":7500,\"skills\":[\"java\",\"python\"]}";
			StringBuffer jsonInStringBuf = new StringBuffer();
			for(String str:CsvReader.getAllLines(jsonFile))
			{
				jsonInStringBuf.append(str);
			}
			String jsonInString = jsonInStringBuf.toString();		
			ComputerConfigV2 staff1 = mapper.readValue(jsonInString, ComputerConfigV2.class);
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
	
	public static Object getObjectFromEncryptedJson(String jsonFile) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		ComputerConfigV2 comp = null;
		if(jsonFile == null)
		{
			jsonFile = "c:\\tmp\\encryptedcomp.json";
		}
		try {

			// Convert JSON string from file to Object
			//comp = mapper.readValue(new File(jsonFile), ComputerConfig.class);
			//System.out.println(comp);

			// Convert JSON string to Object
			//String jsonInString = "{\"name\":\"mkyong\",\"salary\":7500,\"skills\":[\"java\",\"python\"]}";
			/*StringBuffer jsonInStringBuf = new StringBuffer();
			for(String str:CsvReader.getAllLines(jsonFile))
			{
				jsonInStringBuf.append(str);
			}
			String jsonInString = jsonInStringBuf.toString();	*/	
			String jsonInString = FileUtility.readFromFile(jsonFile);
			jsonInString = EncryptionDecryptionUtil.decrypt(jsonInString, null);
			comp = mapper.readValue(jsonInString, ComputerConfigV2.class);
			System.out.println(comp);

			//Pretty print
			//String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff1);
			//System.out.println(prettyStaff1);
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comp;
	}


}

