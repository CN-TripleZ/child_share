package com.ling.child.image;

import java.util.List;

import com.ling.child.image.model.Image;
import com.ling.framework.cache.CacheProxy;

public class ImageManager extends CacheProxy<Image> implements IImageManager {
	

	public void uploadImage(Image image) throws Exception {
		
	}

	public Image combine(Image[] images) throws Exception {
		return null;
	}

	public Image combine(String[] images) throws Exception {
		return null;
	}

	public void deleteImage(Image image) throws Exception {
		
	}

	public List<Image> getImagesByUserId(String userId) throws Exception {
		return null;
	}

	public List<Image> getImagesByUserId(String userId, int start, int limit)
			throws Exception {
		return null;
	}

	public List<Image> getHotImages() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
