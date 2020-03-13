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
   private JLabel label = new JLabel(); //�ؽ�Ʈ�� ���� label ����
   private JLabel imgLabel = new JLabel(); //�̹����� ���� label ����

   public menu() {
	   try {
		new FileOutputStream("ranking.txt").close();
	} catch (IOException e) {}
      setLayout(new FlowLayout());// �÷ο췹�̾ƿ��� �̿�
      getContentPane().add(label);
      getContentPane().add(imgLabel);
      createMenu();
      setSize(250,200);
      setVisible(true);
      
   }
   class choice extends JFrame implements ActionListener{ //�޴����� ������ ������ ������  

		JButton b1 = new JButton("�����ϱ�");	
		JButton b2 = new JButton("�׸��ϱ�"); 
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
			if(b==b1)	{ // �����ϱ⸦ ������
				 g= new GalagaGame(sb); //������ ���� ��ü �����ϰ�
				 g.startGame(); // ����
				}
			else if(b==b2) { //�׸��ϱ� ������
				g.endGame(); //�� ������ ���� 
				}
   }
	}	
   
   class gameActionListener implements ActionListener{ // ���� �޴��� �׼� ó�� 
	   
	    public void actionPerformed(ActionEvent e) {
	       switch(e.getActionCommand()) { 

	       case "������ ����":
	    	   new choice();
	          break; 

	          }
	    }
	}

   
   class textDialog extends JDialog{
   
      JTextField tf = new JTextField(10);//�ؽ�Ʈ�� �Է��� ���� ���� 
      JButton okButton = new JButton("OK");//�Է��� �ؽ�Ʈ�� ����ϰԲ� �� �� �ִ� ��ư ����
      
      public textDialog(JFrame frame, String title) {
         super(frame,title,true); 
         setLayout(new FlowLayout());
         add(tf); //�ؽ�Ʈ�ʵ� ����
         add(okButton); //��ư ����
         setSize(200, 100);

         okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               setVisible(false); // ��ư�� ������ â�� ����
               }
            });
         }

      public String getInput() {
         if(tf.getText().length() == 0) return null; // �ؽ�Ʈ�ʵ忡 text�� ������ �ΰ����� ��ȯ
         else return tf.getText(); //������ �ؽ�Ʈ�� ��ȯ
         }
      }
   
   private void createMenu() {
      
      JMenuBar mb = new JMenuBar();
      
      JMenuItem[] textItem = new JMenuItem [3]; //�ؽ�Ʈ �޴� ������ ����
      String[] textItems = {"�Է� �� ���","���� ����", "�ؽ�Ʈ ����"}; // �ؽ�Ʈ ������ �迭 ����
      JMenu textMenu = new JMenu("Text"); // �ؽ�Ʈ �޴� ����
      
      JMenuItem[] imageItem = new JMenuItem [2]; //�̹��� �޴� ������ ����
      String[] imageItems = {"�̹��� ����","�̹��� ����"};//�̹��� ������ �迭 ����
      JMenu imageMenu = new JMenu("Image");// �̹��� �޴� ����
      
      JMenuItem[] soundItem = new JMenuItem [3];//���� �޴� ������ ����
      String[] soundItems = {"����","����","�ٽ� ����"};//���� �޴� ������ �迭 ����
      JMenu soundMenu = new JMenu("Sound");//���� �޴� ����
      
      JMenuItem[] gameItem = new JMenuItem [1];
      String[] gameItems = {"������ ����"};
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
      // �޴����� �޴��ٿ� ����
 
      setJMenuBar(mb);//�޴��� ����
   }
   


   class SoundActionListener implements ActionListener{//���� �̺�Ʈ�� ó���� Ŭ����
      Clip clip;               //����� Ŭ���� ���� ������ ����
      SoundActionListener(){//������
              try {
                  clip = AudioSystem.getClip();
   //Clip�������̽��� Ŭ���� ����
                  File audioFile = new File("audio/��.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                  //����� ���Ϸκ��� ������� ���� ��Ʈ�� ��ü ����
                  clip.open(audioStream);
                  //����� Ŭ���� ����� ��Ʈ���� ����
               } catch (LineUnavailableException e1) { e1.printStackTrace(); }
               catch(UnsupportedAudioFileException e1) { e1.printStackTrace(); }
               catch (IOException e1) {e1.printStackTrace(); }}
   //�̷� ���ܰ� �߻��ϸ� ���� ������ ������
         public void actionPerformed(ActionEvent e) {//���� �̺�Ʈ�� �߻��ϸ�
            switch(e.getActionCommand()) { 
   //�̺�Ʈ�� �߻���Ų ��ü�� �ؽ�Ʈ����
            case "����": clip.start(); 
   //�����̸� clipŬ������ start()�޼ҵ带 ȣ��
               break;
            case "����": clip.stop();// ������ stop()�޼ҵ� ȣ��
               break;
   
            case "�ٽ� ����": clip.setFramePosition(0); 
   //�ٽ� �����̸� ���� ��������ġ�� 0���� �ٲٰ�
                     clip.start();//����
               break;   
               }
         }
   }

   
   class TextActionListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         String cmd = e.getActionCommand();
         switch(cmd) {// �޴� ������ ����
            case "�Է� �� ���" : 
               
               dialog.setVisible(true);//���̾�α� Ȱ��ȭ
               
               String text = dialog.getInput();//�ؽ�Ʈ�� ������ ���� ����

               if(text == null) return; //������ �ƹ� �Է��� ������� �׳� ��ȯ,
               label.setText(text);//������ label�� �Էµ� �ؽ�Ʈ ���
               
               break;
               
            case "���� ����" :
                Color selectedColor = 
                  JColorChooser.showDialog(null, "Color", 
                                             Color.YELLOW);// ������ ���� �� �� �ִ� colorchooser Ȱ��ȭ
            if(selectedColor != null)// ���ù��� �÷��� null���� �ƴ� ��쿡
               label.setForeground(selectedColor);// ���ý� �ؽ�Ʈ ���� ����

               break;
            case "�ؽ�Ʈ ����" :
               label.setText("");// �ؽ�Ʈ�� �������� ä��
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
            switch(cmd) { // �޴� �������� ���� ����
               case "�̹��� ����" :// �̹��� ���ý�
                  FileNameExtensionFilter filter = 
                  new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png");
               chooser.setFileFilter(filter);
      
               int ret = chooser.showOpenDialog(null);//����â ���� Ȯ�� ��ư �������� Ȯ��
               if(ret != JFileChooser.APPROVE_OPTION) {
                  JOptionPane.showMessageDialog(null, 
                        "������ �������� �ʾҽ��ϴ�", "���", JOptionPane.WARNING_MESSAGE);// ���� ���� ���ҽ� �޽��� ���
                  return;
               }
      
               String filePath = chooser.getSelectedFile().getPath();//���� ��� �������� 
               imgLabel.setIcon(new ImageIcon(filePath)); 
               imgLabel.setVisible(true);// â�� ȭ�鿡 ���
               pack(); // �̹����� ũ�⿡ ���߾� ������ ũ�� ����
               break;
            case "�̹��� ����" :// �̹��� ���� Ŭ����
               imgLabel.setVisible(false);// â�� ȭ�鿡 ����
               break;
         }
      }
   }
   public static void main(String [] args) {
      new menu();
   }
}

