package servlet.view.impl;

import servlet.Model;
import servlet.view.View;

public class RedirectView implements View {

	@Override
	public byte[] render(String viewPath, Model model) {
		return new byte[0];
	}
}
