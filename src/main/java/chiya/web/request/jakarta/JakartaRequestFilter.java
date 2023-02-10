package chiya.web.request.jakarta;

import java.io.IOException;


import chiya.core.base.string.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JakartaRequestFilter {
	/** JSON类型 */
	private static final String TYPE = "application/json";

	public static void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		// 只有是JSON的时候采取复制
		if (StringUtil.findString(servletRequest.getContentType(), TYPE) != -1) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			ServletRequest requestWrapper = new JakartaBodyReaderWrapper(httpServletRequest);
			filterChain.doFilter(requestWrapper, servletResponse);
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}
}
