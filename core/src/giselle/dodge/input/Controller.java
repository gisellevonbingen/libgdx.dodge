package giselle.dodge.input;

import com.badlogic.gdx.math.Vector2;

public abstract class Controller
{
	public abstract void handleInput();

	public abstract void render();

	public abstract Vector2 getMovement(float speed);

	public abstract boolean isSlowing();

	public abstract boolean isRestart();

	public abstract String getDisplayName();

}
