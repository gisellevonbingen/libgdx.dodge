package giselle.dodge.gui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public enum Alignment
{
	topLeft(-1, -1), topCenter(0, -1), topRight(1, -1), middleLeft(-1, 0), middleCenter(0, 0), middleRight(1, 0), bottomLeft(-1, 1), bottomCenter(0, 1), bottomRight(1, 1),;

	private final int x;
	private final int y;

	private Alignment(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public Rectangle align(Rectangle backgroundBounds, Vector2 size, boolean yMirror)
	{
		Vector2 backgroundSize = backgroundBounds.getSize(new Vector2());
		Vector2 position = this.align(backgroundSize, size, yMirror);

		Rectangle bounds = new Rectangle();
		bounds.setPosition(backgroundBounds.x + position.x, backgroundBounds.y + position.y);
		bounds.setSize(size.x, size.y);

		return bounds;
	}

	public Vector2 align(Vector2 backgroundSize, Vector2 size, boolean yMirror)
	{
		float x = (backgroundSize.x - size.x) * (this.x + 1) / 2.0F;
		float y = (backgroundSize.y - size.y) * ((yMirror ? -this.y : this.y) + 1) / 2.0F;

		return new Vector2(x, y);
	}

}
