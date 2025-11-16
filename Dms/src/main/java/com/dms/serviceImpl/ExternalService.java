package com.dms.serviceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.dms.configuration.WebClientConfiguration.WebClientFactory;
import com.dms.dto.DmsDocumentDto;
import com.dms.dto.request.DmsRequestForServiceDto;

@Service
public class ExternalService {

	private static final Logger logger = LogManager.getLogger(ExternalService.class);

	@Autowired
	private WebClientFactory webClientFactory;

	public DmsDocumentDto getListofDocToUploadOrUploaded(DmsRequestForServiceDto dmsRequestForServiceDto) {

		WebClient webClientForDmsStagingServicesUrl = webClientFactory.createWebClientForDmsStagingServicesUrl();

		return webClientForDmsStagingServicesUrl
				.get()
				.uri(uriBuilder -> uriBuilder.path("/VtDocuments")
				.build(dmsRequestForServiceDto)).retrieve()
				.bodyToMono(DmsDocumentDto.class)
				.doOnError(WebClientResponseException.class, ex -> {
					logger.error("Error response: " + ex.getResponseBodyAsString());
				}).block();
	}

}
