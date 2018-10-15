package giselle.dodge.gui.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import giselle.dodge.Dodge;
import giselle.dodge.gui.Alignment;
import giselle.dodge.gui.Button;
import giselle.dodge.gui.CloseConfirmLayer;
import giselle.dodge.gui.Screen;
import giselle.dodge.gui.game.GameScreen;
import giselle.dodge.gui.option.OptionScreen;
import giselle.dodge.render.FontManager;
import giselle.dodge.render.RenderEngine;
import giselle.dodge.render.StringRenderCache;
import giselle.dodge.render.TextureManager;
import giselle.dodge.utils.EventArgs;
import giselle.dodge.utils.IEventHandler;

public class MainScreen extends Screen
{
	private Button startButton = null;
	private Button optionButton = null;

	private Button closeButton = null;

	public MainScreen()
	{
		Dodge game = Dodge.instance();
		TextureManager textureManager = game.getTextureManager();
		RenderEngine renderEngine = game.getRenderEngine();
		Viewport viewport = renderEngine.getViewport();
		ControlCollection children = this.getChildren();

		this.startButton = new Button();
		this.startButton.setSize(1024, 250);
		this.startButton.setLocation((viewport.getWorldWidth() - this.startButton.getWidth()) / 2, 400);
		this.startButton.setText("시작\n일지도?");
		children.add(this.startButton);

		this.optionButton = new Button();
		this.optionButton.setSize(1024, 250);
		this.optionButton.setLocation((viewport.getWorldWidth() - optionButton.getWidth()) / 2, 100);
		this.optionButton.setText("옵션");
		children.add(this.optionButton);

		this.closeButton = new Button();
		this.closeButton.setSize(150, 150);
		this.closeButton.setLocation(viewport.getWorldWidth() - this.closeButton.getWidth() - 50, viewport.getWorldHeight() - this.closeButton.getHeight() - 50);
		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(new TextureRegion(textureManager.get("close.png")));
		this.closeButton.setUpDrawable(textureRegionDrawable.tint(Color.BLACK));
		this.closeButton.setDownDrawable(textureRegionDrawable.tint(Color.RED));
		children.add(this.closeButton);
	}

	@Override
	public boolean onHandleInput()
	{
		if (super.onHandleInput() == false)
		{
			return false;
		}

		Dodge game = Dodge.instance();

		if (this.startButton.isClicked() == true)
		{
			game.setCurrentScreen(new GameScreen());
		}
		else if (this.optionButton.isClicked() == true)
		{
			game.setCurrentScreen(new OptionScreen());
		}
		else if (this.closeButton.isClicked() == true)
		{
			CloseConfirmLayer layer = new CloseConfirmLayer();
			layer.setText("정말로 종료하시겠습니까?");
			layer.getDoneEvent().add(new IEventHandler<EventArgs>()
			{
				@Override
				public void onPost(EventArgs e)
				{
					Gdx.app.exit();
				}
			});

			this.getLayers().add(layer);
		}

		return true;
	}

	@Override
	protected void renderMiddle()
	{
		super.renderMiddle();

		Dodge game = Dodge.instance();
		FontManager fontManager = game.getFontManager();
		RenderEngine renderEngine = game.getRenderEngine();
		Viewport viewport = renderEngine.getViewport();
		SpriteBatch batch = renderEngine.getSpriteBatch();

		BitmapFont font = fontManager.getFont(240);
		String text = "D.o.d.g.e";
		StringRenderCache stringLayout = new StringRenderCache();
		stringLayout.setFont(font);
		stringLayout.setText(text);
		stringLayout.setColor(Color.BLACK);

		font.setColor(Color.BLACK);
		stringLayout.renderBounds(batch, viewport.getWorldWidth() / 2, viewport.getWorldHeight() - 100, 0, 0, Alignment.topCenter);
	}

}
