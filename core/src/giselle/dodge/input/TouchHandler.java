package giselle.dodge.input;

import com.badlogic.gdx.math.Rectangle;

import giselle.dodge.Dodge;

public class TouchHandler
{
	private TouchPoint downedPoint = null;
	private boolean isClicked = false;

	private boolean catchOut = false;

	public TouchHandler()
	{

	}

	public void update(Rectangle bounds)
	{
		boolean isClicked = false;
		Dodge game = Dodge.instance();

		if (this.downedPoint == null)
		{
			InputManager inputManager = game.getInputManager();
			this.downedPoint = inputManager.getJustDownPoint(bounds);
		}
		else if (this.catchOut == false && bounds.contains(this.downedPoint.getPosition()) == false)
		{
			this.downedPoint = null;
		}
		else if (this.downedPoint.isDowned() == false)
		{
			this.downedPoint = null;

			isClicked = true;
		}

		this.isClicked = isClicked;
	}

	public void clear()
	{
		this.downedPoint = null;
		this.isClicked = false;
	}

	public void setCatchOut(boolean catchOut)
	{
		this.catchOut = catchOut;
	}

	public boolean isCatchOut()
	{
		return this.catchOut;
	}

	public TouchPoint getDownedPoint()
	{
		return this.downedPoint;
	}

	public boolean isClicked()
	{
		return this.isClicked;
	}

}
