package giselle.dodge.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import giselle.dodge.gui.game.World;

public abstract class Entity
{
	private final World world;

	private Vector2 position = null;
	private Vector2 size = null;

	public Entity(World world)
	{
		this.world = world;

		this.position = new Vector2();
		this.size = new Vector2();
	}

	public void update()
	{

	}

	public World getWorld()
	{
		return this.world;
	}

	public Vector2 getPosition()
	{
		return new Vector2(this.position);
	}

	public Vector2 setPosition(Vector2 position)
	{
		return this.position.set(position);
	}

	public Vector2 getSize()
	{
		return new Vector2(this.size);
	}

	public Vector2 setSize(Vector2 size)
	{
		return this.size.set(size);
	}

	public Rectangle getBounds()
	{
		Rectangle bounds = new Rectangle();
		bounds.setPosition(this.getPosition());
		Vector2 size = this.getSize();
		bounds.setSize(size.x, size.y);

		return bounds;
	}

}
