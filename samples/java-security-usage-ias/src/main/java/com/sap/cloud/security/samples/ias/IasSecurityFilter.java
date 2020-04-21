package com.sap.cloud.security.samples.ias;

import com.sap.cloud.security.servlet.IasTokenAuthenticator;
import com.sap.cloud.security.servlet.TokenAuthenticationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*") // filter for any endpoint
public class IasSecurityFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(IasSecurityFilter.class);
	private final IasTokenAuthenticator iasTokenAuthenticator;

	public IasSecurityFilter() {
		iasTokenAuthenticator = new IasTokenAuthenticator();
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		TokenAuthenticationResult authenticationResult = iasTokenAuthenticator.validateRequest(request, response);
		if (authenticationResult.isAuthenticated()) {
			LOGGER.debug("AUTHENTICATED");
			chain.doFilter(request, response) ;
		} else {
			LOGGER.debug("UNAUTHENTICATED");
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	public void destroy() {
	}
}
