import java.awt.Image;
import java.util.Random;

public class attack extends Sprite{ //총을 쏘는 에얼리언의 총알의 움직임을 다룰 클래스
	AlienSprite obj;
	attack(AlienSprite obj,Image image){
		super(image,obj.x,obj.y);
		dx=0; dy=4; 

	}

	
}
