package giselle.dodge.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import giselle.dodge.Dodge;
import giselle.dodge.render.TextureManager;

public class CheckBox extends TextRenderableControl
{
	private Drawable noneDrawable = null;
	private Drawable checkedDrawable = null;

	private boolean checked = false;

	public CheckBox()
	{
		TextureManager textureManager = Dodge.instance().getTextureManager();

		Sprite sprite = new Sprite(textureManager.get("checkbox_none.png"));
		sprite.setSize(74, 66);
		this.setNoneDrawable(new SpriteDrawable(sprite));

		Sprite sprite2 = new Sprite(textureManager.get("checkbox_checked.png"));
		sprite2.setSize(74, 66);
		this.setCheckedDrawable(new SpriteDrawable(sprite2));

		this.setSize(74, 66);
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
			this.checked ^= true;
		}

		return true;
	}

	@Override
	public Rectangle getTextRenderBounds()
	{
		Rectangle textRenderBounds = super.getTextRenderBounds();
		Drawable drawable = this.getCurrentDrawable();

		if (drawable != null)
		{
			float minWidth = drawable.getMinWidth() + 10;
			textRenderBounds.x += minWidth;
			textRenderBounds.width -= minWidth;
			textRenderBounds.y = 0;
			textRenderBounds.height = drawable.getMinHeight();
		}

		return textRenderBounds;
	}

	private Drawable getCurrentDrawable()
	{
		Drawable noneDrawable = this.getNoneDrawable();
		Drawable checkedDrawable = this.getCheckedDrawable();

		Drawable drawable = noneDrawable;

		if (this.isChecked() == true)
		{
			drawable = checkedDrawable;
		}

		return drawable;
	}

	@Override
	protected void renderMiddle()
	{
		super.renderMiddle();

		Drawable drawable = this.getCurrentDrawable();

		if (drawable != null)
		{
			Dodge instance = Dodge.instance();
			SpriteBatch batch = instance.getRenderEngine().getSpriteBatch();
			batch.setColor(Color.WHITE);

			Rectangle renderBounds = this.getScreenBounds();
			drawable.draw(batch, renderBounds.x, renderBounds.y, drawable.getMinWidth(), drawable.getMinHeight());
		}

	}

	public Drawable getNoneDrawable()
	{
		return noneDrawable;
	}

	public void setNoneDrawable(Drawable noneDrawable)
	{
		this.noneDrawable = noneDrawable;
	}

	public Drawable getCheckedDrawable()
	{
		return checkedDrawable;
	}

	public void setCheckedDrawable(Drawable checkedDrawable)
	{
		this.checkedDrawable = checkedDrawable;
	}

	public boolean isChecked()
	{
		return this.checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

}
