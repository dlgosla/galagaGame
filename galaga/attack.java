import java.awt.Image;
import java.util.Random;

public class attack extends Sprite{ //���� ��� ���󸮾��� �Ѿ��� �������� �ٷ� Ŭ����
	AlienSprite obj;
	attack(AlienSprite obj,Image image){
		super(image,obj.x,obj.y);
		dx=0; dy=4; 

	}

	
}
