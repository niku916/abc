package com.dms.opentelemetry.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;

@Configuration
public class OpenTelemetryConfiguration {

	OtlpHttpSpanExporter otlpGrpcSpanExporter(@Value("${tracing.url}") String url) {
		return OtlpHttpSpanExporter.builder().setEndpoint(url).build();

	}

}
