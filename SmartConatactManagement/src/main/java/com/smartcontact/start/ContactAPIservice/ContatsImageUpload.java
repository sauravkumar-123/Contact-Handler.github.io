package com.smartcontact.start.ContactAPIservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontact.start.entities.Contact;

@Component
public class ContatsImageUpload {

	
	//Static path
		//public final String Uploaddirectory="D:\Projects\Smart Contact Management\SmartConatactManagement\src\main\resources\static\\img";
		
		//Dynamic Path
		private final String path="static/img/";
		public final String Uploaddirectory=new ClassPathResource(path).getFile().getAbsolutePath();
		
		
		public ContatsImageUpload() throws IOException {
			
		}



		public boolean uploadCheck(MultipartFile multipartFile) {
			boolean flag=false;
			try {
				
				//get data from api
				
				/*
				InputStream inputStream=multipartFile.getInputStream();
				byte data[]=new byte[inputStream.available()];
				inputStream.read(data);
				
				//write data on directory
				FileOutputStream fileOutputStream=new FileOutputStream(Uploaddirectory+File.separator+multipartFile.getOriginalFilename());
				fileOutputStream.write(data);
				fileOutputStream.flush();
				fileOutputStream.close();
				*/
				
				
				//Another way to upload file.
				String filedata=multipartFile.getOriginalFilename();
				
				Contact contact=new Contact();
				contact.setContact_imageurl(filedata);
				
				System.out.println("Image file data==>"+filedata);
				
				Files.copy(multipartFile.getInputStream(), Paths.get(Uploaddirectory+File.separator+multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				
				flag=true;
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return flag;
		}
}
