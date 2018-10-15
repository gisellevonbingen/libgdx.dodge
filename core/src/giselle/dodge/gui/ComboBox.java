package giselle.dodge.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;

import giselle.dodge.Dodge;
import giselle.dodge.render.StringRenderCache;
import giselle.dodge.render.TextureManager;
import giselle.dodge.utils.EventArgs;
import giselle.dodge.utils.EventManager;
import giselle.dodge.utils.EventManager.EventHandlerCollection;

public class ComboBox<T> extends TextRenderableControl
{
	private List<ComboBoxItem<T>> items = null;

	private Drawable drawable = null;
	private Drawable handleDrawable = null;
	private Rectangle handleBounds = null;

	private int selectedIndex = -1;
	private EventManager<EventArgs> selectedIndexChangedEvent;
	private Color selectedItemColor = Color.RED;
	private Color deselectedItemColor = Color.BLACK;

	private StringRenderCache cachedText = null;
	private boolean itemsDirty = false;

	public ComboBox()
	{
		this.items = new ArrayList<ComboBoxItem<T>>();

		TextureManager tm = Dodge.instance().getTextureManager();
		this.setDrawable(new NinePatchDrawable(new NinePatch(tm.get("combobox.png"), 32, 32, 32, 32)).tint(Color.BLACK));

		this.handleBounds = new Rectangle();
		this.setHandleDrawable(new SpriteDrawable(new Sprite(tm.get("combobox_handle.png"))).tint(Color.BLACK));
		this.updateHandleBounds();

		this.selectedIndexChangedEvent = new EventManager<>();
	}

	@Override
	public Rectangle getTextRenderBounds()
	{
		Rectangle bounds = super.getTextRenderBounds();
		bounds.width -= this.handleBounds.width;

		return bounds;
	}

	public void onItemsChanged()
	{
		this.itemsDirty = true;
	}

	protected void onSelectedIndexChanged(EventArgs e)
	{
		this.itemsDirty = true;
		this.selectedIndexChangedEvent.post(e);
	}

	@Override
	protected void onSizeChanged(EventArgs e)
	{
		this.updateHandleBounds();

		super.onSizeChanged(e);
	}

	private void updateHandleBounds()
	{
		Drawable handleDrawable = this.getHandleDrawable();
		Vector2 size = this.getSize();

		if (handleDrawable != null)
		{
			float tw = handleDrawable.getMinWidth();
			float th = handleDrawable.getMinHeight();
			Vector2 apply = Scaling.fit.apply(tw, th, size.x, size.y);

			this.setHandleBounds(new Rectangle(size.x - apply.x, size.y - apply.y, apply.x, apply.y));
		}
		else
		{
			this.setHandleBounds(new Rectangle(size.x, size.y, 0, 0));
		}

	}

	@Override
	public boolean onHandleInput()
	{
		if (super.onHandleInput() == false)
		{
			return false;
		}

		if (this.isClicked() == true)
		{
			Dodge.instance().getCurrentScreen().getLayers().add(new ComboBoxLayer(this));
		}

		return true;
	}

	@Override
	protected void renderMiddle()
	{
		if (this.itemsDirty == true)
		{
			this.itemsDirty = false;
			int selectedIndex = this.selectedIndex;

			if (selectedIndex == -1 || selectedIndex >= this.items.size())
			{
				this.cachedText = null;
				this.setText("");
			}
			else
			{
				StringRenderCache cache = this.items.get(selectedIndex).getText().clone();

				if (cache.getFont() == null)
				{
					cache.setFont(this.getFont());
				}

				this.cachedText = cache;
				this.setText(cache.getText());
				this.setTextColor(cache.getColor());
			}

		}

		super.renderMiddle();

		Rectangle renderBounds = this.getScreenBounds();

		Dodge game = Dodge.instance();
		SpriteBatch batch = game.getRenderEngine().getSpriteBatch();

		Drawable drawable = this.getDrawable();

		if (drawable != null)
		{
			drawable.draw(batch, renderBounds.x, renderBounds.y, renderBounds.width, renderBounds.height);
		}

		Drawable handleDrawable = this.getHandleDrawable();

		if (handleDrawable != null)
		{
			Rectangle handleBounds = this.getHandleBounds();
			handleDrawable.draw(batch, renderBounds.x + handleBounds.x, renderBounds.y + handleBounds.y, handleBounds.width, handleBounds.height);
		}

	}

	public Drawable getDrawable()
	{
		return this.drawable;
	}

	public void setDrawable(Drawable drawable)
	{
		this.drawable = drawable;
	}

	public Drawable getHandleDrawable()
	{
		return handleDrawable;
	}

	public void setHandleDrawable(Drawable handleDrawable)
	{
		this.handleDrawable = handleDrawable;
	}

	public Rectangle getHandleBounds()
	{
		return new Rectangle(this.handleBounds);
	}

	public void setHandleBounds(Rectangle handleBounds)
	{
		this.handleBounds = handleBounds;
	}

	public List<ComboBoxItem<T>> getItems()
	{
		return this.items;
	}

	public int getSelectedIndex()
	{
		return this.selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex)
	{
		this.selectedIndex = selectedIndex;

		this.onSelectedIndexChanged(new EventArgs());
	}

	public EventHandlerCollection<EventArgs> getSelectedIndexChangedEvent()
	{
		return selectedIndexChangedEvent.getHandlers();
	}

	public Color getSelectedItemColor()
	{
		return selectedItemColor;
	}

	public void setSelectedItemColor(Color selectedItemColor)
	{
		this.selectedItemColor = selectedItemColor;
	}

	public Color getDeselectedItemColor()
	{
		return deselectedItemColor;
	}

	public void setDeselectedItemColor(Color deselectedItemColor)
	{
		this.deselectedItemColor = deselectedItemColor;
	}

	public int findIndex(T value)
	{
		for (int i = 0; i < this.items.size(); i++)
		{
			ComboBoxItem<T> o = this.items.get(i);

			if (Objects.equals(o.getValue(), value) == true)
			{
				return i;
			}

		}

		return -1;
	}

	public ComboBoxItem<T> getSelectedItem()
	{
		if (-1 < this.selectedIndex && this.selectedIndex < this.items.size())
		{
			return this.items.get(this.selectedIndex);
		}

		return null;
	}

	public T getSelectedValue()
	{
		return this.getSelectedValue(null);
	}

	public T getSelectedValue(T fallback)
	{
		ComboBoxItem<T> item = this.getSelectedItem();

		if (item != null)
		{
			return item.getValue();
		}

		return fallback;
	}

	public int select(T value)
	{
		int index = this.findIndex(value);
		this.setSelectedIndex(index);

		return index;
	}

}
