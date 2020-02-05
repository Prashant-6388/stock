package com.pc.stock.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pc.stock.constant.AVConstants;
import com.pc.stock.enums.Validation;
import com.pc.stock.model.AVResponse;
import com.pc.stock.model.Stock;
import com.pc.stock.model.StockData;
import com.pc.stock.model.Template;
import com.pc.stock.model.dto.AVMetaDataDTO;
import com.pc.stock.model.dto.AVRequest;
import com.pc.stock.model.dto.AVTimeSeriesDTO;
import com.pc.stock.model.repo.StockDataRepository;
import com.pc.stock.model.repo.StockRepository;
import com.pc.stock.model.repo.TemplateRepository;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.security.AnyTypePermission;

@Service
public class AVService {

	@Autowired
	private TemplateRepository templateRepository;

	@Autowired
	private StockDataRepository stockDataRepository;

	@Autowired
	private StockRepository stockRepository;

	public static final Logger logger = LoggerFactory.getLogger(AVService.class);

	public List<String> createAVRequestURLs() {
		List<AVRequest> requests = getInputStockData(getStockInputConfig("inputConfig"));
		// for each stock enlisted in XML, need to create request depending on params

		List<String> urls = new ArrayList<>();
		requests.forEach(request -> {
			StringBuilder url = new StringBuilder(AVConstants.AVREQUSET_BASE_URL+"apikey="+request.getApiKey());
			validateAndBuildRequestURL(request, url);
			urls.add(url.toString());
		});

		return urls;
	}
	
	/*public String createAVRequestURL(AVRequest avRequest, String apiKey) {
		StringBuilder sb = new StringBuilder(AVConstants.AVREQUSET_BASE_URL);
		if (avRequest.getFunction() != null && !avRequest.getFunction().isEmpty())
			sb.append("function=").append(avRequest.getFunction());
		
		if (avRequest.getSymbol() != null && !avRequest.getSymbol().isEmpty()) 
			sb.append("&symbol=" + avRequest.getSymbol());
		
		if (avRequest.getInterval() !=null && !avRequest.getSymbol().isEmpty())
			sb.append("&interval=" + avRequest.getInterval());
		
		if (apiKey != null)
			sb.append("&apikey=").append(apiKey);
 
		if (avRequest.getDatatype() != null && !avRequest.getDatatype().isEmpty())
			sb.append("&datatype=").append(avRequest.getDatatype());
		return "";
	}*/

	private Template getStockInputConfig(String configName) {
		return templateRepository.findByConfigName(configName);
	}

	private List<AVRequest> getInputStockData(Template template) {
		XStream xstream = new XStream();
		xstream.setClassLoader(Thread.currentThread().getContextClassLoader());
		return (List<AVRequest>) xstream.fromXML(new String(template.getConfig()));
	}

	/*
	 * private String buildAVRequestURL(StockInputDTO inputDTO) { StringBuilder sb =
	 * new StringBuilder(AVConstants.AVREQUSET_BASE_URL); if
	 * (inputDTO.getFrequency() != null && !inputDTO.getFrequency().isEmpty())
	 * sb.append("function=").append(inputDTO.getFrequency()); if
	 * (inputDTO.getStockMarket() != null && !inputDTO.getStockMarket().isEmpty()) {
	 * if (inputDTO.getStockName() != null && !inputDTO.getStockName().isEmpty())
	 * sb.append("&symbol=" + inputDTO.getStockMarket() +
	 * ":").append(inputDTO.getStockName()); } else if (inputDTO.getStockName() !=
	 * null && !inputDTO.getStockName().isEmpty())
	 * sb.append("&symbol=").append(inputDTO.getStockName()); if
	 * (inputDTO.getApiKey() != null && inputDTO.getApiKey().isEmpty())
	 * sb.append("&apikey=").append(inputDTO.getApiKey());
	 * 
	 * if (inputDTO.getDataType() != null && !inputDTO.getDataType().isEmpty())
	 * sb.append("&datatype=").append(inputDTO.getDataType());
	 * 
	 * // restTemplate.execute(REST_URL1, HttpMethod.GET, null, new
	 * AVResponseExtractor<String>()); //
	 * https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=MSFT&
	 * apikey=demo return sb.toString(); }
	 */

	public boolean validateAndBuildRequestURL(AVRequest request, StringBuilder urlBuilder) {
		// TODO Auto-generated method stub
		if (request.getFunction() == null)
			return false;

		Template validatorTemplate = templateRepository.findByConfigName(request.getFunction());
		if(validatorTemplate == null)
			return true;
		else
			return validateAndBuildURL(request, validatorTemplate, urlBuilder);

	}

	private boolean validateAndBuildURL(AVRequest request, Template validatorTemplate, StringBuilder sb) {
		XStream xstream = new XStream();
		AVRequest requestValidation = null;
		try {
			xstream.addPermission(new AnyTypePermission());
			xstream.setClassLoader(Thread.currentThread().getContextClassLoader());
			String validationXML = new String(validatorTemplate.getConfig());
			requestValidation = (AVRequest)xstream.fromXML(validationXML);
		} catch (XStreamException ex) {
			logger.error("error converting Validation XML for " + request.getFunction(), ex);
			return false;
		}
		
		if (requestValidation.getFunction().equalsIgnoreCase(Validation.REQUIRED.toSting()))
			if(request.getFunction() == null) 
				return false;
			else
				sb.append("&function="+request.getFunction());

		if (requestValidation.getSymbol().equalsIgnoreCase(Validation.REQUIRED.toSting()))
				if(request.getSymbol() == null) 
					return false;
				else
					sb.append("&symbol="+request.getSymbol());

		if (requestValidation.getInterval().equalsIgnoreCase(Validation.REQUIRED.toSting()))
				if(request.getInterval() == null) 
					return false;
				else
					sb.append("&interval="+request.getInterval());
		
		if (requestValidation.getDatatype().equalsIgnoreCase(Validation.REQUIRED.toSting()))
				if(request.getDatatype() == null)
					return false;
				else
					sb.append("&datatype="+request.getDatatype());

		if (requestValidation.getKeyword().equalsIgnoreCase(Validation.REQUIRED.toSting()))
				if(request.getKeyword() == null)
					return false;
				else
					sb.append("&keyword="+request.getKeyword());
		

		if (requestValidation.getOutputSize().equalsIgnoreCase(Validation.REQUIRED.toSting()))
				if(request.getOutputSize() == null)
					return false;
				else
					sb.append("&outputsize="+request.getOutputSize());

		return true;
	}

	public boolean updateStockData(List<AVResponse> avResponses) {
		try {
			avResponses.forEach( avResponse -> {
				AVMetaDataDTO avMetaData = avResponse.getMetaData();
				Stock stock = stockRepository.findBySymbol(avMetaData.getSymbol());
				if (stock == null)
					stock = createStock(avMetaData);

				final Stock stockCopy = stock.clone();
				AVTimeSeriesDTO avTimeSeries = avResponse.getAvTimeSeries();
				Map<String, StockData> timeSeries = avTimeSeries.getStockPrice();
				timeSeries.forEach((key, value) -> {
					value.setDate(Timestamp.valueOf(key));
					value.setStock(stockCopy != null ? stockCopy : null);
				});

				Collection<StockData> stockDatas = timeSeries.values();
				stockDataRepository.saveAll(stockDatas);
			});
			return true;
		} catch (ClassCastException h) {
			return false;
		} catch (HibernateException h) {
			return false;
		} catch (Exception ex) {
			return false;
		}
	}

	public Stock createStock(AVMetaDataDTO avMetaData) {
		String description = "";
		String symbol;
		String stockExchange = "NYS";
		if (avMetaData.getSymbol().contains(":")) {
			String data[] = avMetaData.getSymbol().split(":", 2);
			stockExchange = data[0];
			symbol = data[1];
		} else {
			symbol = avMetaData.getSymbol();
		}
		return new Stock(symbol, description, stockExchange);
	}
}
