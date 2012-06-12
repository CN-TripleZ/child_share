package com.ling.child.image;

import java.util.List;

import com.ling.child.image.model.Image;

public interface IImageManager {
	public static String IMAGE_CACHE = "image_cache";
	
	public void uploadImage(Image image) throws Exception;
	
	public Image combine(Image[] images) throws Exception;
	
	public Image combine(String[] images) throws Exception;
	
	public void deleteImage(Image image) throws Exception;
	
	public List<Image> getImagesByUserId(String userId) throws Exception;
	
	public List<Image> getImagesByUserId(String userId, int start, int limit) throws Exception;
	
	public List<Image> getHotImages() throws Exception;
}
