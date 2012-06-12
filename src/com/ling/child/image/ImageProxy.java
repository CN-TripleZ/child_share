package com.ling.child.image;

import java.util.List;

import com.ling.child.image.model.Image;
import com.ling.framework.cache.CacheProxy;

public class ImageProxy extends CacheProxy<Image> implements IImageManager {
	public ImageProxy() {
		super(IImageManager.IMAGE_CACHE);
	}

	public void uploadImage(Image image) throws Exception {
		// TODO Auto-generated method stub

	}

	public Image combine(Image[] images) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Image combine(String[] images) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteImage(Image image) throws Exception {
		// TODO Auto-generated method stub

	}

	public List<Image> getImagesByUserId(String userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Image> getImagesByUserId(String userId, int start, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Image> getHotImages() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
