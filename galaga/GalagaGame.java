import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

class GalagaGame extends JPanel implements KeyListener {  
	JFrame frame = new JFrame("Galaga Game");  
	
	int r=900;
	int score = 0;
	int energy = 100;
	boolean running = true; 
	ScoreBoard sb;
	
	private ArrayList sprites = new ArrayList(); //화면에 그려질 객체들을 저장할 배열
	private Sprite starship;
	
	private BufferedImage alienImage;
	private BufferedImage shotImage;
	private BufferedImage shipImage;
	private BufferedImage bulletImage;
	private BufferedImage alienImage2;
	
	
	class th1 extends Thread{  //에일리언들의 동작을 처리할 스레드
		GalagaGame g;
		BufferedImage img;
		int img_no; //이 이미지가 총을 쏘는 에일리언인지 안 쏘는 에일리언인지 구분
		int x,y;
		
		th1(GalagaGame g,BufferedImage img, int img_no, int x ,int y){ 
			this.g=g;
			this.x=x;
			this.y=y;
			this.img=img;
			this.img_no=img_no;
		}
		
		public void run() {
			while(true){	
			try {
				r+=new Random().nextInt(800); 
				Thread.sleep(r);
			} catch(InterruptedException e) {}
			AlienSprite a = new AlienSprite(g, img, img_no,x ,y); //에얼리언의 동작을 다룰 객체 생성 
			sprites.add(a);								
			if(img_no==2)										// 총을 쏘는 에일리언이면
				new th2(a,g,bulletImage,img_no,x,y).start(); } //추가로 현재 에얼리언의 위치에서 총알을 생성할 스레드 시작
			}
		
		class th2 extends th1{  								//총알을 만들 스레드
			AlienSprite obj;
			th2(AlienSprite obj, GalagaGame g,BufferedImage img, int img_no, int x ,int y){
				super(g,img,img_no,x,y);
				this.obj=obj; 
			}
			public void run() {
			while(obj.flag==false) { //이 에일리언이 불꽃에 맞아 없어지기 전까지
				try {
					Thread.sleep(500); //0.5초 단위로
				} catch(InterruptedException e) {}
					sprites.add(new attack(obj,img)); //총알을 쏘게함
			}  }
		}

	}
	public GalagaGame(ScoreBoard sb) { 
		frame.setSize(800, 600);
		frame.add(this);
		frame.setResizable(false);
		frame.setVisible(true);
		this.sb=sb;
		frame.setLocation(0,100);

		try { //이미지를 파일에서 읽어옴
			shotImage = ImageIO.read(new File("fire.png")); 
			shipImage = ImageIO.read(new File("starship.png"));
			alienImage = ImageIO.read(new File("alien.png"));
			bulletImage = ImageIO.read(new File("bullet.png"));
			alienImage2 = ImageIO.read(new File("alien2.png"));

		} catch (IOException e) { //예외발생하면 오류내용 출력
			e.printStackTrace();
		}
		this.requestFocus(); 
		addKeyListener(this);
		
	}
	
	private void initSprites() {
		starship = new StarShipSprite(this, shipImage, 370, 550);  //내 우주선 객체를 만듦
		sprites.add(starship); 	
		new th1(this,alienImage,1,new Random().nextInt(100),new Random().nextInt(100)+10).start();  
    	//총을 안쏘는 에일리언들을 랜덤한 위치에 생성할 스레드 시작
		new th1(this,alienImage2,2,new Random().nextInt(500),new Random().nextInt(30)).start(); 
		//총을 쏘는 에일리언들을 랜덤한 위치에 생성할 스레드 시작
									
	}

	 void startGame() {  //게임이 시작되면
		new RankingFrame(this);
		this.initSprites(); 
		this.requestFocus(); 
		
	 }
	 
	public void endGame() { //게임을 종료
		running=false; 
		sb.set_s(score); 
		sb.setVisible(true);
		try {
			Thread.sleep(1000); 
		} catch(InterruptedException e) {}
			frame.dispose();// 1초 지나면 자동으로 창닫기
		
	
		
	}

	public void removeSprite(Sprite sprite) { //객체를 지워주는 메소드
		sprite.flag=true;					//이 객체가 화면에서 사라졌다는 걸 flag를 true로 바꿔서 표시
	 	sprites.remove(sprite);				
	}

	public void fire() { //불꽃객체 생성			
		if(energy>0) {
			ShotSprite shot = new ShotSprite(this, shotImage, starship.getX() + 10, starship.getY() - 30);
			sprites.add(shot);
			energy--;
		}
		else
			endGame(); 
	}

	@Override
	public void paint(Graphics g) {  //이미지를 화면에 그려줌
		super.paint(g);

		g.setColor(Color.black);	
		g.fillRect(0, 0, 800, 600); //배경
		for (int i = 0; i < sprites.size(); i++) { 
			Sprite sprite = (Sprite) sprites.get(i); 
			sprite.draw(g); 
		}
		
		g.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
		if(energy>0) {
			g.setColor(Color.WHITE);
			g.drawString("에너지: "+(int)energy,600,500);
		}else {
			g.setColor(Color.red);
			g.drawString("에너지: 없음",600,500);
		}
		
		g.setColor(Color.WHITE);
		g.drawString("점수: "+(int)score,100,500);
	}

	class gameLoop extends Thread {
		public void run() {
			while(running){	
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {}				
				for (int i = 0; i < sprites.size(); i++) { 
					Sprite sprite = (Sprite) sprites.get(i);	//i번째에 저장된 객체를 가져와서 sprite로 형변환하고 
					sprite.move(); 								//움직이게 함
				}

				for (int p = 0; p < sprites.size(); p++) { 
					for (int s = p + 1; s < sprites.size(); s++) { //모든 객체 사이를 따져봐서
						Sprite me = (Sprite) sprites.get(p);	
						Sprite other = (Sprite) sprites.get(s);

						if (me.checkCollision(other)) { //어떤 두 객체에서 충돌이 발생했으면 
							me.handleCollision(other); 
							other.handleCollision(me); //그 충돌을 처리함
						}
					}
				}

				repaint(); //다시 그림
			}
		
			}}

	@Override
	public void keyPressed(KeyEvent e) { //키가 눌리면  호출됨
		if (e.getKeyCode() == KeyEvent.VK_LEFT) 
			starship.setDx(-3);				
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			starship.setDx(+3);
		if (e.getKeyCode() == KeyEvent.VK_SPACE) 
			fire();					
	} 

	@Override
	public void keyReleased(KeyEvent e) {  //키에서 손을 떼면
		if (e.getKeyCode() == KeyEvent.VK_LEFT) 
			starship.setDx(0);
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			starship.setDx(0); 				//움직임을 멈춤
	} 

	@Override
	public void keyTyped(KeyEvent arg0) {
	}


}

