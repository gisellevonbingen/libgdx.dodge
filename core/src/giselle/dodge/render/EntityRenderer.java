package giselle.dodge.render;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import giselle.dodge.Dodge;
import giselle.dodge.entity.Entity;
import giselle.dodge.gui.game.World;
import giselle.dodge.render.entity.RenderEntity;

public class EntityRenderer
{
	private final World world;

	public EntityRenderer(World world)
	{
		this.world = world;
	}

	public void render()
	{
		Dodge game = Dodge.instance();
		RenderEngine renderEngine = game.getRenderEngine();
		SpriteBatch batch = renderEngine.getSpriteBatch();

		List<Entity> entities = this.world.getEntities();

		for (int i = 0; i < entities.size(); i++)
		{
			Entity entity = entities.get(i);
			RenderEntity.renderEntity(batch, entity);
		}

	}

}
