package giselle.dodge.render;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class TextureManager
{
	private HashMap<String, Texture> map = null;

	public TextureManager()
	{
		this.map = new HashMap<String, Texture>();
	}

	public Texture get(String name)
	{
		String key = name.toLowerCase();
		Texture texture = this.map.get(key);

		if (texture == null)
		{
			texture = new Texture(Gdx.files.internal(name));
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			this.map.put(key, texture);
		}

		return texture;
	}

	public NinePatch createButtonNinePatch()
	{
		Texture texture = this.get("button.png");
		NinePatch ninePatch = new NinePatch(texture, 133, 133, 133, 133);

		return ninePatch;
	}

}
