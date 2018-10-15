package giselle.dodge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import giselle.dodge.gui.Screen;
import giselle.dodge.gui.main.MainScreen;
import giselle.dodge.input.ControllerProvider;
import giselle.dodge.input.InputManager;
import giselle.dodge.render.FontManager;
import giselle.dodge.render.RenderEngine;
import giselle.dodge.render.TextureManager;

public class Dodge extends ApplicationAdapter
{
	private static Dodge INSTANCE = null;

	public static Dodge instance()
	{
		return INSTANCE;
	}

	private ControllerProvider controllerProvider;

	private Configuration configuration;

	private TextureManager textureManager;
	private RenderEngine renderEngine;
	private FontManager fontManager;
	private InputManager inputManager;

	private Screen currentScreen;

	public Dodge(ControllerProvider controllerProvider)
	{
		this.controllerProvider = controllerProvider;
		INSTANCE = this;
	}

	@Override
	public void create()
	{
		this.configuration = new Configuration();

		this.textureManager = new TextureManager();
		this.renderEngine = new RenderEngine();
		this.fontManager = new FontManager();
		this.inputManager = new InputManager(this.controllerProvider);

		this.setCurrentScreen(new MainScreen());
	}

	@Override
	public void render()
	{
		this.inputManager.update();

		Input input = Gdx.input;

		if (input.isKeyPressed(Keys.SHIFT_LEFT) && input.isKeyPressed(Keys.ALT_LEFT) && input.isKeyPressed(Keys.X))
		{
			Gdx.app.exit();
			return;
		}

		this.renderEngine.render();
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);

		this.renderEngine.resize(width, height);

		Screen screen = this.getCurrentScreen();

		if (screen != null)
		{
			screen.setBounds(this.renderEngine.getRenderBounds());
		}

	}

	public Configuration getConfiguration()
	{
		return this.configuration;
	}

	public TextureManager getTextureManager()
	{
		return this.textureManager;
	}

	public RenderEngine getRenderEngine()
	{
		return this.renderEngine;
	}

	public InputManager getInputManager()
	{
		return this.inputManager;
	}

	public FontManager getFontManager()
	{
		return this.fontManager;
	}

	public void setCurrentScreen(Screen screen)
	{
		Screen previous = this.currentScreen;

		if (previous != null)
		{
			previous.onClose();
		}

		this.currentScreen = screen;

		if (screen != null)
		{
			screen.setBounds(this.renderEngine.getRenderBounds());
			screen.onOpen();
		}

	}

	public Screen getCurrentScreen()
	{
		return this.currentScreen;
	}

	@Override
	public void dispose()
	{
		this.renderEngine.dispose();
	}

}
