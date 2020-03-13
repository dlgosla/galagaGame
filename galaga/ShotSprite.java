import java.awt.Image;

public class ShotSprite extends Sprite { //�Ҳ� �߻縦 �ٷ�� ���� Ŭ����
	private GalagaGame game;

	public ShotSprite(GalagaGame game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		dy = -3;
		
	}

	@Override
	public void move() { //�Ҳ��� �������� ó��
		super.move();  
		if (y < -100) { 
			game.removeSprite(this); 
		}
	}

	@Override
	public void handleCollision(Sprite other) { //�浹�� �߻��ϸ� ȣ��
		if (other instanceof AlienSprite) { //�� ��ü�� �浹�� ���� ���󸮾�ü��
			game.removeSprite(other); 		//���󸮾� ��ü�� �����
			game.removeSprite(this);		//�Ҳ� �̹����� ����
			game.score+=10;
			
		}
	}
}