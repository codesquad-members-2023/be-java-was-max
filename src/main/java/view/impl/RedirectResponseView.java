package view.impl;

import view.View;

public class RedirectResponseView extends View {

	public RedirectResponseView(String view) {
		setBody(render(view));
	}

	@Override
	public byte[] render(String view) {
		return new byte[0];
	}
}
