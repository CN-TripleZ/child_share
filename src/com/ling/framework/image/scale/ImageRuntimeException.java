package com.ling.framework.image.scale;

public class ImageRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -7411563451122830740L;

	public ImageRuntimeException(String path, String proesstype) {
		super("对图片" + path + "进行" + proesstype + "出错");
	}
}