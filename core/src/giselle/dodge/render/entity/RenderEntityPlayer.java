package giselle.dodge.render.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import giselle.dodge.Dodge;
import giselle.dodge.entity.EntityPlayer;
import giselle.dodge.render.TextureManager;

public class RenderEntityPlayer extends RenderEntity<EntityPlayer>
{
	public RenderEntityPlayer()
	{

	}

	@Override
	public void render(SpriteBatch batch, EntityPlayer entity)
	{
		TextureManager textureManager = Dodge.instance().getTextureManager();
		Texture texture = textureManager.get("badlogic.jpg");

		Vector2 position = entity.getPosition();
		Vector2 size = entity.getSize();
		batch.setColor(Color.WHITE);
		batch.draw(texture, position.x, position.y, size.x, size.y);
	}

}
