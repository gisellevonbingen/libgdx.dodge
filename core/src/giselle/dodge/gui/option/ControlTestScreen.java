package giselle.dodge.gui.option;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import giselle.dodge.Dodge;
import giselle.dodge.entity.EntityPlayer;
import giselle.dodge.gui.Button;
import giselle.dodge.gui.Screen;
import giselle.dodge.gui.game.World;
import giselle.dodge.input.Controller;
import giselle.dodge.render.EntityRenderer;
import giselle.dodge.render.RenderEngine;
import giselle.dodge.render.TextureManager;
import giselle.dodge.utils.EventArgs;

public class ControlTestScreen extends Screen
{
	private final Screen parentScreen;
	private final Controller controller;

	private Button closeButton;

	private World world;
	private EntityPlayer player;
	private EntityRenderer renderer;

	public ControlTestScreen(Screen parent, Controller controller)
	{
		this.parentScreen = parent;
		this.controller = controller;

		ControlCollection children = this.getChildren();

		Dodge game = Dodge.instance();
		TextureManager textureManager = game.getTextureManager();
		RenderEngine renderEngine = game.getRenderEngine();
		Viewport viewport = renderEngine.getViewport();

		this.closeButton = new Button();
		this.closeButton.setSize(150, 150);
		this.closeButton.setLocation(viewport.getWorldWidth() - this.closeButton.getWidth() - 50, viewport.getWorldHeight() - this.closeButton.getHeight() - 50);
		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(new TextureRegion(textureManager.get("close.png")));
		this.closeButton.setUpDrawable(textureRegionDrawable.tint(Color.BLACK));
		this.closeButton.setDownDrawable(textureRegionDrawable.tint(Color.RED));
		children.add(this.closeButton);

		this.world = new World();
		this.player = new EntityPlayer(this.world);
		this.world.getEntities().add(this.player);

		this.renderer = new EntityRenderer(this.world);
	}

	@Override
	protected void onSizeChanged(EventArgs e)
	{
		super.onSizeChanged(e);

		Vector2 size = Dodge.instance().getRenderEngine().getRenderBounds().getSize(new Vector2());
		this.world.setSize(size);
		this.player.setPosition(new Vector2(size.x / 2, size.y / 2));
	}

	@Override
	public boolean onHandleInput()
	{
		if (super.onHandleInput() == false)
		{
			return false;
		}

		if (this.closeButton.isClicked() == true)
		{
			Dodge game = Dodge.instance();
			game.setCurrentScreen(this.getParentScreen());
		}

		this.controller.handleInput();
		this.player.accept(this.controller);
		
		return true;
	}

	@Override
	protected void renderMiddle()
	{
		super.renderMiddle();

		this.controller.render();
		this.renderer.render();
	}

	public Screen getParentScreen()
	{
		return this.parentScreen;
	}

	public Controller getController()
	{
		return this.controller;
	}

}
