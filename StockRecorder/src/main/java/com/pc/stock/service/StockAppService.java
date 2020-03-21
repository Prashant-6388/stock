package com.pc.stock.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pc.stock.model.Template;
import com.pc.stock.model.repo.TemplateRepository;

@Service
public class StockAppService {

	@Autowired
	TemplateRepository templateRepository;
	
	public List<Template> getAllTemplates(){
		return templateRepository.findAll();
	}
	
	public Template getTemplate(String templateName) {
		return templateRepository.findByConfigName(templateName);
	}
	
	public void storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = Paths.get("D:/Test");
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public String storeTemplate(String content, String templateName) {
		Template template = templateRepository.findByConfigName(templateName);
		if(template == null)
			return "Template "+templateName + " not found";
		
		template.setConfig(content.getBytes());
		
		templateRepository.save(template);
		return "Template uploaded successfully";
	}
}
