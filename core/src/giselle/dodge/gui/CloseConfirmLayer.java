package giselle.dodge.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import giselle.dodge.Dodge;
import giselle.dodge.gui.Screen.Layer;
import giselle.dodge.render.RenderEngine;
import giselle.dodge.utils.EventArgs;
import giselle.dodge.utils.EventManager;
import giselle.dodge.utils.EventManager.EventHandlerCollection;

public class CloseConfirmLayer extends Layer
{
	private SpriteDrawable maskDrawableBack;
	private SpriteDrawable maskDrawableMessage;

	private Label messageLabel;
	private Button doneButton;
	private Button cancelButton;
	private Rectangle messageBounds = new Rectangle();

	private EventManager<EventArgs> doneEvent = null;

	public CloseConfirmLayer()
	{
		Texture maskTexture = Dodge.instance().getTextureManager().get("mask.png");
		this.maskDrawableBack = new SpriteDrawable(new Sprite(maskTexture)).tint(new Color(0.0F, 0.0F, 0.0F, 0.25F));
		this.maskDrawableMessage = new SpriteDrawable(new Sprite(maskTexture)).tint(new Color(0.0F, 0.0F, 0.0F, 1.0F));

		this.messageLabel = new Label();
		this.messageLabel.setTextAlignment(Alignment.middleCenter);
		this.messageLabel.setTextColor(Color.WHITE);
		this.getChildren().add(this.messageLabel);

		this.doneButton = new Button();
		this.doneButton.setTextColor(Color.WHITE);
		this.doneButton.setUpDrawable(this.doneButton.getDefaultStyleDrable(Color.WHITE));
		this.doneButton.setText("확인");
		this.doneButton.setSize(300, 100);
		this.getChildren().add(this.doneButton);

		this.cancelButton = new Button();
		this.cancelButton.setTextColor(Color.WHITE);
		this.cancelButton.setUpDrawable(this.doneButton.getDefaultStyleDrable(Color.WHITE));
		this.cancelButton.setText("취소");
		this.cancelButton.setSize(300, 100);
		this.getChildren().add(this.cancelButton);

		this.doneEvent = new EventManager<>();
	}

	public void setText(String text)
	{
		this.messageLabel.setText(text);
	}

	public String getText()
	{
		return this.messageLabel.getText();
	}

	@Override
	protected void onSizeChanged(EventArgs e)
	{
		super.onSizeChanged(e);

		Vector2 size = this.getSize();
		float buttonsWidth = this.doneButton.getWidth() + this.cancelButton.getWidth() + 100.0F;
		float buttonsHeight = Math.max(this.doneButton.getHeight(), this.cancelButton.getHeight());

		float x = (size.x - buttonsWidth) / 2;
		float y = (size.y - buttonsHeight) / 2 - 200;

		this.messageBounds = this.messageBounds.set(0, y - 100, size.x, 700);
		this.messageLabel.setSize(size.x, 400);
		this.messageLabel.setLocation(0, y + 150);

		this.doneButton.setLocation(x, y);
		this.cancelButton.setLocation(x + buttonsWidth - this.cancelButton.getWidth(), y);
	}

	protected void onDone(EventArgs e)
	{
		this.doneEvent.post(e);
	}

	@Override
	public boolean onHandleInput()
	{
		if (super.onHandleInput() == false)
		{
			return false;
		}

		if (this.doneButton.isClicked() == true)
		{
			this.onDone(new EventArgs());
		}

		if (this.cancelButton.isClicked() == true)
		{
			this.getParent().getLayers().remove(this);
		}

		return false;
	}

	@Override
	protected void renderMiddle()
	{
		RenderEngine re = Dodge.instance().getRenderEngine();
		SpriteBatch batch = re.getSpriteBatch();
		Rectangle renderBounds = re.getRenderBounds();
		Rectangle messageBounds = this.messageBounds;
		this.maskDrawableBack.draw(batch, renderBounds.x, renderBounds.y, renderBounds.width, renderBounds.height);
		this.maskDrawableMessage.draw(batch, messageBounds.x, messageBounds.y, messageBounds.width, messageBounds.height);
		batch.flush();

		super.renderMiddle();
	}

	public EventHandlerCollection<EventArgs> getDoneEvent()
	{
		return this.doneEvent.getHandlers();
	}

}
