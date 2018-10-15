package giselle.dodge.gui.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import giselle.dodge.Dodge;
import giselle.dodge.gui.Alignment;
import giselle.dodge.render.RenderEngine;
import giselle.dodge.render.StringRenderCache;
import giselle.dodge.utils.LimitTimer;

public class ToastMessage
{
	private LimitTimer timer = null;
	private StringRenderCache stringRenderCache = null;

	private boolean fadeOut = false;

	public ToastMessage()
	{
		this.timer = new LimitTimer();
		this.stringRenderCache = new StringRenderCache();
		this.stringRenderCache.setFont(120);
		
		this.fadeOut = true;
	}

	public void update()
	{
		this.timer.update();

		float progress = this.timer.getElapsedTime() / this.timer.getLimitTime();

		if (this.fadeOut == true)
		{
			Color color = new Color(this.stringRenderCache.getColor());
			color.a = 1 - progress;

			this.stringRenderCache.setColor(color);
		}
		
	}

	public void render()
	{
		Dodge game = Dodge.instance();
		RenderEngine renderEngine = game.getRenderEngine();
		SpriteBatch batch = renderEngine.getSpriteBatch();
		Rectangle renderBounds = renderEngine.getRenderBounds();

		this.stringRenderCache.renderBounds(batch, renderBounds.x, renderBounds.y + renderBounds.height / 2, renderBounds.width, renderBounds.height / 2, Alignment.middleCenter);
	}

	public LimitTimer getTimer()
	{
		return this.timer;
	}

	public StringRenderCache getStringRenderCache()
	{
		return this.stringRenderCache;
	}

	public void setFadeOut(boolean fadeOut)
	{
		this.fadeOut = fadeOut;
	}

	public boolean isFadeOut()
	{
		return fadeOut;
	}

}
