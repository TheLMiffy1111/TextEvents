package thelm.textevents.events;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EntityDisplayNameEvent extends Event {

	private String displayName;
	private final Entity entity;

	public EntityDisplayNameEvent(String displayName, Entity entity) {
		this.displayName = displayName;
		this.entity = entity;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Entity getEntity() {
		return entity;
	}
}
