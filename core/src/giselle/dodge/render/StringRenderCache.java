package giselle.dodge.render;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import giselle.dodge.Dodge;
import giselle.dodge.gui.Alignment;

public class StringRenderCache
{
	private BitmapFont font = null;
	private String text = null;
	private Color color = null;
	private float scaleX = 1.0F;
	private float scaleY = 1.0F;

	private List<StringRenderCache> lines = null;
	private Vector2 size = null;

	private boolean dirty = false;

	public StringRenderCache()
	{
		this.font = null;
		this.text = null;
		this.color = Color.BLACK;
		this.scaleX = 1.0F;
		this.scaleY = 1.0F;

		this.lines = new ArrayList<StringRenderCache>();
		this.size = new Vector2(0.0F, 0.0F);

		this.dirty = true;
	}

	public StringRenderCache(StringRenderCache parent)
	{
		this();

		this.font = parent.font;
		this.text = parent.text;
		this.color = parent.color;
		this.scaleX = parent.scaleX;
		this.scaleY = parent.scaleY;

		for (StringRenderCache c : parent.lines)
		{
			this.lines.add(new StringRenderCache(c));
		}

		this.size = new Vector2(parent.size);

		this.dirty = parent.dirty;
	}

	public StringRenderCache clone()
	{
		return new StringRenderCache(this);
	}

	public StringRenderCache cloneStyle()
	{
		StringRenderCache value = new StringRenderCache();
		value.font = this.font;
		value.text = this.text;
		value.color = this.color;
		value.scaleX = this.scaleX;
		value.scaleY = this.scaleY;

		return value;
	}

	public BitmapFont getFont()
	{
		return this.font;
	}

	public void setFont(int fontSize)
	{
		FontManager fontManager = Dodge.instance().getFontManager();
		this.setFont(fontManager.getFont(fontSize));
	}

	public void setFont(FontStyle fontStyle)
	{
		FontManager fontManager = Dodge.instance().getFontManager();
		this.setFont(fontManager.getFont(fontStyle));
	}

	public void setFont(BitmapFont font)
	{
		this.font = font;
		this.dirty = true;
	}

	public String getText()
	{
		return this.text;
	}

	public void setText(String text)
	{
		this.text = text;
		this.dirty = true;
	}

	public Vector2 getSize()
	{
		return new Vector2(this.size);
	}

	public Vector2 getScaledSize()
	{
		Vector2 size = new Vector2(this.size);
		size.scl(this.scaleX, this.scaleY);
		return size;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
		this.dirty = true;
	}

	public float getScaleX()
	{
		return scaleX;
	}

	public void setScaleX(float scaleX)
	{
		this.scaleX = Math.max(0.1F, scaleX);
		this.dirty = true;
	}

	public float getScaleY()
	{
		return scaleY;
	}

	public void setScaleY(float scaleY)
	{
		this.scaleY = Math.max(0.1F, scaleY);
		this.dirty = true;
	}

	public void setScale(float scale)
	{
		this.setScaleX(scale);
		this.setScaleY(scale);
		this.dirty = true;
	}

	public void updateDirty()
	{
		if (this.dirty == false)
		{
			return;
		}

		String[] splited = this.text.replace("\r\n", "\n").split("\n");
		float width = 0.0F;
		float height = 0.0F;

		if (splited.length == 1)
		{
			char[] chars = this.text.toCharArray();
			BitmapFontData data = this.font.getData();
			data.setScale(this.scaleX, this.scaleY);

			Glyph lastGlyph = null;

			for (int i = 0; i < chars.length; i++)
			{
				char ch = chars[i];
				Glyph glyph = data.getGlyph(ch);

				width = width += glyph.xadvance;

				if (lastGlyph != null)
				{
					width += lastGlyph.getKerning(ch);
				}

				lastGlyph = glyph;
			}

			width = width * this.scaleX;
			height = (data.capHeight + data.ascent) * this.scaleY;
		}
		else
		{
			for (int i = 0; i < splited.length; i++)
			{
				StringRenderCache line = this.cloneStyle();
				line.setText(splited[i]);
				line.updateDirty();

				Vector2 lineSize = line.getSize();
				this.lines.add(line);

				width = Math.max(width, lineSize.x);
				height += lineSize.y;

				if (i > 0)
				{
					height += this.font.getAscent() - this.font.getDescent();
				}

			}

		}

		this.size = new Vector2(width, height);

		this.dirty = false;
	}

	public void renderBounds(SpriteBatch batch, float x, float y, float width, float height, Alignment alignment)
	{
		this.updateDirty();

		BitmapFontData data = this.font.getData();
		data.setScale(this.scaleX, this.scaleY);

		Vector2 size = this.getSize();
		Vector2 renderOffset = alignment.align(new Vector2(width, height), size, true);

		if (this.lines.size() == 0)
		{
			float yOffset = (this.font.getAscent() + this.font.getDescent()) / 2;

			this.font.setColor(this.color);
			this.font.draw(batch, this.text, x + renderOffset.x, y + renderOffset.y + size.y + yOffset);
		}
		else
		{
			float yOffset = this.font.getAscent() - this.font.getDescent();
			y += renderOffset.y;
			y -= this.font.getAscent() + this.font.getDescent();

			for (int i = this.lines.size() - 1; i > -1; i--)
			{
				StringRenderCache line = this.lines.get(i);
				Vector2 lineSize = line.getSize();
				line.renderBounds(batch, x, y, width, lineSize.y, alignment);
				y += lineSize.y + yOffset;
			}

		}

	}

	public void renderBounds(SpriteBatch batch, Rectangle bounds, Alignment alignment)
	{
		this.renderBounds(batch, bounds.x, bounds.y, bounds.width, bounds.height, alignment);
	}

}
