package giselle.dodge.render;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;

public class FontManager
{
	private HashMap<FontStyle, BitmapFont> maps = null;
	private FreeTypeFontGenerator fontGenerator = null;

	public FontManager()
	{
		this.maps = new HashMap<FontStyle, BitmapFont>();
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("NanumGothic.ttf"));
	}

	private FreeTypeFontParameter createParameter(FontStyle style)
	{
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.minFilter = TextureFilter.Linear;
		parameter.magFilter = TextureFilter.Linear;
		parameter.size = style.getSize();
		parameter.incremental = true;
		parameter.genMipMaps = true;
		parameter.hinting = Hinting.Slight;
		parameter.borderColor = style.getBorderColor();
		parameter.borderWidth = style.getBorderWidth();
		parameter.color = Color.WHITE;

		return parameter;
	}

	public BitmapFont getFont(int size)
	{
		FontStyleBuilder builder = new FontStyleBuilder();
		builder.setSize(size);

		return this.getFont(builder);
	}

	public BitmapFont getFont(FontStyleBuilder builder)
	{
		FontStyle style = builder.build();
		return this.getFont(style);
	}

	public BitmapFont getFont(FontStyle style)
	{
		BitmapFont font = this.maps.get(style);

		if (font == null)
		{
			FreeTypeFontParameter parameter = this.createParameter(style);
			font = this.fontGenerator.generateFont(parameter);
			font.setColor(Color.WHITE);
			this.maps.put(style, font);
		}

		return font;
	}

	public void dispose()
	{
		this.fontGenerator.dispose();

		for (Entry<FontStyle, BitmapFont> entry : this.maps.entrySet())
		{
			BitmapFont font = entry.getValue();
			font.dispose();
		}

	}

}
