package com.pc.stock.scheduler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pc.model.stock.AVResponse;
import com.pc.model.stock.Template;
import com.pc.stock.dto.AVRequest;
import com.pc.stock.enums.Function;
import com.pc.stock.repo.TemplateRepository;
import com.pc.stock.service.AVResponseExtractor;
import com.pc.stock.service.AVService;
import com.pc.stock.service.StockAppService;
import com.thoughtworks.xstream.XStream;

@Component
public class AVTimeSeriesFetcher {

	Logger log = LoggerFactory.getLogger(AVTimeSeriesFetcher.class);

	private static final String AV_APIKEY = "NH0JRAHL2FB49D8K";

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	AVService avService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	StockAppService stockAppService;

//	@Scheduled(fixedRate = 5000)
	public void test() {
		Template template = templateRepository.findByConfigName("inputConfig");

		List<AVResponse> responses = new ArrayList<>();
		List<String> urls = avService.createAVRequestURLs();
		urls.forEach(url -> {
			responses.add(restTemplate.execute(url, HttpMethod.GET, null, new AVResponseExtractor<String>()));
		});

		boolean result = avService.updateStockData(responses);
		// stockAppService.updateLastRequestSent("AVREQUEST","Test");
	}

	@PostConstruct
	public void initApplcation() {
		AVRequest inputHDFC = new AVRequest(Function.TIME_SERIES_DAILY.toString(), "NSE:HDFC", null, null, "json", null,
				AV_APIKEY);
		AVRequest inputAxis = new AVRequest(Function.TIME_SERIES_DAILY_ADJUSTED.toString(), "NSE:AXIS", null, null,
				"json", null, AV_APIKEY);
		AVRequest inputRel = new AVRequest(Function.TIME_SERIES_DAILY.toString(), "MSFT", null, null, "json", null,
				AV_APIKEY);

		List<AVRequest> inputs = new ArrayList<>();
		inputs.add(inputHDFC);
		inputs.add(inputAxis);
		inputs.add(inputRel);
		log.debug("inputs = " + inputs);

		XStream xStream = new XStream();
		String inputXML = xStream.toXML(inputs);
		log.debug("input AV Request Template :\n" + inputXML);

		Template template = templateRepository.findByConfigName("inputConfig");
		if (template == null) {
			template = new Template();
			template.setConfigName("inputConfig");
		}

		template.setConfig(inputXML);

		templateRepository.save(template);
	}
}
