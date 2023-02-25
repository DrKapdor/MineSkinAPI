package me.drkapdor.mineskinsapi.paper.event;

import lombok.Getter;
import lombok.Setter;
import me.drkapdor.mineskinsapi.api.skin.GeneratedSkin;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class SkinApplicationPrepareEvent extends SkinEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Getter
    private final Player requester;

    @Getter
    @Setter
    private boolean cancelled;

    public SkinApplicationPrepareEvent(Player requester, GeneratedSkin skin) {
        super(skin);
        this.requester = requester;
    }


    @Override
    public String getEventName() {
        return "SkinAppliedEvent";
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
