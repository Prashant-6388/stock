package com.pc.stock.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pc.stock.model.AVResponse;
import com.pc.stock.model.Template;
import com.pc.stock.model.repo.TemplateRepository;
import com.pc.stock.service.AVResponseExtractor;
import com.pc.stock.service.AVService;

@Component
public class AVTimeSeriesFetcher {

	@Autowired
	TemplateRepository templateRepository;
	
	@Autowired
	AVService avService;
	
	@Autowired
	RestTemplate restTemplate;
	
	
//	@Scheduled(fixedRate = 5000)
	public void test() {
		Template template = templateRepository.findByConfigName("inputConfig");
		
		List<AVResponse> responses = new ArrayList<>();
		List<String> urls = avService.createAVRequestURLs();
		urls.forEach(url -> {
			responses.add(restTemplate.execute(url, HttpMethod.GET, null, new AVResponseExtractor<String>()));
		} );
		
		boolean result = avService.updateStockData(responses);

	}
}
