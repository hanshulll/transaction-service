package com.paymentledger.shared.correlation;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Reads/generates a correlation id per request and puts it on the MDC so every log
 * line from here on carries it. Also stamps it onto the response headers.
 */
@Component
public class CorrelationIdFilter extends HttpFilter {

	public static final String HEADER_NAME = "X-Correlation-Id";
	public static final String MDC_KEY = "correlationId";

	@Override
	protected void doFilter(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		throw new UnsupportedOperationException("TODO: read/generate correlation id, put in MDC, propagate on response, clear after chain.doFilter");
	}
}
