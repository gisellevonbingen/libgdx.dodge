package giselle.dodge.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import giselle.dodge.Dodge;
import giselle.dodge.render.TextureManager;

public class Button extends TextRenderableControl
{
	private Drawable upDrawable = null;
	private Drawable downDrawable = null;

	private Color downTextColor = null;

	public Button()
	{
		this.setUpDrawable(this.getDefaultStyleDrable(Color.BLACK));
		this.setDownDrawable(this.getDefaultStyleDrable(Color.RED));

		this.setDownTextColor(Color.RED);

		this.setSize(64, 64);
	}

	public NinePatchDrawable getDefaultStyleDrable(Color color)
	{
		TextureManager textureManager = Dodge.instance().getTextureManager();
		Texture texture = textureManager.get("button.png");
		NinePatch ninePatch = new NinePatch(texture, 32, 32, 32, 32);

		return new NinePatchDrawable(ninePatch).tint(color);
	}

	@Override
	protected void renderMiddle()
	{
		super.renderMiddle();

		Rectangle renderBounds = this.getScreenBounds();

		Drawable downDrawable = this.downDrawable;
		Drawable drawable = this.upDrawable;

		if (this.getTouchHandler().getDownedPoint() != null && downDrawable != null)
		{
			drawable = downDrawable;
		}

		if (drawable != null)
		{
			Dodge instance = Dodge.instance();
			SpriteBatch batch = instance.getRenderEngine().getSpriteBatch();
			batch.setColor(Color.WHITE);
			drawable.draw(batch, renderBounds.x, renderBounds.y, renderBounds.width, renderBounds.height);
		}

	}

	@Override
	public Color getTextRenderColor()
	{
		Color color = super.getTextRenderColor();
		Color downColor = this.downTextColor;

		if (this.getTouchHandler().getDownedPoint() != null && downColor != null)
		{
			return downColor;
		}

		return color;
	}

	public Drawable getUpDrawable()
	{
		return this.upDrawable;
	}

	public void setUpDrawable(Drawable upDrawable)
	{
		this.upDrawable = upDrawable;
	}

	public Drawable getDownDrawable()
	{
		return this.downDrawable;
	}

	public void setDownDrawable(Drawable downDrawable)
	{
		this.downDrawable = downDrawable;
	}

	public Color getDownTextColor()
	{
		return this.downTextColor;
	}

	public void setDownTextColor(Color downTextColor)
	{
		this.downTextColor = downTextColor;
	}

}
