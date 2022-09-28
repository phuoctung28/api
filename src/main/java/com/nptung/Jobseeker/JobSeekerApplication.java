package com.nptung.Jobseeker;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.JsonObject;

@SpringBootApplication
public class JobSeekerApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(JobSeekerApplication.class, args);



		FileInputStream serviceAccount =
		new FileInputStream("D:\\Document\\Eclipse\\swp_Job Seeker\\Job-seeker\\src\\main\\resources\\jobportal-f8c24-firebase-adminsdk-usdiu-be69f3c927.json");
//		new FileInputStream("D:\\EclipseProject\\Job-seeker\\src\\main\\resources\\jobportal-f8c24-firebase-adminsdk-usdiu-be69f3c927.json");
		FirebaseOptions options = new FirebaseOptions.Builder()
				  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				  .build();

		
		FirebaseApp.initializeApp(options);

	}

}
