package giselle.dodge.gui.game;

import com.badlogic.gdx.graphics.Color;

import giselle.dodge.render.StringRenderCache;
import giselle.dodge.utils.LimitTimer;

public class GamePhaseReady extends GamePhase
{
	private LimitTimer readyTimer = null;
	private int lastWatchSeconds = 0;

	public GamePhaseReady()
	{
		this.readyTimer = new LimitTimer(4);
		this.lastWatchSeconds = 0;
	}

	private void showToastMessage(GameScreen gameScreen, int r)
	{
		ToastMessage message = new ToastMessage();
		message.getTimer().setLimitTime(1);

		StringRenderCache stringRenderCache = message.getStringRenderCache();
		stringRenderCache.setFont(120);
		stringRenderCache.setColor(Color.BLACK);

		if (r == -1)
		{
			stringRenderCache.setText("Start!");
		}
		else if (r + 1 >= this.readyTimer.getLimitTime())
		{
			stringRenderCache.setText("Ready!");
		}
		else
		{
			stringRenderCache.setText(String.valueOf(r + 1));
		}

		gameScreen.setToastMessage(message);
	}

	@Override
	public void update(GameScreen gameScreen)
	{
		this.readyTimer.update();

		float remainTime = this.readyTimer.getRemainTime();
		int remainSeconds = (int) remainTime;

		if (remainSeconds != this.lastWatchSeconds)
		{
			this.lastWatchSeconds = remainSeconds;
			this.showToastMessage(gameScreen, remainSeconds);
		}

		if (this.readyTimer.isOver() == true)
		{
			gameScreen.setPhase(new GamePhaseGame());
			this.showToastMessage(gameScreen, -1);
		}

	}

}
