package chiya.web.request;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import chiya.core.base.string.StringUtil;

import java.io.IOException;

/**
 * 针对JSON的备份ServletRequest
 */
public class RequestFilter {

	/** JSON类型 */
	private static final String TYPE = "application/json";

	public static void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		// 只有是JSON的时候采取复制
		if (StringUtil.eqString(TYPE, servletRequest.getContentType())) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			ServletRequest requestWrapper = new BodyReaderWrapper(httpServletRequest);
			filterChain.doFilter(requestWrapper, servletResponse);
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

}