///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package filters;
//
//import java.io.IOException;
//import java.util.Random;
//
//import javax.faces.application.ResourceHandler;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import nic.org.apache.log4j.Logger;
//
//@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml", "/vahandocumentsystem/*" })
//public class AuthFilter implements Filter {
//
//	private static final Logger LOGGER = Logger.getLogger(AuthFilter.class);
//
//	public AuthFilter() {
//	}
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//
//		// check whether session variable is set
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		HttpSession ses = req.getSession(false);
//		String reqURI = req.getRequestURI();
//
//		Exception exception = null;
//
//		try {
//
//			// disable the browser cache.
//			if (!reqURI.startsWith(req.getContextPath() + "/vahan" + ResourceHandler.RESOURCE_IDENTIFIER)) {
//				res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
//				// 1.1.
//				res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//				res.setDateHeader("Expires", 0); // Proxies.
//				res.setHeader("X-Frame-Options", "SAMEORIGIN");
//				res.setHeader("X-Powered-By", "unset");
//				res.setHeader("Server", "unset");
//				res.setHeader("X-XSS-Protection", "1; mode=block");
//				// Disabling browsers to perform risky mime sniffing
//				res.setHeader("X-Content-Type-Options", "nosniff");
//				res.setHeader("X-Frame-Options", " sameorigin");
//				// res.setHeader("Content-Security-Policy ", "default-src
//				// 'self';");
//			}
//
//			Random random = new Random();
//			int refValue = random.nextInt(1000);
//			Cookie cookie = new Cookie("JSESSIONID", req.getSession().getId() + refValue);
//			cookie.setHttpOnly(true);
//			cookie.setSecure(true);
//
//			if (req.getHeader("Referer") == null) {
//				res.sendRedirect(req.getContextPath() + "/dms/ui/uploadDoc/test.xhtml");
//				return;
//			}
//			chain.doFilter(request, response);
//
//		} catch (IOException e) {
//			exception = e;
//
//		} catch (Exception ex) {
//			exception = ex;
//
//		}
//
//		if (exception != null) {
//			if (!exception.getClass().getName().equals("java.lang.IllegalStateException")) {
//
//				LOGGER.error("authFilter..Exception..." + exception);
//			}
//			if (ses == null && reqURI.indexOf("/outside-error.xhtml") < 0) {
//				res.sendRedirect(req.getContextPath() + "/vahan/ui/session/outside-error.xhtml");
//			}
//			if (ses != null && ses.getAttribute("user_id") == null && reqURI.indexOf("/outside-error.xhtml") < 0) {
//				res.sendRedirect(req.getContextPath() + "/vahan/ui/session/outside-error.xhtml");
//			}
//
//		}
//	} // end of doFilter
//
//	@Override
//	public void destroy() {
//	}
//}// end of AuthFilter
