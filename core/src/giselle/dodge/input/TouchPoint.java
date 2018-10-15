package giselle.dodge.input;

import com.badlogic.gdx.math.Vector2;

public class TouchPoint
{
	private final int index;

	private Vector2 position = null;
	private boolean downed = false;
	private boolean prevDowned = false;

	public TouchPoint(int index)
	{
		this.index = index;
		this.position = new Vector2();
	}

	public int getIndex()
	{
		return this.index;
	}

	public void update(Vector2 position, boolean downed)
	{
		this.prevDowned = this.downed;

		this.position.set(position);
		this.downed = downed;
	}

	public Vector2 getPosition()
	{
		return new Vector2(this.position);
	}

	public boolean isDowned()
	{
		return this.downed;
	}

	public boolean isJustDown()
	{
		return this.prevDowned == false && this.downed == true;
	}

	public boolean isJustUp()
	{
		return this.prevDowned == true && this.downed == false;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append("Position=").append(this.position);
		builder.append(", ");
		builder.append("Downed=").append(this.downed);
		builder.append("}");

		return builder.toString();
	}

}
