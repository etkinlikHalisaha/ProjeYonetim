package tr.org.tnb.egitim.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class OrnekFilter implements Filter {
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("OrnekFilter çalışıyor");
	}

	@Override
	public void destroy() {
		System.out.println("OrnekFilter durduruluyor");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("Request OrnekFilter'dan gecti");
		
		chain.doFilter(request, response);
		
		System.out.println("Response dönüyor");
	}


}
