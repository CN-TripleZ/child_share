package com.ling.framework.cache;

import java.util.logging.Logger;

/**
 * 缓存代理抽象类
 * @version 1.0
 * @param <T>
 */
public abstract class CacheProxy<T> {
	protected final Logger logger = Logger.getLogger(getClass().getName());

	protected ICache<T> cache;

	public CacheProxy(String cacheName) {
		cache = CacheFactory.getCache(cacheName);
	}
}