package com.pc.stock.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	TemplateRepository templateRepository;
	
	@Autowired
	StockAppService stockAppService;
	
	Logger log = LoggerFactory.getLogger(UploadController.class);

	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/upload")
	public String upload(HttpServletRequest request, Model model) {
		List<Template> templates = stockAppService.getAllTemplates();
		model.addAttribute("message", "File Upload");
		model.addAttribute("templates", templates);
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap != null && flashMap.get("result") != null) {
			String result = (String)flashMap.get("result");
			model.addAttribute("result", result);
		}
		return "upload";
	}

	@PostMapping("/upload")
	public String uploadFile(@ModelAttribute("configForm")ConfigUploadDTO configDTO, Model  model, RedirectAttributes redirectAttributes) {
		//			String content = new String(file.getBytes());
		System.out.println("Contents : \n"+configDTO);
		redirectAttributes.addFlashAttribute("result", "File Uploaded successfully.");
		return "redirect:/upload";
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

}
