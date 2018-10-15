package giselle.dodge.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import giselle.dodge.Dodge;
import giselle.dodge.gui.Alignment;
import giselle.dodge.gui.Screen;

public class RenderEngine
{
	private Viewport viewport = null;
	private SpriteBatch spriteBatch = null;

	private Rectangle renderBounds = new Rectangle();

	private float renderScale = 1.0F;
	private int renderCalls = 0;

	public RenderEngine()
	{
		this.viewport = new FitViewport(1920, 1080, new OrthographicCamera());
		this.spriteBatch = new SpriteBatch();
	}

	public void render()
	{
		Gdx.gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Matrix4 combined = this.viewport.getCamera().combined;

		this.spriteBatch.setProjectionMatrix(combined);
		this.spriteBatch.enableBlending();
		this.spriteBatch.begin();

		Dodge game = Dodge.instance();
		Screen currentScreen = game.getCurrentScreen();

		if (currentScreen != null)
		{
			currentScreen.onHandleInput();
			currentScreen.render();
		}

		StringRenderCache debug = new StringRenderCache();
		debug.setFont(40);
		debug.setText("FPS : " + Gdx.graphics.getFramesPerSecond() + ", RC : " + this.renderCalls + ", RS : " + this.renderScale);
		debug.renderBounds(this.spriteBatch, this.getRenderBounds(), Alignment.bottomRight);

		this.renderCalls = this.spriteBatch.renderCalls;
		this.spriteBatch.end();
	}

	public void resize(int width, int height)
	{
		Viewport viewport = this.viewport;
		viewport.update(width, height);
		viewport.apply(true);

		this.renderScale = (float) viewport.getScreenWidth() / viewport.getWorldWidth();
		this.renderBounds = new Rectangle(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
	}

	public void dispose()
	{
		this.spriteBatch.dispose();
	}

	public Viewport getViewport()
	{
		return this.viewport;
	}

	public SpriteBatch getSpriteBatch()
	{
		return this.spriteBatch;
	}

	public float getRenderScale()
	{
		return this.renderScale;
	}

	public int getRenderCalls()
	{
		return this.renderCalls;
	}

	public Rectangle getRenderBounds()
	{
		return new Rectangle(this.renderBounds);
	}

}
