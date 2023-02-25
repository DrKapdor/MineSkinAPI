package me.drkapdor.mineskinsapi.api.skin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class GeneratedSkin  {

    private final String value;
    private final String signature;

    public boolean isEmpty() {
        return value.isEmpty() && signature.isEmpty();
    }

    @Override
    public String toString() {
        return "GeneratedSkin{value=\""+ value +"\", signature=\"" + signature + "\"}";
    }

}
