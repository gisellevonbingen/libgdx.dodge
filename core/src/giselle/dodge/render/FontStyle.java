package giselle.dodge.render;

import java.util.Objects;

import com.badlogic.gdx.graphics.Color;

public class FontStyle
{
	private Color borderColor = null;
	private float borderWidth = 0.0F;

	private int size = 0;

	public FontStyle(FontStyleBuilder builder)
	{
		this.borderColor = builder.getBorderColor();
		this.borderWidth = builder.getBorderWidth();

		this.size = builder.getSize();
	}

	@Override
	public int hashCode()
	{
		int hash = 17;

		hash = hash * 32 + (this.borderColor != null ? this.borderColor.hashCode() : 0);
		hash = hash * 32 + Float.floatToIntBits(this.borderWidth);

		hash = hash * 32 + this.size;

		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || obj.getClass().equals(this.getClass()) == false)
		{
			return false;
		}

		FontStyle other = (FontStyle) obj;

		if (Objects.deepEquals(this.borderColor, other.borderColor) == false)
		{
			return false;
		}

		if (this.borderWidth != other.borderWidth)
		{
			return false;
		}

		if (this.size != other.size)
		{
			return false;
		}

		return true;
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public float getBorderWidth()
	{
		return borderWidth;
	}

	public int getSize()
	{
		return size;
	}

}
