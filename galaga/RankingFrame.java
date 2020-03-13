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

class RankingFrame extends JFrame{ //게임 시작할 때 랭킹 보여주는 클래스
	GalagaGame g;
	RankingFrame(GalagaGame g) {
		this.g = g;

		JP panel = new JP();
		JButton btn = new JButton("닫고 게임 시작");
		
		btn.setLocation(150, 500);
		btn.setSize(200,40);
	
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				g.new gameLoop().start(); //닫고 게임시작 버튼을 누르면 갤러그 게임시작
				
			}
		});
		
		panel.setLayout(null);
		panel.add(btn);
		add(panel);
		
		pack();
		this.setLocation(800,100); //프레임 위치 지정

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
		public void paint(Graphics g) { // 랭킹을 출력
			super.paint(g);
			g.setFont(new Font("휴먼엑스포", Font.PLAIN, 30));
			g.drawString("GalafaGame RANK", 90, 40);
			g.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
			g.setColor(Color.red);
			g.drawString("RANK", 60, 80);
			g.drawString("USER", 180, 80);
			g.drawString("SCORE", 320, 80);

			g.setFont(new Font("휴먼엑스포", Font.PLAIN, 17));
			g.setColor(Color.black);
			int y=80;
			 try{
		            File file = new File("ranking.txt"); 
		            FileReader reader = new FileReader(file);
		            BufferedReader buf = new BufferedReader(reader);
		            String line = "";
		           
		            while((line = buf.readLine()) != null){
		            	 StringTokenizer token = new StringTokenizer(line, ","); //파일에서 한줄 읽어와서 ,를 구분자로 해서 토큰으로 나눔
		            	 ArrayList<String> rank = new ArrayList<>(); 
		            	  while (token.hasMoreTokens()) {
		            	   rank.add(token.nextToken()); // 각 토큰을 rank배열에 저장
		            	  }  
		            	y+=30;
		            	g.drawString(rank.get(0), 60, y); //첫번째 토큰(등수)는 x좌표가 60 y좌표가 y인 곳에 출력
		            	g.drawString(rank.get(1), 180, y);// 유저이름 출력
		            	g.drawString(rank.get(2), 320, y);// 점수 출력
		     
		            }           
		            buf.close(); 
		        }catch (FileNotFoundException e) {
		        }catch(IOException e){
		            System.out.println(e);
		        }

		}
	
}
}
