package giselle.dodge.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class ControllerKeyboard extends Controller
{
	private final Vector2 direction;
	private boolean slowing = false;

	private int[] leftKeys = new int[0];
	private int[] topKeys = new int[0];
	private int[] rightKeys = new int[0];
	private int[] bottomKeys = new int[0];
	private int[] slowKeys = new int[0];
	private int[] restartKeys = new int[0];

	public ControllerKeyboard()
	{
		this.direction = new Vector2();

		this.leftKeys = new int[]{Keys.A, Keys.LEFT};
		this.topKeys = new int[]{Keys.W, Keys.UP};
		this.rightKeys = new int[]{Keys.D, Keys.RIGHT};
		this.bottomKeys = new int[]{Keys.S, Keys.DOWN};
		this.slowKeys = new int[]{Keys.SHIFT_LEFT};
		this.restartKeys = new int[]{Keys.R};
	}

	public boolean isKeyPressed(Input input, int[] keys)
	{
		for (int key : keys)
		{
			if (input.isKeyPressed(key) == true)
			{
				return true;
			}

		}

		return false;
	}

	@Override
	public void handleInput()
	{
		Input input = Gdx.input;

		float x = 0.0F;
		float y = 0.0F;

		if (this.isKeyPressed(input, this.leftKeys) == true)
		{
			x -= 1;
		}

		if (this.isKeyPressed(input, this.topKeys) == true)
		{
			y += 1;
		}

		if (this.isKeyPressed(input, this.rightKeys) == true)
		{
			x += 1;
		}

		if (this.isKeyPressed(input, this.bottomKeys) == true)
		{
			y -= 1;
		}

		this.direction.set(x, y);

		this.slowing = this.isKeyPressed(input, this.slowKeys);
	}

	@Override
	public boolean isSlowing()
	{
		return this.slowing;
	}

	@Override
	public void render()
	{

	}

	@Override
	public Vector2 getMovement(float speed)
	{
		return new Vector2(this.direction).scl(speed);
	}

	@Override
	public boolean isRestart()
	{
		return this.isKeyPressed(Gdx.input, this.restartKeys);
	}

	@Override
	public String getDisplayName()
	{
		return "키보드";
	}

}
