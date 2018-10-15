package giselle.dodge.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import giselle.dodge.Dodge;
import giselle.dodge.render.RenderEngine;
import giselle.dodge.render.StringRenderCache;

public class TextRenderableControl extends Control
{
	private String text = null;
	private Alignment textAlignment = null;

	private BitmapFont font = null;
	private Color textColor = null;

	public TextRenderableControl()
	{
		this.text = "";
		this.textAlignment = Alignment.middleCenter;

		this.font = Dodge.instance().getFontManager().getFont(80);
		this.textColor = Color.BLACK;
	}

	@Override
	protected void renderMiddle()
	{
		super.renderMiddle();

		Dodge instance = Dodge.instance();
		RenderEngine renderEngine = instance.getRenderEngine();
		SpriteBatch spriteBatch = renderEngine.getSpriteBatch();

		Rectangle textRenderBounds = this.getTextRenderBounds();
		Rectangle renderBounds = this.getScreenBounds();
		textRenderBounds.x += renderBounds.x;
		textRenderBounds.y += renderBounds.y;

		StringRenderCache stringRenderCache = new StringRenderCache();
		stringRenderCache.setFont(this.font);
		stringRenderCache.setText(this.text);
		stringRenderCache.setColor(this.getTextRenderColor());
		stringRenderCache.renderBounds(spriteBatch, textRenderBounds, this.textAlignment);
	}

	public Color getTextRenderColor()
	{
		return this.textColor;
	}

	public Rectangle getTextRenderBounds()
	{
		Rectangle renderBounds = this.getScreenBounds();
		return new Rectangle(0.0F, 0.0F, renderBounds.width, renderBounds.height);
	}

	public String getText()
	{
		return this.text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Alignment getTextAlignment()
	{
		return textAlignment;
	}

	public void setTextAlignment(Alignment textAlignment)
	{
		this.textAlignment = textAlignment;
	}

	public BitmapFont getFont()
	{
		return font;
	}

	public void setFont(BitmapFont font)
	{
		this.font = font;
	}

	public Color getTextColor()
	{
		return this.textColor;
	}

	public void setTextColor(Color textColor)
	{
		this.textColor = textColor;
	}

}
