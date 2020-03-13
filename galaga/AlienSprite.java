import java.awt.Image;

public class AlienSprite extends Sprite { //���󸮾� ��ü�� ������ �ٷ� Ŭ����
	private GalagaGame game;
	private int img_no;
	
	public AlienSprite(GalagaGame game, Image image, int img_no, int x, int y) { 
		super(image, x, y);
		this.game = game;
		this.img_no=img_no;
		dx = -3;
	}

	@Override
	public void move() {	//���󸮾��� �������� ó��
		if (((dx < 0) && (x < 10)) || ((dx > 0) && (x > 700))) { //�������� �����̴µ� x�� 10���� �۰ų� ���������� �����̴µ� x�� 700���� Ŀ����
			dx = -dx; 									//�����̴� ������ �ݴ�� �ٲ�
			if(img_no==1)								//���� �Ƚ�� ���󸮾���
				y += 30;								//���� ������ ����������
				
			if (y > 600) {								//���󸮾��� ȭ���� ��� �� ���� �� ������
				game.endGame();							//���ӿ���
			}
		}
		super.move();
	}
	

}