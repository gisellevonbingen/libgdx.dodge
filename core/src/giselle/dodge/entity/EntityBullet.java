package giselle.dodge.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import giselle.dodge.gui.game.World;

public class EntityBullet extends Entity
{
	private Color color = null;

	private Vector2 velocity = null;

	public EntityBullet(World world)
	{
		super(world);

		this.setSize(new Vector2(12.5F, 12.5F));

		this.color = Color.WHITE;
		this.velocity = new Vector2();
	}

	@Override
	public void update()
	{
		super.update();

		Vector2 vel = new Vector2(this.velocity).scl(Gdx.graphics.getDeltaTime());

		Vector2 add = this.getPosition().add(vel);
		this.setPosition(add);
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public Vector2 getVelocity()
	{
		return this.velocity;
	}

	public void setVelocity(Vector2 velocity)
	{
		this.velocity = velocity;
	}

}
