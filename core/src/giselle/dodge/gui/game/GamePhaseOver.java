package giselle.dodge.gui.game;

import com.badlogic.gdx.graphics.Color;

import giselle.dodge.render.FontStyle;
import giselle.dodge.render.FontStyleBuilder;
import giselle.dodge.render.StringRenderCache;

public class GamePhaseOver extends GamePhase
{
	private FontStyle messageFontStyle = null;

	public GamePhaseOver()
	{
		FontStyleBuilder builder = new FontStyleBuilder();
		builder.setSize(120);
//		builder.setBorderColor(Color.BLACK);
//		builder.setBorderWidth(10);
		this.messageFontStyle = builder.build();
	}

	@Override
	public void update(GameScreen gameScreen)
	{
		ToastMessage toastMessage = new ToastMessage();
		toastMessage.setFadeOut(false);

		toastMessage.getTimer().setInfinity();

		StringRenderCache stringRenderCache = toastMessage.getStringRenderCache();
		stringRenderCache.setText("당신은 죽었습니다.\n명복을 액션빔");
		stringRenderCache.setColor(Color.BLACK);
		stringRenderCache.setFont(this.messageFontStyle);

		gameScreen.setToastMessage(toastMessage);
	}

}
