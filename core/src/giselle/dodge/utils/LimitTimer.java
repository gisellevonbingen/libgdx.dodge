package giselle.dodge.utils;

import com.badlogic.gdx.Gdx;

public class LimitTimer
{
	public static final float INFINITY_TIME = -1.0F;

	private float limitTime = 0.0F;
	private float elapsedTime = 0.0F;

	public LimitTimer()
	{
		this.setInfinity();
	}

	public LimitTimer(float limitTime)
	{
		this.setLimitTime(limitTime);
	}

	public void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		this.elapsedTime += deltaTime;
	}

	public void resetElapsed()
	{
		this.elapsedTime = 0.0F;
	}

	public float getLimitTime()
	{
		return this.limitTime;
	}

	public boolean isInfinity()
	{
		return this.limitTime == INFINITY_TIME;
	}

	public void setInfinity()
	{
		this.limitTime = INFINITY_TIME;
	}

	public void setLimitTime(float limitTime)
	{
		this.limitTime = Math.max(0, limitTime);
	}

	public float getElapsedTime()
	{
		return elapsedTime;
	}

	public void setElapsedTime(float elapsedTime)
	{
		this.elapsedTime = Math.max(0, elapsedTime);
	}

	public float getRemainTime()
	{
		float limitTime = this.getLimitTime();
		float elapsedTime = this.getElapsedTime();

		return limitTime - elapsedTime;
	}

	public boolean isOver()
	{
		return this.isInfinity() == false && this.getRemainTime() <= 0;
	}

}
