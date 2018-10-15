package giselle.dodge.input;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import giselle.dodge.Dodge;
import giselle.dodge.gui.Button;
import giselle.dodge.gui.Touchpad;
import giselle.dodge.render.RenderEngine;

public class ControllerTocuhpad extends Controller
{
	private Touchpad touchpad;
	private Button slowButton;

	private Vector2 moveDirection;

	public ControllerTocuhpad()
	{
		this.touchpad = new Touchpad();
		this.slowButton = new Button();

		this.moveDirection = new Vector2();
	}

	@Override
	public void handleInput()
	{
		RenderEngine re = Dodge.instance().getRenderEngine();
		Rectangle renderBounds = re.getRenderBounds();
		this.touchpad.setSize(400, 400);
		this.touchpad.setLocation(50, 50);
		this.slowButton.setSize(400, 400);
		this.slowButton.setLocation(renderBounds.width - this.slowButton.getWidth() - 50, 50);

		this.touchpad.handleInput();
		this.slowButton.handleInput();

		this.moveDirection = this.touchpad.getKnobDirection();
	}

	@Override
	public void render()
	{
		this.touchpad.render();
		this.slowButton.render();
	}

	@Override
	public Vector2 getMovement(float speed)
	{
		return new Vector2(this.moveDirection).scl(speed);
	}

	@Override
	public boolean isSlowing()
	{
		return this.slowButton.getTouchHandler().getDownedPoint() != null;
	}

	@Override
	public boolean isRestart()
	{
		return false;
	}

	@Override
	public String getDisplayName()
	{
		return "터치패드";
	}

}
