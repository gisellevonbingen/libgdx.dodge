package giselle.dodge.gui.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import giselle.dodge.entity.Entity;

public class World
{
	private Vector2 size = new Vector2();
	private List<Entity> entities = null;

	public World()
	{
		this.entities = new ArrayList<Entity>();
	}

	public void update()
	{
		for (int i = 0; i < this.entities.size(); i++)
		{
			Entity entity = this.entities.get(i);
			entity.update();
		}

	}

	public void setSize(Vector2 size)
	{
		this.size.set(size);
	}

	public Vector2 getSize()
	{
		return new Vector2(this.size);
	}

	public List<Entity> getEntities()
	{
		return this.entities;
	}

}
