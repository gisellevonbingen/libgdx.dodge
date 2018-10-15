package giselle.dodge.input;

import com.badlogic.gdx.math.Vector2;

import giselle.dodge.Dodge;

public class ControllerDrag extends Controller
{
	private Vector2 prevPosition;
	private Vector2 movement;

	public ControllerDrag()
	{
		this.prevPosition = new Vector2();
		this.movement = new Vector2();
	}

	@Override
	public void handleInput()
	{
		TouchPoint touchPoint = Dodge.instance().getInputManager().getPoints()[0];
		Vector2 position = touchPoint.getPosition();
		this.movement.set(0, 0);

		if (touchPoint.isJustDown() == true)
		{

		}
		else if (touchPoint.isDowned() == true)
		{
			float dx = position.x - this.prevPosition.x;
			float dy = position.y - this.prevPosition.y;
			this.movement.set(dx, dy);
		}

		this.prevPosition.set(position);
	}

	@Override
	public void render()
	{

	}

	@Override
	public Vector2 getMovement(float speed)
	{
		return new Vector2(this.movement);
	}

	@Override
	public boolean isSlowing()
	{
		return false;
	}

	@Override
	public boolean isRestart()
	{
		return false;
	}

	@Override
	public String getDisplayName()
	{
		return "드래그";
	}

}
