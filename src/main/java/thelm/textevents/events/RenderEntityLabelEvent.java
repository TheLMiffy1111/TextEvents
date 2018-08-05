package thelm.textevents.events;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderEntityLabelEvent extends Event {

	private String name;
	private final Entity entity;

	public RenderEntityLabelEvent(String name, Entity entity) {
		this.name = name;
		this.entity = entity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Entity getEntity() {
		return this.entity;
	}
}
