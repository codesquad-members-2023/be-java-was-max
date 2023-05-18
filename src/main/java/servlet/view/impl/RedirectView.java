package servlet.view.impl;

import servlet.view.View;

public class RedirectView implements View {

	@Override
	public byte[] render(String viewPath) {
		return new byte[0];
	}
}
