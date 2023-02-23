package me.drkapdor.mineskinsapi.api.skin;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public class SkinRegistry {

    private final Cache<String, GeneratedSkin> cache;

    public SkinRegistry() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .expireAfterAccess(15, TimeUnit.MINUTES)
                .maximumSize(60)
                .build();
    }
}
