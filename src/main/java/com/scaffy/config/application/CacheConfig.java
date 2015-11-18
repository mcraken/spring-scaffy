package com.scaffy.config.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

import com.google.common.cache.CacheBuilder;

@EnableCaching
public class CacheConfig {
	
	@Value("${caches.names}")
	private String caches;
	
	@Value("${caches.eviction}")
	private Integer evictionTime;
	
	/**
	 * <p>cacheManager.</p>
	 *
	 * @return CacheManager. The cache names are read from
	 * web.properties. Each cache is configured with 60 minutes eviction time.
	 */
	@Bean
	public CacheManager cacheManager() {
		
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		
		String[] cacheNames = caches.split(",");
		
		List<GuavaCache> caches = new ArrayList<GuavaCache>();
		
		for(String cacheName : cacheNames)
			caches.add(
					new GuavaCache(cacheName, CacheBuilder.newBuilder()
				            .expireAfterWrite(evictionTime, TimeUnit.MINUTES)
				            .build()
				            )
					);
		
		cacheManager.setCaches(caches);
		
		return cacheManager;
	}
}
