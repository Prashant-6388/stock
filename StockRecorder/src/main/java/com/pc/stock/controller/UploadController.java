package com.pc.stock.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pc.stock.model.Template;
import com.pc.stock.model.dto.ConfigUploadDTO;
import com.pc.stock.model.repo.TemplateRepository;
import com.pc.stock.service.StockAppService;

@Controller
public class UploadController {

	@Autowired
	StockAppService stockAppService;

	Logger log = LoggerFactory.getLogger(UploadController.class);

	@GetMapping("/upload")
	public String upload(HttpServletRequest request, Model model) {
		List<Template> templates = stockAppService.getAllTemplates();
		model.addAttribute("message", "File Upload");
		model.addAttribute("templates", templates);
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null && flashMap.get("result") != null) {
			String result = (String) flashMap.get("result");
			model.addAttribute("result", result);
		}
		return "upload";
	}

	@PostMapping("/upload")
	public String uploadFile(@ModelAttribute("configForm") ConfigUploadDTO configDTO, Model model,
			RedirectAttributes redirectAttributes) {
		// String content = new String(file.getBytes());
		log.debug("uploading file");

		List<Template> templates = stockAppService.getAllTemplates();
		model.addAttribute("message", "File Upload");
		model.addAttribute("templates", templates);

		try {
			String result = new BufferedReader(new InputStreamReader(configDTO.getFile().getInputStream())).lines()
					.collect(Collectors.joining("\n"));
			System.out.println("Contents : " + result);
			String response = stockAppService.storeTemplate(result, configDTO.getTemplateName());
			model.addAttribute("result", response);
			model.addAttribute("response", "SUCCESS");
		} catch (IOException e) {
			log.error(e.getMessage());
			model.addAttribute("result", "File Upload Failed");
			model.addAttribute("response", "ERROR");
		}
		return "upload";
	}

	@GetMapping("/getTemplateData")
	public ResponseEntity<?> getTemplateData(@RequestParam String templateName) {
		Template template = stockAppService.getTemplate(templateName);
		if (template != null)
			return ResponseEntity.ok(new String(template.getConfig()));
		return ResponseEntity.ok("");
	}

	/*
	 * @RequestMapping("/config") public String uploadConfig(@RequestParam("file")
	 * MultipartFile file, @RequestParam String function, RedirectAttributes
	 * redirectAttributes) { if (file.isEmpty()) {
	 * redirectAttributes.addFlashAttribute("message",
	 * "Please select a file to upload"); return "redirect:upload"; }
	 * 
	 * try {
	 * 
	 * // Get the file and save it somewhere byte[] bytes = file.getBytes();
	 * Template template = templateRepository.findByConfigName(function); if
	 * (template == null) { template = new Template();
	 * template.setConfigName("TIME_SERIES_DAILY"); }
	 * redirectAttributes.addFlashAttribute("message",
	 * "File Uploaded successfully."); return "redirect:upload"; } catch
	 * (IOException e) { redirectAttributes.addFlashAttribute("message",
	 * "File upload Failed"); log.error("File upload failed..."); } return
	 * "redirect:upload"; }
	 */
	@GetMapping("/uploadConfigData")
	public String uploadConfigData(HttpServletRequest request, Model model) {
		List<Template> templates = stockAppService.getAllTemplates();
		model.addAttribute("message", "File Upload");
		model.addAttribute("templates", templates);
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null && flashMap.get("result") != null) {
			String result = (String) flashMap.get("result");
			model.addAttribute("result", result);
		}
		return "upload";
	}
}
