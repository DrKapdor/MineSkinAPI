package me.drkapdor.mineskinsapi.paper.event;

import lombok.Getter;
import lombok.Setter;
import me.drkapdor.mineskinsapi.api.skin.GeneratedSkin;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class SkinApplicationPrepareEvent extends SkinEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public SkinApplicationPrepareEvent(GeneratedSkin skin) {
        super(skin);
    }

    @Getter
    @Setter
    private boolean cancelled;

    @Override
    public String getEventName() {
        return "SkinAppliedEvent";
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
