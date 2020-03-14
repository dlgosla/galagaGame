
/*일반 계산기와 수식 계산기 구현
  일반 계산기의 경우에는 2+3과 같이 두 숫자만 계산
  수식 계산기의 경우에는 2*3+3과 같은 수식을 입력하면 전부 계산가능 */

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class stack {				//값을 임시 저장할 스택을 만듦
	int top = -1; 			//가장 최근에 삽입된 데이터를 가리키는 변수
	String[] stack_arr = new String[50];//50개의 값을 저장할 수 있는 스택 배열을 선언

	void push(String c) {			//String 타입의 변수를 스택에 저장할 때
		stack_arr[++top]=c;	} 
//top의 값을 하나 증가시킨 다음 top이 가리키는 곳에 c를 삽입
	String pop() {			//스택에서 가장 최근에 넣은 값을 하나 뺄 때 사용
		return stack_arr[top--]; //top이 가리키는 스택 위치에서 값을 하나 빼 온 후 top을 감소시킴
	}
	String peek(){				//현재 가장 최근에 넣은 값이 뭔 지 알려줌
		return stack_arr[top];	//top이 가리키는 곳의 값을 반환
	}
}

class toPostfix extends stack{ 	//컴퓨터가 계산을 쉽게 할 수 있도록//사용자가 버튼으로 입력한 중위식(2+3)을 후위식(23+)로 변환할 수 있는 클래스
	String infix = new String();		//사용자가 입력한 중위식을 받아오는 변수
	String postfix = new String();	//후위식으로 변환된 식을 저장할 변수
	stack s = new stack();		//값의 임시 저장을 위해 스택 객체를 만듦

	toPostfix(String infix){ 
		this.infix = infix; 
}				//중위식을 받아와서 객체변수 infix에 저장해주는 생성자

	int priority(String op) {		//연산자의 우선순위를 반환해주는 메소드
		switch(op) { 
		case "*": case "/": 
			return 2;			//연산자가 *나 /면 우선순위가 높음
		case "+": case "-": 
			return 1;			//연산자가 +나 -면 우선순위가 낮음
		}
		return 0;
	}
	

	String itop() {			//중위식을 후위식으로 변환해주는 함수
		for(int i=0;i<infix.length();i++) {	//infix의 길이만큼 반복 
		String op = infix.substring(i,i+1); 
//infix의 값을 처음부터 하나씩 뜯어서 op에 저장
//infix=2+3이라면 첫 반복 에는 2, 두번째 반복에서는 +를 가져오는 식
		switch(op) {//op가 연산자인지 피연산자인지 구분하고 거기에 맞춰 행위를 수행
		case "+":
		case "-":
		case "*":
		case "/":				//op가 연산자라면
			postfix += " ";	
//두 자리 수 이상의 피연산자를 다루기 위해 공백으로 구분
			while(s.top!=-1 && priority(op)<=priority(s.peek())) {
					postfix += s.pop()+" ";}
			//스택에서 나보다 높거나 같은 우선순위를 가진 연산자를 찾을 때까지 
			//값을 pop해서 후위식에 추가
			s.push(op); 
//나보다 높은 우선순위를 가진 연산자를 찾으면 나를 스택에 푸쉬
			break;
		case " ":				//공백이면 아무것도 안함
			break;
		default:				//숫자면
			postfix+=op;			//후위식에 추가
			break;
			}
		}
		while(s.top!=-1) 	
			postfix+=" "+s.pop();
//반복문이 끝났으면 스택에 있는 모든 연산자들을 pop해서 후위식에 저장
		return postfix; //완성된 후위식을 반환
				 
	}
	
}



class calculation { 				//식을 받아와서 계산해주는 클래스 
	int answer;				//계산 결과를 저장
	String[] arr;				//후위식을 공백을 기준으로 나눠서 저장
	calculation(String sic){		
		arr = sic.split(" ");}
 //식을 받아와서 공백을 기준으로 나눠서 스트링 배열을 만듦
//11 2 +가 왔으면 arr[0]에11, arr[1]에 이런 식으로 저장 
	


	String calulate_postfix() { 			//후위식을 계산해주는 메소드
		stack s = new stack();		//스택을 생성
		for(String op:arr) {			//arr에 있는 각 요소 하나하나에 대해
if(op.equals("+")||op.equals("-")||op.equals("*")||op.equals("/")) {
							//그 요소가 연산자면
		int a=Integer.parseInt(s.pop()); 
 		int b=Integer.parseInt(s.pop());	//스택에서 두 개의 값을 빼 와서
				switch(op) { 
				case "+":			//op가 +면 a와 b를 더함
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
				}			//각 연산자에 맞춰 연산을 함
				s.push(Integer.toString(answer)); 
//연산의 결과값을 스택에 저장하고 다음 반복시작
					
		}
			else				//그 요소가 숫자면
				s.push(op);		//스택에 넣음
	}
		return Integer.toString(answer);//모든 연산이 끝난 후 나온 결과를 반환
}
	
	String calculate_infix() {	//피연산자가 두 개인 중위식을 계산해주는 메소드
		int a = Integer.parseInt(arr[0]);//계산을 위해 0,2번째 값을 숫자로 변환
		int b = Integer.parseInt(arr[2]);//2+3이왔으면 2와 3을 int로 변환하는 식
		if(arr[1].equals("+"))		
			answer=a+b; 
		else if(arr[1].equals("-"))
			answer=a-b;
		else if(arr[1].equals("*"))
			answer=a*b;
		else if(arr[1].equals("/"))
			answer=a/b;			//각 연산자에 맞는 연산을 진행
		return Integer.toString(answer);
		}
}




class choice extends JFrame implements ActionListener{
		//처음에 일반 계산기를 선택할 건지 수식 계산기를 선택할 건 지 결정하는 창
 
	JButton b1 = new JButton("일반 계산기");	//일반계산기 버튼
	JButton b2 = new JButton("수식 계산기");	//수식계산기 버튼
	choice(){
		setLayout(new FlowLayout());	//이 프레임의 배치를 flowlayout으로 설정
		setSize(300,100);		//사이즈 설정
		add(b1);			
		b1.addActionListener(this);	//b1버튼에 액션 리스너 추가
		add(b2);
		b2.addActionListener(this);	//b2버튼에 액션 리스너 추가
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {//액션이 발생하면 호출
		JButton b = (JButton)e.getSource();//액션을 발생시킨 버튼을 가져옴
		if(b==b1)				//그 버튼이 b1이면
			new Calculator(1);	//1을 넘겨주고 calualtor 객체를 생성
		else if(b==b2)
			new Calculator(2);}}	//2를 넘겨주고 calualtor 객체를 생성
 
class Calculator extends JFrame implements MouseListener{
					//계산기 외형을 만들어주는 클래스
	int num;			//이 값이 1이면 일반계산 2면 수식계산을 함	
String infix;			//중위식
	JTextField show_data = new JTextField(15); //위에 수식이나 답을 보여주는 공간

	String[] arr = {"BACKSPACE"," "," ","/","7","8","9","*","4","5","6","-","1","2","3","+"," ","0"," ","="};			//버튼에 넣을 값들
	JButton[] Buttons = new JButton[20];		//버튼 20개 생성

	Calculator(int n){				//생성자 
		num=n;				//이 값이 1이면 일반계산 2면 수식계산을 함
		setSize(500,500);		
		add(show_data,BorderLayout.NORTH);	//배치를 보더레이아웃으로 함 
JPanel p2 = new JPanel();		//20개의 버튼을 여기에 붙일 것 
		p2.setLayout(new GridLayout(5,4,3,3)); 
//패널 p2의 컴포넌트들의 배치를 5행 4열 격자로 함
	
		for(int i=0;i<20;i++) {	
			Buttons[i] = new JButton(arr[i]);	
//i번째 버튼을 만들면서 그 버튼에 arr[i]번째에 있는 문자열을 집어넣음
			p2.add(Buttons[i]);				//버튼을 p2에 붙임
			Buttons[i].addMouseListener(this); }
							//각 버튼에 이벤트 리스너 추가
		add(p2);				//p2를 프레임에 붙임
		
		show_data.requestFocus();//키리스너를 사용하기위해 show_data에 포커스를 줌
		show_data.setFocusable(true);
		show_data.addKeyListener(new KeyListener() {
							//무명클래스가 키 이벤트를 처리
			public void keyPressed(KeyEvent e) { //키가 눌리면 실행
				int keycode = e.getKeyCode();//눌린 키가 뭔 지 가져옴
				if( keycode == KeyEvent.VK_ENTER ) {
								//눌려진 키가 엔터면
					infix = show_data.getText();
//현재 show_data에 있는 수식을 infix에 저장해서
					show_dap(infix);}	//답을 계산하고 보여줌
				}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		
		setVisible(true);}
void show_dap(String infix) {				//답을 계산해주는 메소드
		if(num==1) {					//일반 계산기의 경우에는
			calculation dap = new calculation(infix);
			show_data.setText(dap.calculate_infix()); }
//중위식을 caluation 클래스에 전달하고
//calulate_infix()수행해 계산하고 답을 보여줌
		
		else if(num==2) {				//수식 계산기의 경우에는
			toPostfix obj = new toPostfix(infix); 
			calculation dap = new calculation(obj.itop());
						//중위식을 후위식으로 바꾸고 그걸 생성자를 
//이용해서 calculation객체에 전달하고
			show_data.setText(dap.calulate_postfix()); }
									//후위식을 계산
	}


	public void mouseClicked(MouseEvent e) {			//마우스가 클릭되면
		String str= ((JButton) e.getSource()).getText();
						//클릭된 버튼에 써 있는 문자열을 str에 저장
		infix = show_data.getText();	
		switch(str) {				//클릭된 버튼에 써 있는 문자열이
		case "BACKSPACE":			//백스페이스이면
			infix = infix.substring(0,infix.length()-1);
				//infix에서 맨 뒤 글자 하나를 제외하고 infix에 저장하고
			show_data.setText(infix);		//보여줌
			break;
	
		case "=":				//=이 클릭되면
			show_dap(infix);		//답을 계산하고 보여줌
			break;
				
		case "+": case "-": 			
		case "*": case "/":			//연산자가 클릭되면
			infix += " "+str+" ";	
//양 옆에 공백을 추가하고 infix에 추가해서
			show_data.setText(infix); //식을 보여줌
			break;
			
		default:				//숫자가 클릭되면
			infix += str;			//infix에 추가해서 
			show_data.setText(infix);	//식을 보여줌
			break;}			
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	
}
public class cal {
	public static void main(String args[]) {
		choice f1 = new choice();		//choice 객체를 생성
	}
}
