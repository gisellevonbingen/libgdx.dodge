package giselle.dodge.gui.option;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import giselle.dodge.Configuration;
import giselle.dodge.Dodge;
import giselle.dodge.gui.Alignment;
import giselle.dodge.gui.Button;
import giselle.dodge.gui.ComboBox;
import giselle.dodge.gui.ComboBoxItem;
import giselle.dodge.gui.Label;
import giselle.dodge.gui.Screen;
import giselle.dodge.gui.main.MainScreen;
import giselle.dodge.input.Controller;
import giselle.dodge.input.InputManager;
import giselle.dodge.render.RenderEngine;
import giselle.dodge.render.StringRenderCache;
import giselle.dodge.render.TextureManager;

public class OptionScreen extends Screen
{
	private Button closeButton = null;

	private Label controlLabel = null;
	private ComboBox<Controller> controlComboBox = null;
	private Button controlTestButton = null;

	public OptionScreen()
	{
		ControlCollection children = this.getChildren();

		Dodge game = Dodge.instance();
		InputManager inputManager = game.getInputManager();
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

		this.controlLabel = new Label();
		this.controlLabel.setSize(300, 120);
		this.controlLabel.setText("조작방법");
		children.add(this.controlLabel);

		this.controlComboBox = new ComboBox<Controller>();
		this.controlComboBox.setSize(450, 120);
		children.add(this.controlComboBox);

		List<Controller> controllers = inputManager.getControllers();

		for (Controller controller : controllers)
		{
			ComboBoxItem<Controller> item = new ComboBoxItem<Controller>();
			item.setValue(controller);

			StringRenderCache text = item.getText();
			text.setText(controller.getDisplayName());
			this.controlComboBox.getItems().add(item);
		}

		this.controlComboBox.select(inputManager.getSelectedController());

		this.controlTestButton = new Button();
		this.controlTestButton.setText("조작 테스트");
		this.controlTestButton.setSize(500, 120);
		children.add(this.controlTestButton);
	}

	@Override
	public void onOpen()
	{
		super.onOpen();

		Vector2 size = this.getSize();
		this.controlLabel.setLocation(50, size.y - this.controlLabel.getHeight() - 300);
		this.controlComboBox.setLocation(this.controlLabel.getRight() + 50, this.controlLabel.getTop());
		this.controlTestButton.setLocation(this.controlComboBox.getRight() + 50, this.controlComboBox.getTop());
	}

	@Override
	public void onClose()
	{
		super.onClose();

		Controller controller = this.controlComboBox.getSelectedValue();

		Dodge instance = Dodge.instance();
		instance.getInputManager().setSelectedController(controller);

		Configuration configuration = instance.getConfiguration();
		configuration.controller = controller.getDisplayName();
		configuration.save();

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
			game.setCurrentScreen(new MainScreen());
		}
		else if (this.controlTestButton.isClicked() == true)
		{
			Dodge game = Dodge.instance();
			game.setCurrentScreen(new ControlTestScreen(this, this.controlComboBox.getSelectedValue()));
		}

		return true;
	}

	@Override
	protected void renderMiddle()
	{
		super.renderMiddle();

		Dodge game = Dodge.instance();
		RenderEngine renderEngine = game.getRenderEngine();
		Viewport viewport = renderEngine.getViewport();
		SpriteBatch batch = renderEngine.getSpriteBatch();

		StringRenderCache stringLayout = new StringRenderCache();
		stringLayout.setFont(150);
		stringLayout.setText("Option");
		stringLayout.setColor(Color.BLACK);
		stringLayout.renderBounds(batch, viewport.getWorldWidth() / 2, viewport.getWorldHeight() - 50, 0, 0, Alignment.topCenter);

	}

}
