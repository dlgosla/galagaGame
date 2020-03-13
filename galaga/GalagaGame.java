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
	
	private ArrayList sprites = new ArrayList(); //ȭ�鿡 �׷��� ��ü���� ������ �迭
	private Sprite starship;
	
	private BufferedImage alienImage;
	private BufferedImage shotImage;
	private BufferedImage shipImage;
	private BufferedImage bulletImage;
	private BufferedImage alienImage2;
	
	
	class th1 extends Thread{  //���ϸ������ ������ ó���� ������
		GalagaGame g;
		BufferedImage img;
		int img_no; //�� �̹����� ���� ��� ���ϸ������� �� ��� ���ϸ������� ����
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
			AlienSprite a = new AlienSprite(g, img, img_no,x ,y); //���󸮾��� ������ �ٷ� ��ü ���� 
			sprites.add(a);								
			if(img_no==2)										// ���� ��� ���ϸ����̸�
				new th2(a,g,bulletImage,img_no,x,y).start(); } //�߰��� ���� ���󸮾��� ��ġ���� �Ѿ��� ������ ������ ����
			}
		
		class th2 extends th1{  								//�Ѿ��� ���� ������
			AlienSprite obj;
			th2(AlienSprite obj, GalagaGame g,BufferedImage img, int img_no, int x ,int y){
				super(g,img,img_no,x,y);
				this.obj=obj; 
			}
			public void run() {
			while(obj.flag==false) { //�� ���ϸ����� �Ҳɿ� �¾� �������� ������
				try {
					Thread.sleep(500); //0.5�� ������
				} catch(InterruptedException e) {}
					sprites.add(new attack(obj,img)); //�Ѿ��� �����
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

		try { //�̹����� ���Ͽ��� �о��
			shotImage = ImageIO.read(new File("fire.png")); 
			shipImage = ImageIO.read(new File("starship.png"));
			alienImage = ImageIO.read(new File("alien.png"));
			bulletImage = ImageIO.read(new File("bullet.png"));
			alienImage2 = ImageIO.read(new File("alien2.png"));

		} catch (IOException e) { //���ܹ߻��ϸ� �������� ���
			e.printStackTrace();
		}
		this.requestFocus(); 
		addKeyListener(this);
		
	}
	
	private void initSprites() {
		starship = new StarShipSprite(this, shipImage, 370, 550);  //�� ���ּ� ��ü�� ����
		sprites.add(starship); 	
		new th1(this,alienImage,1,new Random().nextInt(100),new Random().nextInt(100)+10).start();  
    	//���� �Ƚ�� ���ϸ������ ������ ��ġ�� ������ ������ ����
		new th1(this,alienImage2,2,new Random().nextInt(500),new Random().nextInt(30)).start(); 
		//���� ��� ���ϸ������ ������ ��ġ�� ������ ������ ����
									
	}

	 void startGame() {  //������ ���۵Ǹ�
		new RankingFrame(this);
		this.initSprites(); 
		this.requestFocus(); 
		
	 }
	 
	public void endGame() { //������ ����
		running=false; 
		sb.set_s(score); 
		sb.setVisible(true);
		try {
			Thread.sleep(1000); 
		} catch(InterruptedException e) {}
			frame.dispose();// 1�� ������ �ڵ����� â�ݱ�
		
	
		
	}

	public void removeSprite(Sprite sprite) { //��ü�� �����ִ� �޼ҵ�
		sprite.flag=true;					//�� ��ü�� ȭ�鿡�� ������ٴ� �� flag�� true�� �ٲ㼭 ǥ��
	 	sprites.remove(sprite);				
	}

	public void fire() { //�Ҳɰ�ü ����			
		if(energy>0) {
			ShotSprite shot = new ShotSprite(this, shotImage, starship.getX() + 10, starship.getY() - 30);
			sprites.add(shot);
			energy--;
		}
		else
			endGame(); 
	}

	@Override
	public void paint(Graphics g) {  //�̹����� ȭ�鿡 �׷���
		super.paint(g);

		g.setColor(Color.black);	
		g.fillRect(0, 0, 800, 600); //���
		for (int i = 0; i < sprites.size(); i++) { 
			Sprite sprite = (Sprite) sprites.get(i); 
			sprite.draw(g); 
		}
		
		g.setFont(new Font("�޸տ�����", Font.PLAIN, 20));
		if(energy>0) {
			g.setColor(Color.WHITE);
			g.drawString("������: "+(int)energy,600,500);
		}else {
			g.setColor(Color.red);
			g.drawString("������: ����",600,500);
		}
		
		g.setColor(Color.WHITE);
		g.drawString("����: "+(int)score,100,500);
	}

	class gameLoop extends Thread {
		public void run() {
			while(running){	
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {}				
				for (int i = 0; i < sprites.size(); i++) { 
					Sprite sprite = (Sprite) sprites.get(i);	//i��°�� ����� ��ü�� �����ͼ� sprite�� ����ȯ�ϰ� 
					sprite.move(); 								//�����̰� ��
				}

				for (int p = 0; p < sprites.size(); p++) { 
					for (int s = p + 1; s < sprites.size(); s++) { //��� ��ü ���̸� ��������
						Sprite me = (Sprite) sprites.get(p);	
						Sprite other = (Sprite) sprites.get(s);

						if (me.checkCollision(other)) { //� �� ��ü���� �浹�� �߻������� 
							me.handleCollision(other); 
							other.handleCollision(me); //�� �浹�� ó����
						}
					}
				}

				repaint(); //�ٽ� �׸�
			}
		
			}}

	@Override
	public void keyPressed(KeyEvent e) { //Ű�� ������  ȣ���
		if (e.getKeyCode() == KeyEvent.VK_LEFT) 
			starship.setDx(-3);				
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			starship.setDx(+3);
		if (e.getKeyCode() == KeyEvent.VK_SPACE) 
			fire();					
	} 

	@Override
	public void keyReleased(KeyEvent e) {  //Ű���� ���� ����
		if (e.getKeyCode() == KeyEvent.VK_LEFT) 
			starship.setDx(0);
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			starship.setDx(0); 				//�������� ����
	} 

	@Override
	public void keyTyped(KeyEvent arg0) {
	}


}

