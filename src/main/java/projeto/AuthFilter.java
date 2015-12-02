package projeto;

/* Adaptado de: http://www.journaldev.com/7252/jsf-authentication-login-logout-database-example */

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "*.xhtml" })
public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();
			if (reqURI.indexOf("/login.xhtml") >= 0 || reqURI.indexOf("/register.xhtml") >= 0
					|| reqURI.indexOf("/top_bar.xhtml") >= 0 || (ses != null && ses.getAttribute("usuario") != null)
					|| reqURI.contains("javax.faces.resource")) {
				resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
																						// 1.1.
				resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				resp.setDateHeader("Expires", 0); // Proxies.
				chain.doFilter(request, response);
			} else {
				resp.sendRedirect(reqt.getContextPath() + "/public/login.xhtml");
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void destroy() {

	}
}
