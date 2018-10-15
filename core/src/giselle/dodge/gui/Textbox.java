package giselle.dodge.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import giselle.dodge.Dodge;
import giselle.dodge.render.TextureManager;

public class Textbox extends TextRenderableControl implements TextInputListener
{
	private Drawable drawable = null;

	private String queryTitle = null;
	private String queryHint = null;

	public Textbox()
	{
		TextureManager textureManager = Dodge.instance().getTextureManager();
		Texture texture = textureManager.get("textbox.png");
		NinePatch ninePatch = new NinePatch(texture, 32, 32, 32, 32);

		Color color = Color.BLACK;
		this.setDrawable(new NinePatchDrawable(ninePatch).tint(color));
		this.setSize(100, 70);
		this.setTextAlignment(Alignment.middleLeft);
	}

	@Override
	public boolean onHandleInput()
	{
		if (super.onHandleInput() == false)
		{
			return false;
		}
		
		if (this.isClicked() == true)
		{
			Gdx.input.getTextInput(this, this.getQueryTitle(), this.getText(), this.getQueryHint());
		}

		return true;
	}

	@Override
	protected void renderMiddle()
	{
		super.renderMiddle();

		if (this.drawable != null)
		{
			Dodge instance = Dodge.instance();
			SpriteBatch batch = instance.getRenderEngine().getSpriteBatch();
			batch.setColor(Color.WHITE);

			Rectangle renderBounds = this.getScreenBounds();
			this.drawable.draw(batch, renderBounds.x, renderBounds.y, renderBounds.width, renderBounds.height);
		}

	}

	@Override
	public Rectangle getTextRenderBounds()
	{
		Rectangle textRenderBounds = super.getTextRenderBounds();

		if (this.drawable != null)
		{
			float left = this.drawable.getLeftWidth();
			float right = this.drawable.getRightWidth();
			float bottom = this.drawable.getBottomHeight();
			float top = this.drawable.getTopHeight();
			Rectangle bounds = this.getBounds();

			textRenderBounds.x = left;
			textRenderBounds.y = bottom;
			textRenderBounds.width = bounds.width - left - right;
			textRenderBounds.height = bounds.height - bottom - top;
		}

		return textRenderBounds;
	}

	@Override
	public void input(String text)
	{
		this.setText(text);
	}

	@Override
	public void canceled()
	{

	}

	public Drawable getDrawable()
	{
		return drawable;
	}

	public void setDrawable(Drawable drawable)
	{
		this.drawable = drawable;
	}

	public String getQueryTitle()
	{
		return queryTitle;
	}

	public void setQueryTitle(String queryTitle)
	{
		this.queryTitle = queryTitle;
	}

	public String getQueryHint()
	{
		return queryHint;
	}

	public void setQueryHint(String queryHint)
	{
		this.queryHint = queryHint;
	}

}
