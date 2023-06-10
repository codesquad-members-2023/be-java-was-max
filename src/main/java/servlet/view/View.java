package servlet.view;

import servlet.Model;

public interface View {

	/**
	 * viewPath를 통해 해당 경로의 파일을 읽어 온다.
	 * @param viewPath
	 * @return byte[]
	 */
	byte[] render(String viewPath, Model model);
}
