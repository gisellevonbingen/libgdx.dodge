package giselle.dodge.input;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import giselle.dodge.Dodge;
import giselle.dodge.render.RenderEngine;

public class InputManager
{
	public static final int PointerCount = 2;
	public static final int PointerNone = -1;

	private List<Controller> controllers = null;
	private Controller selectedController = null;
	private TouchPoint[] points = null;

	public InputManager(ControllerProvider controllerProvider)
	{

		this.controllers = new ArrayList<>();
		controllerProvider.provide(this.controllers);

		String selectedControllerName = Dodge.instance().getConfiguration().controller;
		this.selectedController = this.getSelectedController(selectedControllerName);

		this.points = new TouchPoint[PointerCount];

		for (int i = 0; i < this.points.length; i++)
		{
			this.points[i] = new TouchPoint(i);
		}

	}

	private Controller getSelectedController(String name)
	{
		for (Controller c : this.controllers)
		{
			if (c.getDisplayName().equals(name) == true)
			{
				return c;
			}

		}

		return this.controllers.get(0);
	}

	public List<Controller> getControllers()
	{
		return new ArrayList<>(this.controllers);
	}

	public Controller getSelectedController()
	{
		return selectedController;
	}

	public void setSelectedController(Controller controller)
	{
		this.selectedController = controller;
	}

	public void update()
	{
		RenderEngine renderEngine = Dodge.instance().getRenderEngine();
		Viewport viewport = renderEngine.getViewport();

		for (int i = 0; i < this.points.length; i++)
		{
			TouchPoint point = this.points[i];
			int index = point.getIndex();

			int screenX = Gdx.input.getX(index);
			int screenY = Gdx.input.getY(index);
			boolean touched = Gdx.input.isTouched(index);

			point.update(viewport.unproject(new Vector2(screenX, screenY)), touched);
		}

	}

	public TouchPoint getJustDownPoint()
	{
		for (int i = 0; i < this.points.length; i++)
		{
			TouchPoint point = this.points[i];

			if (point.isJustDown() == true)
			{
				return point;
			}

		}

		return null;
	}

	public TouchPoint getJustUpPoint()
	{
		for (int i = 0; i < this.points.length; i++)
		{
			TouchPoint point = this.points[i];

			if (point.isJustUp() == true)
			{
				return point;
			}

		}

		return null;
	}

	public TouchPoint getJustDownPoint(Rectangle bounds)
	{
		for (int i = 0; i < this.points.length; i++)
		{
			TouchPoint point = this.points[i];

			if (point.isJustDown() == true && bounds.contains(point.getPosition()))
			{
				return point;
			}

		}

		return null;
	}

	public TouchPoint[] getPoints()
	{
		return this.points;
	}

}
