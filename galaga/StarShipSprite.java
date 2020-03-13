import java.awt.Image;

public class StarShipSprite extends Sprite { //���ּ��� ������ �ٷ� Ŭ����
	private GalagaGame game;

	public StarShipSprite(GalagaGame game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dx = 0;
		dy = 0;
	}

	@Override
	public void move() {
		if ((dx < 0) && (x < 10)) {
			return;
		}
		if ((dx > 0) && (x > 800)) {
			return;
		}
		super.move();
	}

	@Override
	public void handleCollision(Sprite other) {  //�浹�� �߻��ϸ� ȣ��
		if (other instanceof AlienSprite || other instanceof attack) { //���󸮾��̳� �Ѿ˿� ������
			game.endGame();			//���ӿ���
		}
	}
	
}