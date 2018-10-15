package giselle.dodge.gui;

import giselle.dodge.render.StringRenderCache;

public class ComboBoxItem<T>
{
	private T value = null;
	private StringRenderCache text = null;

	public ComboBoxItem()
	{
		this.setText(new StringRenderCache());
	}

	public T getValue()
	{
		return value;
	}

	public void setValue(T value)
	{
		this.value = value;
	}

	public StringRenderCache getText()
	{
		return text;
	}

	public void setText(StringRenderCache text)
	{
		this.text = text;
	}

}
