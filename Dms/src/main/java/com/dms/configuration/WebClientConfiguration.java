package com.dms.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

@Configuration
public class WebClientConfiguration {
	private static final Logger logger = LogManager.getLogger(WebClientConfiguration.class);
	@Value("${dms.url.staging}")
	private String dmsStagingServicesUrl;
	@Value("${dms.url.prod}")
	private String dmsProdServicesUrl;

	@Bean
	public WebClientFactory webClientFactory( Tracer tracer) {
		return new WebClientFactory(tracer);
	}

	public class WebClientFactory {

		private final Tracer tracer;

		public WebClientFactory(Tracer tracer) {
			this.tracer = tracer;
		}

		// Factory method to create WebClient for service
		public WebClient createWebClientForDmsProdServicesUrl() {
			return createWebClient(dmsProdServicesUrl);
		}
		
		public WebClient createWebClientForDmsStagingServicesUrl() {
			return createWebClient(dmsStagingServicesUrl);
		}

		// Helper method to configure a WebClient
		private WebClient createWebClient(String baseUrl) {
			return WebClient.builder().baseUrl(baseUrl).defaultHeader("Authorization", "Bearer ")
					.defaultHeader("Accept", "application/json").filter(tracingFilter()) // Add tracing filter
					.build();
		}

		private ExchangeFilterFunction tracingFilter() {
			return (request, next) -> {
				// Start a new span
				Span span = tracer.nextSpan().name(request.method().name() + " " + request.url()).start();
				return next.exchange(request).doOnEach(signal -> {
					if (signal.isOnComplete() || signal.hasError()) {
						span.end(); // End the span when the request completes or fails
					}
				}).contextWrite(context -> context.put(Span.class, span)); // Attach span to context
			};
		}
	}

}
