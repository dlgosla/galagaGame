import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class menu extends JFrame{
   ScoreBoard sb = new ScoreBoard();
   GalagaGame g;
   textDialog dialog = new textDialog(this, "Test Modal Dialog");
   private JLabel label = new JLabel(); //텍스트를 넣을 label 생성
   private JLabel imgLabel = new JLabel(); //이미지를 넣을 label 생성

   public menu() {
	   try {
		new FileOutputStream("ranking.txt").close();
	} catch (IOException e) {}
      setLayout(new FlowLayout());// 플로우레이아웃을 이용
      getContentPane().add(label);
      getContentPane().add(imgLabel);
      createMenu();
      setSize(250,200);
      setVisible(true);
      
   }
   class choice extends JFrame implements ActionListener{ //메뉴에서 갤러그 게임을 누르면  

		JButton b1 = new JButton("시작하기");	
		JButton b2 = new JButton("그만하기"); 
		choice(){
			setLayout(new FlowLayout());	
			setSize(300,100);		
			add(b1);			
			b1.addActionListener(this);	
			add(b2);
			b2.addActionListener(this);	
			setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			if(b==b1)	{ // 시작하기를 누르면
				 g= new GalagaGame(sb); //갤러그 게임 객체 생성하고
				 g.startGame(); // 시작
				}
			else if(b==b2) { //그만하기 누르면
				g.endGame(); //이 게임을 종료 
				}
   }
	}	
   
   class gameActionListener implements ActionListener{ // 게임 메뉴의 액션 처리 
	   
	    public void actionPerformed(ActionEvent e) {
	       switch(e.getActionCommand()) { 

	       case "갤러그 게임":
	    	   new choice();
	          break; 

	          }
	    }
	}

   
   class textDialog extends JDialog{
   
      JTextField tf = new JTextField(10);//텍스트를 입력할 공간 생성 
      JButton okButton = new JButton("OK");//입력한 텍스트를 출력하게끔 할 수 있는 버튼 생성
      
      public textDialog(JFrame frame, String title) {
         super(frame,title,true); 
         setLayout(new FlowLayout());
         add(tf); //텍스트필드 부착
         add(okButton); //버튼 부착
         setSize(200, 100);

         okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               setVisible(false); // 버튼을 누르면 창이 꺼짐
               }
            });
         }

      public String getInput() {
         if(tf.getText().length() == 0) return null; // 텍스트필드에 text가 없으면 널값으로 반환
         else return tf.getText(); //있으면 텍스트를 반환
         }
      }
   
   private void createMenu() {
      
      JMenuBar mb = new JMenuBar();
      
      JMenuItem[] textItem = new JMenuItem [3]; //텍스트 메뉴 아이템 생성
      String[] textItems = {"입력 및 출력","색상 변경", "텍스트 제거"}; // 텍스트 아이템 배열 생성
      JMenu textMenu = new JMenu("Text"); // 텍스트 메뉴 생성
      
      JMenuItem[] imageItem = new JMenuItem [2]; //이미지 메뉴 아이템 생성
      String[] imageItems = {"이미지 선택","이미지 제거"};//이미지 아이템 배열 생성
      JMenu imageMenu = new JMenu("Image");// 이미지 메뉴 생성
      
      JMenuItem[] soundItem = new JMenuItem [3];//사운드 메뉴 아이템 생성
      String[] soundItems = {"실행","중지","다시 시작"};//사운드 메뉴 아이템 배열 생성
      JMenu soundMenu = new JMenu("Sound");//사운드 메뉴 생성
      
      JMenuItem[] gameItem = new JMenuItem [1];
      String[] gameItems = {"갤러그 게임"};
      JMenu gameMenu = new JMenu("Game");

      TextActionListener listener = new TextActionListener();
      for(int i=0; i<textItem.length; i++) {
         textItem[i] = new JMenuItem(textItems[i]); 
         textItem[i].addActionListener(listener);
         textMenu.add(textItem[i]);
      }
      
      ImageActionListener listener2 = new ImageActionListener();
      for(int i=0; i<imageItem.length; i++) {
         imageItem[i] = new JMenuItem(imageItems[i]); 
         imageItem[i].addActionListener(listener2);
         imageMenu.add(imageItem[i]); 
      }
      
      SoundActionListener listener3 = new SoundActionListener();
      for(int i=0; i<soundItem.length; i++) {
         soundItem[i] = new JMenuItem(soundItems[i]); 
         soundItem[i].addActionListener(listener3);
         soundMenu.add(soundItem[i]); 
      }
      
      gameActionListener listener4 = new gameActionListener();
      for(int i=0; i<gameItem.length; i++) {
    	  gameItem[i] = new JMenuItem(gameItems[i]); 
    	  gameItem[i].addActionListener(listener4);
    	  gameMenu.add(gameItem[i]); 
      }
      
      mb.add(textMenu);
      mb.add(soundMenu);
      mb.add(imageMenu);
      mb.add(gameMenu);
      // 메뉴들을 메뉴바에 부착
 
      setJMenuBar(mb);//메뉴바 부착
   }
   


   class SoundActionListener implements ActionListener{//사운드 이벤트를 처리할 클래스
      Clip clip;               //오디오 클립을 만들어서 저장할 변수
      SoundActionListener(){//생성자
              try {
                  clip = AudioSystem.getClip();
   //Clip인터페이스로 클립을 만듦
                  File audioFile = new File("audio/닭.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                  //오디오 파일로부터 오디오를 읽을 스트림 객체 생성
                  clip.open(audioStream);
                  //오디오 클립에 오디오 스트림을 연결
               } catch (LineUnavailableException e1) { e1.printStackTrace(); }
               catch(UnsupportedAudioFileException e1) { e1.printStackTrace(); }
               catch (IOException e1) {e1.printStackTrace(); }}
   //이런 예외가 발생하면 오류 내역을 보여줌
         public void actionPerformed(ActionEvent e) {//사운드 이벤트가 발생하면
            switch(e.getActionCommand()) { 
   //이벤트를 발생시킨 객체의 텍스트값이
            case "실행": clip.start(); 
   //실행이면 clip클래스의 start()메소드를 호출
               break;
            case "중지": clip.stop();// 중지면 stop()메소드 호출
               break;
   
            case "다시 시작": clip.setFramePosition(0); 
   //다시 시작이면 현재 프레임위치를 0으로 바꾸고
                     clip.start();//시작
               break;   
               }
         }
   }

   
   class TextActionListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         String cmd = e.getActionCommand();
         switch(cmd) {// 메뉴 아이템 구분
            case "입력 및 출력" : 
               
               dialog.setVisible(true);//다이얼로그 활성화
               
               String text = dialog.getInput();//텍스트를 저장할 변수 생성

               if(text == null) return; //변수에 아무 입력이 없을경우 그냥 반환,
               label.setText(text);//있으면 label에 입력된 텍스트 출력
               
               break;
               
            case "색상 변경" :
                Color selectedColor = 
                  JColorChooser.showDialog(null, "Color", 
                                             Color.YELLOW);// 색깔을 선택 할 수 있는 colorchooser 활성화
            if(selectedColor != null)// 선택받은 컬러가 null값이 아닌 경우에
               label.setForeground(selectedColor);// 선택시 텍스트 색상 변경

               break;
            case "텍스트 제거" :
               label.setText("");// 텍스트를 공백으로 채움
         }

      }
   }
   class ImageActionListener implements ActionListener {
      private JFileChooser chooser;

      public ImageActionListener() {
         chooser = new JFileChooser(); 
      }
      public void actionPerformed(ActionEvent e) {
         String cmd = e.getActionCommand();
            switch(cmd) { // 메뉴 아이템의 종류 구분
               case "이미지 선택" :// 이미지 선택시
                  FileNameExtensionFilter filter = 
                  new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png");
               chooser.setFileFilter(filter);
      
               int ret = chooser.showOpenDialog(null);//열기창 열고 확인 버튼 눌렀는지 확인
               if(ret != JFileChooser.APPROVE_OPTION) {
                  JOptionPane.showMessageDialog(null, 
                        "파일을 선택하지 않았습니다", "경고", JOptionPane.WARNING_MESSAGE);// 파일 선택 안할시 메시지 출력
                  return;
               }
      
               String filePath = chooser.getSelectedFile().getPath();//파일 경로 가져오기 
               imgLabel.setIcon(new ImageIcon(filePath)); 
               imgLabel.setVisible(true);// 창을 화면에 출력
               pack(); // 이미지의 크기에 맞추어 프레임 크기 조절
               break;
            case "이미지 제거" :// 이미지 제거 클릭시
               imgLabel.setVisible(false);// 창을 화면에 제거
               break;
         }
      }
   }
   public static void main(String [] args) {
      new menu();
   }
}

