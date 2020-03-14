
/*�Ϲ� ����� ���� ���� ����
  �Ϲ� ������ ��쿡�� 2+3�� ���� �� ���ڸ� ���
  ���� ������ ��쿡�� 2*3+3�� ���� ������ �Է��ϸ� ���� ��갡�� */

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class stack {				//���� �ӽ� ������ ������ ����
	int top = -1; 			//���� �ֱٿ� ���Ե� �����͸� ����Ű�� ����
	String[] stack_arr = new String[50];//50���� ���� ������ �� �ִ� ���� �迭�� ����

	void push(String c) {			//String Ÿ���� ������ ���ÿ� ������ ��
		stack_arr[++top]=c;	} 
//top�� ���� �ϳ� ������Ų ���� top�� ����Ű�� ���� c�� ����
	String pop() {			//���ÿ��� ���� �ֱٿ� ���� ���� �ϳ� �� �� ���
		return stack_arr[top--]; //top�� ����Ű�� ���� ��ġ���� ���� �ϳ� �� �� �� top�� ���ҽ�Ŵ
	}
	String peek(){				//���� ���� �ֱٿ� ���� ���� �� �� �˷���
		return stack_arr[top];	//top�� ����Ű�� ���� ���� ��ȯ
	}
}

class toPostfix extends stack{ 	//��ǻ�Ͱ� ����� ���� �� �� �ֵ���//����ڰ� ��ư���� �Է��� ������(2+3)�� ������(23+)�� ��ȯ�� �� �ִ� Ŭ����
	String infix = new String();		//����ڰ� �Է��� �������� �޾ƿ��� ����
	String postfix = new String();	//���������� ��ȯ�� ���� ������ ����
	stack s = new stack();		//���� �ӽ� ������ ���� ���� ��ü�� ����

	toPostfix(String infix){ 
		this.infix = infix; 
}				//�������� �޾ƿͼ� ��ü���� infix�� �������ִ� ������

	int priority(String op) {		//�������� �켱������ ��ȯ���ִ� �޼ҵ�
		switch(op) { 
		case "*": case "/": 
			return 2;			//�����ڰ� *�� /�� �켱������ ����
		case "+": case "-": 
			return 1;			//�����ڰ� +�� -�� �켱������ ����
		}
		return 0;
	}
	

	String itop() {			//�������� ���������� ��ȯ���ִ� �Լ�
		for(int i=0;i<infix.length();i++) {	//infix�� ���̸�ŭ �ݺ� 
		String op = infix.substring(i,i+1); 
//infix�� ���� ó������ �ϳ��� �� op�� ����
//infix=2+3�̶�� ù �ݺ� ���� 2, �ι�° �ݺ������� +�� �������� ��
		switch(op) {//op�� ���������� �ǿ��������� �����ϰ� �ű⿡ ���� ������ ����
		case "+":
		case "-":
		case "*":
		case "/":				//op�� �����ڶ��
			postfix += " ";	
//�� �ڸ� �� �̻��� �ǿ����ڸ� �ٷ�� ���� �������� ����
			while(s.top!=-1 && priority(op)<=priority(s.peek())) {
					postfix += s.pop()+" ";}
			//���ÿ��� ������ ���ų� ���� �켱������ ���� �����ڸ� ã�� ������ 
			//���� pop�ؼ� �����Ŀ� �߰�
			s.push(op); 
//������ ���� �켱������ ���� �����ڸ� ã���� ���� ���ÿ� Ǫ��
			break;
		case " ":				//�����̸� �ƹ��͵� ����
			break;
		default:				//���ڸ�
			postfix+=op;			//�����Ŀ� �߰�
			break;
			}
		}
		while(s.top!=-1) 	
			postfix+=" "+s.pop();
//�ݺ����� �������� ���ÿ� �ִ� ��� �����ڵ��� pop�ؼ� �����Ŀ� ����
		return postfix; //�ϼ��� �������� ��ȯ
				 
	}
	
}



class calculation { 				//���� �޾ƿͼ� ������ִ� Ŭ���� 
	int answer;				//��� ����� ����
	String[] arr;				//�������� ������ �������� ������ ����
	calculation(String sic){		
		arr = sic.split(" ");}
 //���� �޾ƿͼ� ������ �������� ������ ��Ʈ�� �迭�� ����
//11 2 +�� ������ arr[0]��11, arr[1]�� �̷� ������ ���� 
	


	String calulate_postfix() { 			//�������� ������ִ� �޼ҵ�
		stack s = new stack();		//������ ����
		for(String op:arr) {			//arr�� �ִ� �� ��� �ϳ��ϳ��� ����
if(op.equals("+")||op.equals("-")||op.equals("*")||op.equals("/")) {
							//�� ��Ұ� �����ڸ�
		int a=Integer.parseInt(s.pop()); 
 		int b=Integer.parseInt(s.pop());	//���ÿ��� �� ���� ���� �� �ͼ�
				switch(op) { 
				case "+":			//op�� +�� a�� b�� ����
					answer=a+b;	
					break;
				case "-":
					answer=b-a;
					break;
				case "*":
					answer=b*a;
					break;
				case "/":
					answer=b/a;
					break;
				}			//�� �����ڿ� ���� ������ ��
				s.push(Integer.toString(answer)); 
//������ ������� ���ÿ� �����ϰ� ���� �ݺ�����
					
		}
			else				//�� ��Ұ� ���ڸ�
				s.push(op);		//���ÿ� ����
	}
		return Integer.toString(answer);//��� ������ ���� �� ���� ����� ��ȯ
}
	
	String calculate_infix() {	//�ǿ����ڰ� �� ���� �������� ������ִ� �޼ҵ�
		int a = Integer.parseInt(arr[0]);//����� ���� 0,2��° ���� ���ڷ� ��ȯ
		int b = Integer.parseInt(arr[2]);//2+3�̿����� 2�� 3�� int�� ��ȯ�ϴ� ��
		if(arr[1].equals("+"))		
			answer=a+b; 
		else if(arr[1].equals("-"))
			answer=a-b;
		else if(arr[1].equals("*"))
			answer=a*b;
		else if(arr[1].equals("/"))
			answer=a/b;			//�� �����ڿ� �´� ������ ����
		return Integer.toString(answer);
		}
}




class choice extends JFrame implements ActionListener{
		//ó���� �Ϲ� ���⸦ ������ ���� ���� ���⸦ ������ �� �� �����ϴ� â
 
	JButton b1 = new JButton("�Ϲ� ����");	//�Ϲݰ��� ��ư
	JButton b2 = new JButton("���� ����");	//���İ��� ��ư
	choice(){
		setLayout(new FlowLayout());	//�� �������� ��ġ�� flowlayout���� ����
		setSize(300,100);		//������ ����
		add(b1);			
		b1.addActionListener(this);	//b1��ư�� �׼� ������ �߰�
		add(b2);
		b2.addActionListener(this);	//b2��ư�� �׼� ������ �߰�
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {//�׼��� �߻��ϸ� ȣ��
		JButton b = (JButton)e.getSource();//�׼��� �߻���Ų ��ư�� ������
		if(b==b1)				//�� ��ư�� b1�̸�
			new Calculator(1);	//1�� �Ѱ��ְ� calualtor ��ü�� ����
		else if(b==b2)
			new Calculator(2);}}	//2�� �Ѱ��ְ� calualtor ��ü�� ����
 
class Calculator extends JFrame implements MouseListener{
					//���� ������ ������ִ� Ŭ����
	int num;			//�� ���� 1�̸� �Ϲݰ�� 2�� ���İ���� ��	
String infix;			//������
	JTextField show_data = new JTextField(15); //���� �����̳� ���� �����ִ� ����

	String[] arr = {"BACKSPACE"," "," ","/","7","8","9","*","4","5","6","-","1","2","3","+"," ","0"," ","="};			//��ư�� ���� ����
	JButton[] Buttons = new JButton[20];		//��ư 20�� ����

	Calculator(int n){				//������ 
		num=n;				//�� ���� 1�̸� �Ϲݰ�� 2�� ���İ���� ��
		setSize(500,500);		
		add(show_data,BorderLayout.NORTH);	//��ġ�� �������̾ƿ����� �� 
JPanel p2 = new JPanel();		//20���� ��ư�� ���⿡ ���� �� 
		p2.setLayout(new GridLayout(5,4,3,3)); 
//�г� p2�� ������Ʈ���� ��ġ�� 5�� 4�� ���ڷ� ��
	
		for(int i=0;i<20;i++) {	
			Buttons[i] = new JButton(arr[i]);	
//i��° ��ư�� ����鼭 �� ��ư�� arr[i]��°�� �ִ� ���ڿ��� �������
			p2.add(Buttons[i]);				//��ư�� p2�� ����
			Buttons[i].addMouseListener(this); }
							//�� ��ư�� �̺�Ʈ ������ �߰�
		add(p2);				//p2�� �����ӿ� ����
		
		show_data.requestFocus();//Ű�����ʸ� ����ϱ����� show_data�� ��Ŀ���� ��
		show_data.setFocusable(true);
		show_data.addKeyListener(new KeyListener() {
							//����Ŭ������ Ű �̺�Ʈ�� ó��
			public void keyPressed(KeyEvent e) { //Ű�� ������ ����
				int keycode = e.getKeyCode();//���� Ű�� �� �� ������
				if( keycode == KeyEvent.VK_ENTER ) {
								//������ Ű�� ���͸�
					infix = show_data.getText();
//���� show_data�� �ִ� ������ infix�� �����ؼ�
					show_dap(infix);}	//���� ����ϰ� ������
				}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		
		setVisible(true);}
void show_dap(String infix) {				//���� ������ִ� �޼ҵ�
		if(num==1) {					//�Ϲ� ������ ��쿡��
			calculation dap = new calculation(infix);
			show_data.setText(dap.calculate_infix()); }
//�������� caluation Ŭ������ �����ϰ�
//calulate_infix()������ ����ϰ� ���� ������
		
		else if(num==2) {				//���� ������ ��쿡��
			toPostfix obj = new toPostfix(infix); 
			calculation dap = new calculation(obj.itop());
						//�������� ���������� �ٲٰ� �װ� �����ڸ� 
//�̿��ؼ� calculation��ü�� �����ϰ�
			show_data.setText(dap.calulate_postfix()); }
									//�������� ���
	}


	public void mouseClicked(MouseEvent e) {			//���콺�� Ŭ���Ǹ�
		String str= ((JButton) e.getSource()).getText();
						//Ŭ���� ��ư�� �� �ִ� ���ڿ��� str�� ����
		infix = show_data.getText();	
		switch(str) {				//Ŭ���� ��ư�� �� �ִ� ���ڿ���
		case "BACKSPACE":			//�齺���̽��̸�
			infix = infix.substring(0,infix.length()-1);
				//infix���� �� �� ���� �ϳ��� �����ϰ� infix�� �����ϰ�
			show_data.setText(infix);		//������
			break;
	
		case "=":				//=�� Ŭ���Ǹ�
			show_dap(infix);		//���� ����ϰ� ������
			break;
				
		case "+": case "-": 			
		case "*": case "/":			//�����ڰ� Ŭ���Ǹ�
			infix += " "+str+" ";	
//�� ���� ������ �߰��ϰ� infix�� �߰��ؼ�
			show_data.setText(infix); //���� ������
			break;
			
		default:				//���ڰ� Ŭ���Ǹ�
			infix += str;			//infix�� �߰��ؼ� 
			show_data.setText(infix);	//���� ������
			break;}			
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	
}
public class cal {
	public static void main(String args[]) {
		choice f1 = new choice();		//choice ��ü�� ����
	}
}
