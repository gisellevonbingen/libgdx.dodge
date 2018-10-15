package giselle.dodge.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.viewport.Viewport;

import giselle.dodge.Dodge;
import giselle.dodge.gui.Screen.Layer;
import giselle.dodge.input.TouchHandler;
import giselle.dodge.render.RenderEngine;
import giselle.dodge.utils.EventArgs;
import giselle.dodge.utils.EventManager;
import giselle.dodge.utils.EventManager.EventHandlerCollection;

public class Control
{
	private ControlCollection children;
	private Control parent;

	private Rectangle bounds;
	private EventManager<EventArgs> boundsChangedEvent;
	private EventManager<EventArgs> locationChangedEvent;
	private EventManager<EventArgs> sizeChangedEvent;

	private boolean visible;
	private EventManager<EventArgs> visibleChangedEvent;

	private TouchHandler touchHandler;
	private EventManager<EventArgs> clickEvent;

	public Control()
	{
		this.children = new ControlCollection(this);
		this.parent = null;

		this.bounds = new Rectangle();
		this.boundsChangedEvent = new EventManager<>();
		this.locationChangedEvent = new EventManager<>();
		this.sizeChangedEvent = new EventManager<>();

		this.visible = true;
		this.visibleChangedEvent = new EventManager<>();

		this.touchHandler = new TouchHandler();
		this.clickEvent = new EventManager<>();
	}

	public boolean isVisible()
	{
		return this.visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;

		if (visible == false)
		{
			this.clearInputStatus();
		}

		this.onVisibleChanged(new EventArgs());
	}

	public EventHandlerCollection<EventArgs> getVisibleChangedEvent()
	{
		return visibleChangedEvent.getHandlers();
	}

	protected void onVisibleChanged(EventArgs e)
	{
		this.visibleChangedEvent.post(e);
	}

	public boolean handleInput()
	{
		if (this.canHandleInput() == false)
		{
			return false;
		}

		return this.onHandleInput();
	}

	protected boolean canHandleInput()
	{
		if (this.visible == false)
		{
			return false;
		}

		return true;
	}

	protected void clearInputStatus()
	{
		this.touchHandler.clear();
	}

	public boolean isClicked()
	{
		return this.touchHandler.isClicked();
	}

	protected boolean onHandleInput()
	{
		this.touchHandler.update(this.getScreenBounds());

		if (this.touchHandler.isClicked() == true)
		{
			this.clickEvent.post(new EventArgs());
		}

		for (Control child : this.children)
		{
			child.handleInput();
		}

		return true;
	}

	public void render()
	{
		if (this.visible == false)
		{
			return;
		}

		this.renderBottom();

		this.renderMiddle();

		this.renderTop();
	}

	protected void renderTop()
	{

	}

	protected void renderMiddle()
	{
		for (Control child : this.children)
		{
			this.renderChild(child);
		}

	}

	protected void renderBottom()
	{

	}

	private void renderChild(Control child)
	{
		Dodge instance = Dodge.instance();
		RenderEngine renderEngine = instance.getRenderEngine();
		SpriteBatch spriteBatch = renderEngine.getSpriteBatch();

		Viewport viewport = renderEngine.getViewport();
		Camera camera = viewport.getCamera();
		Matrix4 transformMatrix = spriteBatch.getTransformMatrix();

		Rectangle scissors = new Rectangle();
		ScissorStack.calculateScissors(camera, viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight(), transformMatrix, child.getScreenBounds(), scissors);
		boolean b = ScissorStack.pushScissors(scissors);

		if (b == true)
		{
			child.render();

			spriteBatch.flush();

			ScissorStack.popScissors();
		}

	}

	public Rectangle getScreenBounds()
	{
		Control parent = this.parent;
		Rectangle renderBounds = this.getBounds();

		if (parent != null)
		{
			Rectangle parentRenderBounds = parent.getScreenBounds();
			renderBounds.x += parentRenderBounds.x;
			renderBounds.y += parentRenderBounds.y;
		}

		return renderBounds;
	}

	public Rectangle getBounds()
	{
		return new Rectangle(this.bounds);
	}

	public void setBounds(Rectangle bounds)
	{
		this.bounds.set(bounds);
		this.onLocationChanged(new EventArgs());
		this.onSizeChanged(new EventArgs());
	}

	public Vector2 getLocation()
	{
		return this.bounds.getPosition(new Vector2());
	}

	public void setLocation(Vector2 location)
	{
		this.bounds.setPosition(location);
		this.onLocationChanged(new EventArgs());
	}

	public void setLocation(float x, float y)
	{
		this.bounds.setPosition(x, y);
		this.onLocationChanged(new EventArgs());
	}

	public Vector2 getSize()
	{
		return this.bounds.getSize(new Vector2());
	}

	public void setSize(Vector2 size)
	{
		this.bounds.setSize(size.x, size.y);
		this.onSizeChanged(new EventArgs());
	}

	public void setSize(float width, float height)
	{
		this.bounds.setSize(width, height);
		this.onSizeChanged(new EventArgs());
	}

	public float getX()
	{
		return this.bounds.x;
	}

	public float getY()
	{
		return this.bounds.y;
	}

	public float getLeft()
	{
		return this.bounds.x;
	}

	public float getTop()
	{
		return this.bounds.y;
	}

	public float getRight()
	{
		return this.bounds.x + this.bounds.width;
	}

	public float getBottom()
	{
		return this.bounds.y - this.bounds.height;
	}

	public float getWidth()
	{
		return this.bounds.width;
	}

	public float getHeight()
	{
		return this.bounds.height;
	}

	public void setX(float x)
	{
		this.bounds.x = x;
		this.onLocationChanged(new EventArgs());
	}

	public void setY(float y)
	{
		this.bounds.y = y;
		this.onLocationChanged(new EventArgs());
	}

	public void setWidth(float width)
	{
		this.bounds.width = width;
		this.onSizeChanged(new EventArgs());
	}

	public void setHeight(float height)
	{
		this.bounds.height = height;
		this.onSizeChanged(new EventArgs());
	}

	public EventHandlerCollection<EventArgs> getBoundsChangedEvent()
	{
		return this.boundsChangedEvent.getHandlers();
	}

	public EventHandlerCollection<EventArgs> getSizeChangedEvent()
	{
		return this.sizeChangedEvent.getHandlers();
	}

	public EventHandlerCollection<EventArgs> getLocationChangedEvent()
	{
		return this.locationChangedEvent.getHandlers();
	}

	protected void onBoundsChanged(EventArgs e)
	{
		this.boundsChangedEvent.post(e);
	}

	protected void onLocationChanged(EventArgs e)
	{
		this.locationChangedEvent.post(e);

		this.onBoundsChanged(e);
	}

	protected void onSizeChanged(EventArgs e)
	{
		this.sizeChangedEvent.post(e);

		this.onBoundsChanged(e);
	}

	public ControlCollection getChildren()
	{
		return this.children;
	}

	public TouchHandler getTouchHandler()
	{
		return this.touchHandler;
	}

	public EventHandlerCollection<EventArgs> getClickEventHandlers()
	{
		return this.clickEvent.getHandlers();
	}

	public Control getParent()
	{
		return this.parent;
	}

	private void setParent(Control parent)
	{
		this.parent = parent;
	}

	public class ControlCollection implements Iterable<Control>
	{
		private final Control owner;
		private final List<Control> children;

		private ControlCollection(Control owner)
		{
			this.owner = owner;
			this.children = new ArrayList<>();
		}

		public void add(Control control)
		{
			Control parent = control.getParent();

			if (parent != null)
			{
				throw new IllegalArgumentException();
			}

			if (control instanceof Layer)
			{
				throw new IllegalArgumentException();
			}

			if (this.children.contains(control) == true)
			{
				throw new IllegalArgumentException();
			}

			control.setParent(this.owner);
			this.children.add(control);
		}

		public void remove(Control control)
		{
			Control parent = control.getParent();

			if (parent != this.owner)
			{
				return;
			}

			if (this.children.contains(control) == false)
			{
				return;
			}

			control.setParent(null);
			this.children.remove(control);
		}

		public Control getOwner()
		{
			return this.owner;
		}

		@Override
		public Iterator<Control> iterator()
		{
			return this.children.iterator();
		}

	}

}
