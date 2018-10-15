package giselle.dodge.render.entity;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import giselle.dodge.entity.Entity;
import giselle.dodge.entity.EntityBullet;
import giselle.dodge.entity.EntityPlayer;

public abstract class RenderEntity<E extends Entity>
{
	private static final HashMap<Class<? extends Entity>, RenderEntity<? extends Entity>> renderers;

	static
	{
		renderers = new HashMap<Class<? extends Entity>, RenderEntity<? extends Entity>>();
		registerRenderer(EntityPlayer.class, new RenderEntityPlayer());
		registerRenderer(EntityBullet.class, new RenderEntityBullet());
	}

	private static <E extends Entity> void registerRenderer(Class<E> clazz, RenderEntity<E> renderer)
	{
		renderers.put(clazz, renderer);
	}

	public static <E extends Entity> RenderEntity<E> getRenderer(Class<E> clazz)
	{
		@SuppressWarnings("unchecked")
		RenderEntity<E> renderEntity = (RenderEntity<E>) renderers.get(clazz);
		return renderEntity;
	}

	public static <E extends Entity> void renderEntity(SpriteBatch batch, E entity)
	{
		@SuppressWarnings("unchecked")
		Class<E> class1 = (Class<E>) entity.getClass();
		getRenderer(class1).render(batch, entity);
	}

	public RenderEntity()
	{

	}

	public abstract void render(SpriteBatch batch, E entity);
}
