package me.drkapdor.mineskinsapi.paper.event;

import lombok.Getter;
import me.drkapdor.mineskinsapi.api.skin.GeneratedSkin;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkinEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Getter private final GeneratedSkin skin;

    public SkinEvent(GeneratedSkin skin) {
        this.skin = skin;
    }

    @Override
    public String getEventName() {
        return "SkinEvent";
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
