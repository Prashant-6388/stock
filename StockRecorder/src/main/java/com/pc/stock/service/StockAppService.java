package com.pc.stock.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pc.model.stock.RequestLog;
import com.pc.model.stock.RequestLogId;
import com.pc.model.stock.Template;
import com.pc.stock.repo.RequestLogRepository;
import com.pc.stock.repo.TemplateRepository;

@Service
public class StockAppService {

	Logger log = LoggerFactory.getLogger(StockAppService.class);
	
	@Autowired
	TemplateRepository templateRepository;
	
	@Autowired
	RequestLogRepository requestLogRepository;
	
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
			e.printStackTrace();
		}
    }
	
	public String storeTemplate(String content, String templateName) {
		Template template = templateRepository.findByConfigName(templateName);
		template.getConfig().getClass();
		if(template == null)
			return "Template "+templateName + " not found";

		template.setConfig(content);
		templateRepository.save(template);
		return "Template uploaded successfully";
	}
	
	public void updateLastRequestSent(String requestType, String keyword) { //
		requestLogRepository.save(new RequestLog(new RequestLogId(requestType, keyword), LocalDate.now()));
	}
}
