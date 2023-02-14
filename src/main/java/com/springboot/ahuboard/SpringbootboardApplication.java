package com.springboot.ahuboard;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringbootboardApplication {

	public static void main(String[] args) {
		generateDatabaseFile();
		SpringApplication.run(SpringbootboardApplication.class, args);
	}
	
	public static void generateDatabaseFile() {
		
		File userDirectory = new File(System.getProperty("user.home"));
		File target = new File(userDirectory, "test.mv.db");
		if(!target.exists()) {
			
			try {
				if(target.createNewFile()) {
				}
				
			}
			
			catch(Exception e ) {
			}
		}
		
		else {
			
		}
	}

}
