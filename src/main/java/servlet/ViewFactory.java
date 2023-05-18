package servlet;

import servlet.view.View;
import servlet.view.impl.OkView;
import servlet.view.impl.RedirectView;
import servlet.viewResolver.ViewResolver;
import servlet.viewResolver.impl.OkViewResolver;
import servlet.viewResolver.impl.RedirectViewResolver;

public class ViewFactory {

	private static final String REDIRECT = "redirect";

	public View view(String viewName) {
		if (viewName.startsWith(REDIRECT)) {
			return new RedirectView();
		}
		return new OkView();
	}

	public ViewResolver viewResolver(String viewName) {
		if (viewName.startsWith(REDIRECT)) {
			return new RedirectViewResolver();
		}
		return new OkViewResolver();
	}
}
