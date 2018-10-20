package com.example.demo.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.util.ValidationUtils;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("/repo")
public class HomeController {
	
	@RequestMapping(value="/ValidateJSON", method = RequestMethod.POST)
	@ResponseBody
	public String ValidateJSON(@RequestParam("JSONFfile") String json)
	{
			
		
		String url = null;
		try {
			url = URLDecoder.decode( this.getClass().getClassLoader().getResource("static/schemaJSON.txt").getFile().toString(), "UTF-8" );
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

		String schemaFile="";
		try {
			schemaFile = new String(Files.readAllBytes(Paths.get(url.substring(1))));
		
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String prettyJsonString="";
		try {
			if (ValidationUtils.isJsonValid(schemaFile, json)){
			    	System.out.println("Valid!");
			    	 System.out.println(json);
					 Gson gson = new GsonBuilder().setPrettyPrinting().create();
					 JsonParser jp = new JsonParser();
					 JsonElement je = jp.parse(json);
					 prettyJsonString = gson.toJson(je);
			    }else{
			    	System.out.println("NOT valid!");
			    }
		} catch (ProcessingException e) {
			e.printStackTrace();
			prettyJsonString=e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			prettyJsonString=e.getMessage();
		}
		return prettyJsonString;
	}

}
