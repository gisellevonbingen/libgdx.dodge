package giselle.dodge.utils;

import com.badlogic.gdx.math.Rectangle;

public class RectangleUtils
{
	public static boolean intersectWith(Rectangle rect1, Rectangle rect2)
	{
		return rect1.x < rect2.x + rect2.width && rect2.x < rect1.x + rect1.width && rect1.y < rect2.y + rect2.height && rect2.y < rect1.y + rect1.height;
	}

	private RectangleUtils()
	{

	}

}