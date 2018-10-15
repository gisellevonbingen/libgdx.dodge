package giselle.dodge.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import giselle.dodge.utils.EventArgs;

public class Screen extends Control
{
	private LayerCollection layers;

	public Screen()
	{
		this.layers = new LayerCollection(this);
	}

	@Override
	public boolean onHandleInput()
	{
		for (Layer layer : Arrays.asList(this.layers.toArray()))
		{
			layer.handleInputGlobal();
		}

		for (Layer layer : Arrays.asList(this.layers.toArray()))
		{
			boolean conti = layer.onHandleInput();

			if (conti == false)
			{
				return false;
			}

		}

		return super.onHandleInput();
	}

	@Override
	protected void onSizeChanged(EventArgs e)
	{
		super.onSizeChanged(e);

		Vector2 size = this.getSize();

		for (Layer layer : Arrays.asList(this.layers.toArray()))
		{
			layer.setSize(size);
		}

	}

	@Override
	protected void renderTop()
	{
		super.renderTop();

		for (Layer layer : Arrays.asList(this.layers.toArray()))
		{
			layer.render();
		}

	}

	public void onClose()
	{
		for (Layer layer : Arrays.asList(this.layers.toArray()))
		{
			this.layers.remove(layer);
		}

	}

	public void onOpen()
	{
		this.onSizeChanged(new EventArgs());
	}

	public LayerCollection getLayers()
	{
		return this.layers;
	}

	public static class Layer extends Control
	{
		private Screen parent = null;

		public Layer()
		{

		}

		public void open(Screen parent)
		{
			this.parent = parent;
			this.setSize(parent.getSize());
		}

		public void close()
		{

		}

		public void handleInputGlobal()
		{

		}

		public Screen getParent()
		{
			return this.parent;
		}

	}

	public class LayerCollection implements Iterable<Layer>
	{
		private final Screen owner;
		private final List<Layer> children;

		private LayerCollection(Screen owner)
		{
			this.owner = owner;
			this.children = new ArrayList<>();
		}

		public boolean add(Layer layer)
		{
			if (this.contains(layer) == true)
			{
				return false;
			}

			Screen parent = layer.getParent();

			if (parent != null)
			{
				return false;
			}

			this.children.add(layer);
			layer.open(this.owner);

			return true;
		}

		public boolean contains(Layer layer)
		{
			return this.children.contains(layer);
		}

		public boolean remove(Layer layer)
		{
			if (this.contains(layer) == false)
			{
				return false;
			}

			Screen parent = layer.getParent();

			if (parent != this.owner)
			{
				return false;
			}

			this.children.remove(layer);
			layer.close();

			return true;
		}

		public Layer[] toArray()
		{
			return this.children.toArray(new Layer[0]);
		}

		public Screen getOwner()
		{
			return this.owner;
		}

		@Override
		public Iterator<Layer> iterator()
		{
			return this.children.iterator();
		}

	}

}
