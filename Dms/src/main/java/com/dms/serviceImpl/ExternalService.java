package com.dms.serviceImpl;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.dms.configuration.WebClientConfiguration.WebClientFactory;
import com.dms.dto.DmsDocumentDto;
import com.dms.dto.request.DmsRequestForServiceDto;

import reactor.core.publisher.Mono;

@Service
public class ExternalService {

	private static final Logger logger = LogManager.getLogger(ExternalService.class);

	@Autowired
	private WebClientFactory webClientFactory;

	/**
	 * Calls the DMS API using the same headers/signature logic as the provided JAR.
	 * This method obtains headers from the JAR's private getHeader() via reflection,
	 * computes the signature using the jar's SignatureUtil, adds it to headers and
	 * performs a POST to the exact URL pattern the jar uses.
	 */
	public DmsDocumentDto getListofDocToUploadOrUploaded(DmsRequestForServiceDto requestDto) {

		// base values used by the jar usage in your project
		final String baseUrl = "https://staging.parivahan.nic.in/dms-app/common-app";
		final String apiMethod = "common-app";   // second constructor arg in JAR usage
		final String className = "VtDocuments";  // third constructor arg in JAR usage
		final String finalUrl = baseUrl + "/" + apiMethod + "/" + className;

		// get headers from the jar's private static getHeader() using reflection
		HttpHeaders headersFromJar;
		try {
			Class<?> dmsRestClientClazz = Class.forName("com.np.dms.client.DmsRestClient");
			Method getHeaderMethod = dmsRestClientClazz.getDeclaredMethod("getHeader");
			getHeaderMethod.setAccessible(true);
			Object headerObj = getHeaderMethod.invoke(null); // static method, target null
			if (!(headerObj instanceof HttpHeaders)) {
				throw new IllegalStateException("DmsRestClient.getHeader() did not return HttpHeaders");
			}
			headersFromJar = (HttpHeaders) headerObj;
		} catch (Exception e) {
			logger.error("Failed to obtain headers from DmsRestClient.getHeader(): {}", e.getMessage(), e);
			throw new RuntimeException("Cannot build headers for DMS call", e);
		}

		// compute signature exactly like the jar does and add to headers
		String timestamp = headersFromJar.getFirst("timestamp");
		// NOTE: second parameter must match serviceParameter.getParameter() used in the jar.
		// Earlier usage showed "common-app" passed; keep consistent.
		String signature = com.np.dms.utils.SignatureUtil.getSignature(apiMethod, apiMethod, className, timestamp,
				requestDto);
		headersFromJar.add("signature", signature);

		// Build a WebClient instance (we set headers per-request below to ensure fresh timestamp)
		WebClient client = WebClient.builder().build();

		try {
			return client.post()
					.uri(finalUrl) // absolute URL
					.headers(h -> h.addAll(headersFromJar))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(requestDto)
					.retrieve()
					.onStatus(status -> !status.is2xxSuccessful(),
							resp -> resp.bodyToMono(String.class)
									.flatMap(body -> Mono.<Throwable>error(new RuntimeException(
											"Upstream error " + resp.statusCode() + " : " + body))))
					.bodyToMono(DmsDocumentDto.class)
					.block();
		} catch (WebClientResponseException ex) {
			logger.error("Upstream returned status {} body: {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
			throw ex;
		} catch (Exception ex) {
			logger.error("Error calling DMS: {}", ex.getMessage(), ex);
			throw new RuntimeException(ex);
		}
	}
}
