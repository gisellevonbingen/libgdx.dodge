package giselle.dodge.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import giselle.dodge.gui.game.World;
import giselle.dodge.input.Controller;

public class EntityPlayer extends Entity
{
	public EntityPlayer(World world)
	{
		super(world);

		this.setSize(new Vector2(12.5F, 12.5F));
	}

	public void accept(Controller controller)
	{
		Vector2 movement = controller.getMovement(256.0F * Gdx.graphics.getDeltaTime());

		if (controller.isSlowing() == false)
		{
			movement.scl(1.0F);
		}
		else
		{
			movement.scl(0.5F);
		}

		Vector2 worldSize = this.getWorld().getSize();
		Vector2 playerPosition = this.getPosition();
		Vector2 playerSize = this.getSize();
		playerPosition.add(movement);
		playerPosition.x = Math.max(0, Math.min(worldSize.x - playerSize.x, playerPosition.x));
		playerPosition.y = Math.max(0, Math.min(worldSize.y - playerSize.y, playerPosition.y));
		this.setPosition(playerPosition);
	}

}
