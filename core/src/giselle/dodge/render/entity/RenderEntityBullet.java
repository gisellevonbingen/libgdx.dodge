package giselle.dodge.render.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import giselle.dodge.Dodge;
import giselle.dodge.entity.EntityBullet;
import giselle.dodge.render.TextureManager;

public class RenderEntityBullet extends RenderEntity<EntityBullet>
{
	public RenderEntityBullet()
	{

	}

	@Override
	public void render(SpriteBatch batch, EntityBullet entity)
	{
		TextureManager textureManager = Dodge.instance().getTextureManager();
		Texture texture = textureManager.get("bullet.png");

		Vector2 position = entity.getPosition();
		Vector2 size = entity.getSize();
		batch.setColor(entity.getColor());
		batch.draw(texture, position.x, position.y, size.x, size.y);
	}

}
