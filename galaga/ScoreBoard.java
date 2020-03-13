import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class ScoreBoard extends JDialog { //������ ó���� Ŭ����
	private JLabel NameLabel = new JLabel("PLAYER : ");
	private JTextField Nametf = new JTextField(10);//�÷��̾� �̸��� ���� �ؽ�Ʈ�ʵ�
	private JButton okButton = new JButton("OK");
	int s;
	ArrayList<Integer> score = new ArrayList<>(); // ���� ����
	ArrayList<String> nickname = new ArrayList<>(); //�г��� ����
	public ScoreBoard() {
		
		setSize(400,100);
		setLayout(new FlowLayout());
		add(NameLabel);
		add(Nametf);
	    add(okButton);
		setModal(true);
		
		okButton.addActionListener(new ActionListener() {  //ok�� ������
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				score.add(s);
				if(Nametf.getText() == null) {nickname.add("�̸�����");}
				else {
				nickname.add(Nametf.getText()+" ");} //����ڰ� �Է��� �г��� ����
				sort(); 
				
				}
		});

	}
	void set_s(int s) {
		this.s = s;
	}
	
	void sort() { //������ ������������ ����
		int size = score.size();
	
		for(int i=0; i<size; i++) {
			for(int j=0; j<size-1; j++) {
				if(score.get(j) < score.get(j+1)) {
					int tmp = score.get(j);
					String stmp = nickname.get(j);
					
					score.set(j,score.get(j+1));
					score.set(j+1,tmp);
					
					nickname.set(j,nickname.get(j+1));
					nickname.set(j+1,stmp);}  }
		
			}
		
		write_file();
	}
	
	void write_file() {
		 File file = new File("ranking.txt");
		 try{
	            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
	            if(file.isFile() && file.canWrite()){
	            	
	            	for(int i=0;i<10;i++) {  //�ִ� 10������� ����
	            		if(score.size()==i) break; // �÷��̸� 10�� ���Ϸ� ���� �� �ε��� �����÷ο� ����
	                bufferedWriter.write(i+1+"st"+","+nickname.get(i)+","+score.get(i)); // ���Ͽ� ����� ��
	                bufferedWriter.newLine();
	            	}
	            	
	                bufferedWriter.close();
	            }
	        }catch (IOException e) {
	            System.out.println(e);
	        }


	}
	
	
}

