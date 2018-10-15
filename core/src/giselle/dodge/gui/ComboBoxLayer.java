package giselle.dodge.gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import giselle.dodge.Dodge;
import giselle.dodge.gui.Screen.Layer;
import giselle.dodge.input.TouchPoint;
import giselle.dodge.render.StringRenderCache;
import giselle.dodge.render.TextureManager;

public class ComboBoxLayer extends Layer
{
	private ComboBox<?> owner;

	private Drawable verticalDrawable;
	private Drawable horizontalDrawable;

	private ItemRenderCache[] items;

	public ComboBoxLayer(ComboBox<?> owner)
	{
		this.owner = owner;
		Rectangle ownerScreenBounds = owner.getScreenBounds();
		Color selectedItemColor = owner.getSelectedItemColor();
		Color deselectedItemColor = owner.getDeselectedItemColor();
		int selectedIndex = owner.getSelectedIndex();

		TextureManager tm = Dodge.instance().getTextureManager();
		this.verticalDrawable = new SpriteDrawable(new Sprite(tm.get("combobox_item_vertical.png"))).tint(Color.BLACK);
		this.horizontalDrawable = new SpriteDrawable(new Sprite(tm.get("combobox_item_horizontal.png"))).tint(Color.BLACK);

		List<?> originalItems = new ArrayList<>(owner.getItems());
		this.items = new ItemRenderCache[originalItems.size()];

		float top = ownerScreenBounds.y;

		for (int i = 0; i < originalItems.size(); i++)
		{
			ItemRenderCache item = this.items[i] = new ItemRenderCache();

			ComboBoxItem<?> originalItem = (ComboBoxItem<?>) originalItems.get(i);
			StringRenderCache text = new StringRenderCache(originalItem.getText());

			if (text.getFont() == null)
			{
				text.setFont(owner.getFont());
			}

			if (selectedIndex == i)
			{
				if (selectedItemColor != null)
				{
					text.setColor(selectedItemColor);
				}

			}
			else
			{
				if (deselectedItemColor != null)
				{
					text.setColor(deselectedItemColor);
				}

			}

			text.updateDirty();
			item.setText(text);

			Rectangle bounds = new Rectangle();
			bounds.x = ownerScreenBounds.x;
			bounds.height = ownerScreenBounds.height;
			bounds.width = ownerScreenBounds.width;
			bounds.y = top - bounds.height;
			item.setBounds(bounds);

			top -= bounds.height;
		}

	}

	@Override
	public boolean onHandleInput()
	{
		TouchPoint justDownPoint = Dodge.instance().getInputManager().getJustDownPoint();

		if (justDownPoint != null)
		{
			Vector2 location = justDownPoint.getPosition();

			for (int i = 0; i < this.items.length; i++)
			{
				ItemRenderCache c = this.items[i];

				if (c.getBounds().contains(location) == true)
				{
					this.owner.setSelectedIndex(i);

					break;
				}

			}

			this.getParent().getLayers().remove(this);
		}

		return false;
	}

	@Override
	protected void renderMiddle()
	{
		super.renderMiddle();

		float fitWidth = this.verticalDrawable.getMinWidth();
		float fitHeight = this.horizontalDrawable.getMinHeight();
		Rectangle screenBounds = null;

		ItemRenderCache[] items = this.items;

		if (items.length > 0)
		{
			Rectangle first = items[0].getBounds();
			Rectangle last = items[items.length - 1].getBounds();

			screenBounds = new Rectangle(first.x, last.y, first.width, first.y - last.y + first.height);
		}

		SpriteBatch batch = Dodge.instance().getRenderEngine().getSpriteBatch();

		if (screenBounds != null)
		{
			this.verticalDrawable.draw(batch, screenBounds.x, screenBounds.y, fitWidth, screenBounds.height);
			this.verticalDrawable.draw(batch, screenBounds.x + screenBounds.width - fitWidth, screenBounds.y, fitWidth, screenBounds.height);

			this.horizontalDrawable.draw(batch, screenBounds.x, screenBounds.y - fitHeight, screenBounds.width, fitHeight);
			this.horizontalDrawable.draw(batch, screenBounds.x, screenBounds.y + screenBounds.height, screenBounds.width, fitHeight);
		}

		for (int i = 0; i < items.length; i++)
		{
			ItemRenderCache item = items[i];
			item.getText().renderBounds(batch, item.getBounds(), Alignment.middleCenter);
		}

	}

	public ComboBox<?> getOwner()
	{
		return this.owner;
	}

	private class ItemRenderCache
	{
		private StringRenderCache text = null;
		private Rectangle bounds = null;

		public ItemRenderCache()
		{

		}

		public StringRenderCache getText()
		{
			return text;
		}

		public void setText(StringRenderCache text)
		{
			this.text = text;
		}

		public Rectangle getBounds()
		{
			return bounds;
		}

		public void setBounds(Rectangle bounds)
		{
			this.bounds = bounds;
		}

	}

}
