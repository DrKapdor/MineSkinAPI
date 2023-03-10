package me.drkapdor.mineskinsapi.paper.event;

import lombok.Getter;
import lombok.Setter;
import me.drkapdor.mineskinsapi.api.skin.GeneratedSkin;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkinReceivedEvent extends SkinEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Getter private final Player requester;
    @Getter private final boolean success;

    public SkinReceivedEvent(Player requester, GeneratedSkin skin, boolean success) {
        super(skin);
        this.requester = requester;
        this.success = success;
    }

    @Override
    public String getEventName() {
        return "SkinReceivedEvent";
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
