package giselle.dodge.render;

import com.badlogic.gdx.graphics.Color;

public class FontStyleBuilder
{
	private Color color = null;

	private Color borderColor = null;
	private float borderWidth = 0.0F;

	private int size = 0;

	public FontStyleBuilder()
	{
		this.setColor(Color.BLACK);

		this.setBorderColor(Color.BLACK);
		this.setSize(0);
	}

	public FontStyle build()
	{
		return new FontStyle(this);
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public FontStyleBuilder setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
		return this;
	}

	public float getBorderWidth()
	{
		return borderWidth;
	}

	public FontStyleBuilder setBorderWidth(float borderWidth)
	{
		this.borderWidth = borderWidth;
		return this;
	}

	public int getSize()
	{
		return size;
	}

	public FontStyleBuilder setSize(int size)
	{
		this.size = Math.max(10, size);
		return this;
	}

}
