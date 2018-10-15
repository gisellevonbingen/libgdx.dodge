package giselle.dodge.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import giselle.dodge.Dodge;
import giselle.dodge.input.TouchHandler;
import giselle.dodge.input.TouchPoint;
import giselle.dodge.utils.EventArgs;

public class Touchpad extends Control
{
	private Drawable backgroundDrawable = null;
	private Drawable knobDrawable = null;

	private Vector2 knobSize = null;

	private Vector2 knobPosition = null;
	private Vector2 knobDirection = null;

	public Touchpad()
	{
		this.knobSize = new Vector2();
		this.knobPosition = new Vector2();

		Texture texture = Dodge.instance().getTextureManager().get("touchpad.png");
		this.setBackgroundDrawable(new SpriteDrawable(new Sprite(texture)).tint(Color.BLACK));
		this.setKnobDrawable(new SpriteDrawable(new Sprite(texture)).tint(Color.BLACK));

		this.getTouchHandler().setCatchOut(true);
	}

	@Override
	public boolean onHandleInput()
	{
		if (super.onHandleInput() == false)
		{
			return false;
		}

		Rectangle bounds = this.getBounds();
		TouchHandler touchHandler = this.getTouchHandler();
		TouchPoint downedPoint = touchHandler.getDownedPoint();

		Vector2 knobPosition = new Vector2();
		Vector2 knobDirection = new Vector2();

		if (downedPoint != null)
		{
			Vector2 knobSize = this.knobSize;
			Vector2 position = new Vector2(downedPoint.getPosition());
			position.sub(bounds.width / 2, bounds.height / 2);
			Vector2 knobUnlimitedPosition = new Vector2(position.x - bounds.x, position.y - bounds.x);

			float radians = MathUtils.atan2(knobUnlimitedPosition.y, knobUnlimitedPosition.x);
			float xLimit = Math.abs((bounds.width - knobSize.x) / 2 * MathUtils.cos(radians));
			float yLimit = Math.abs((bounds.height - knobSize.y) / 2 * MathUtils.sin(radians));

			float x = Math.max(-xLimit, Math.min(xLimit, knobUnlimitedPosition.x));
			float y = Math.max(-yLimit, Math.min(yLimit, knobUnlimitedPosition.y));
			knobPosition = new Vector2(x, y);
			knobDirection = new Vector2(knobPosition.x * 2 / knobSize.x, knobPosition.y * 2 / knobSize.y);
		}

		this.knobPosition = knobPosition;
		this.knobDirection = knobDirection;

		return true;
	}

	@Override
	protected void onSizeChanged(EventArgs e)
	{
		super.onSizeChanged(e);

		this.updateKnobSize();
	}

	protected void updateKnobSize()
	{
		this.knobSize = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
	}

	@Override
	protected void renderMiddle()
	{
		super.renderMiddle();

		Dodge game = Dodge.instance();
		SpriteBatch batch = game.getRenderEngine().getSpriteBatch();
		Drawable backgroundDrawable = this.backgroundDrawable;
		Drawable knobDrawable = this.knobDrawable;

		Rectangle screenBounds = this.getScreenBounds();

		if (backgroundDrawable != null)
		{
			backgroundDrawable.draw(batch, screenBounds.x, screenBounds.y, screenBounds.width, screenBounds.height);
		}

		if (knobDrawable != null)
		{
			Vector2 knobPosition = this.knobPosition;
			Vector2 knobSize = this.knobSize;
			float x = screenBounds.x + knobPosition.x + (screenBounds.width - knobSize.x) / 2;
			float y = screenBounds.y + knobPosition.y + (screenBounds.height - knobSize.y) / 2;
			knobDrawable.draw(batch, x, y, knobSize.x, knobSize.y);
		}

	}

	public Drawable getBackgroundDrawable()
	{
		return this.backgroundDrawable;
	}

	public void setBackgroundDrawable(Drawable backgroundDrawable)
	{
		this.backgroundDrawable = backgroundDrawable;
	}

	public Drawable getKnobDrawable()
	{
		return this.knobDrawable;
	}

	public void setKnobDrawable(Drawable knobDrawable)
	{
		this.knobDrawable = knobDrawable;

		this.updateKnobSize();
	}

	public Vector2 getKnobSize()
	{
		return new Vector2(this.knobSize);
	}

	public void setKnobSize(Vector2 knobSize)
	{
		this.knobSize.set(knobSize);
	}

	public Vector2 getKnobPosition()
	{
		return new Vector2(this.knobPosition);
	}

	public Vector2 getKnobDirection()
	{
		return new Vector2(this.knobDirection);
	}

}
