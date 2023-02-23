package me.drkapdor.mineskinsapi.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.drkapdor.mineskinsapi.api.skin.SkinRegistry;

@Getter
@AllArgsConstructor
public class MineSkinApi {

    private MineSkinClient client;
    private SkinRegistry registry;

}
