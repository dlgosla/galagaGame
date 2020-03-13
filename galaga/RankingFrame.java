import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class RankingFrame extends JFrame{ //���� ������ �� ��ŷ �����ִ� Ŭ����
	GalagaGame g;
	RankingFrame(GalagaGame g) {
		this.g = g;

		JP panel = new JP();
		JButton btn = new JButton("�ݰ� ���� ����");
		
		btn.setLocation(150, 500);
		btn.setSize(200,40);
	
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				g.new gameLoop().start(); //�ݰ� ���ӽ��� ��ư�� ������ ������ ���ӽ���
				
			}
		});
		
		panel.setLayout(null);
		panel.add(btn);
		add(panel);
		
		pack();
		this.setLocation(800,100); //������ ��ġ ����

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
class JP extends JPanel{
		
		public JP(){
			setPreferredSize(new Dimension(500, 550)); //
			setBackground(Color.yellow);
		}
		@Override
		public void paint(Graphics g) { // ��ŷ�� ���
			super.paint(g);
			g.setFont(new Font("�޸տ�����", Font.PLAIN, 30));
			g.drawString("GalafaGame RANK", 90, 40);
			g.setFont(new Font("�޸տ�����", Font.PLAIN, 20));
			g.setColor(Color.red);
			g.drawString("RANK", 60, 80);
			g.drawString("USER", 180, 80);
			g.drawString("SCORE", 320, 80);

			g.setFont(new Font("�޸տ�����", Font.PLAIN, 17));
			g.setColor(Color.black);
			int y=80;
			 try{
		            File file = new File("ranking.txt"); 
		            FileReader reader = new FileReader(file);
		            BufferedReader buf = new BufferedReader(reader);
		            String line = "";
		           
		            while((line = buf.readLine()) != null){
		            	 StringTokenizer token = new StringTokenizer(line, ","); //���Ͽ��� ���� �о�ͼ� ,�� �����ڷ� �ؼ� ��ū���� ����
		            	 ArrayList<String> rank = new ArrayList<>(); 
		            	  while (token.hasMoreTokens()) {
		            	   rank.add(token.nextToken()); // �� ��ū�� rank�迭�� ����
		            	  }  
		            	y+=30;
		            	g.drawString(rank.get(0), 60, y); //ù��° ��ū(���)�� x��ǥ�� 60 y��ǥ�� y�� ���� ���
		            	g.drawString(rank.get(1), 180, y);// �����̸� ���
		            	g.drawString(rank.get(2), 320, y);// ���� ���
		     
		            }           
		            buf.close(); 
		        }catch (FileNotFoundException e) {
		        }catch(IOException e){
		            System.out.println(e);
		        }

		}
	
}
}
