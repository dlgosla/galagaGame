import java.util.*;
import java.util.ArrayList; 

class user{
	String id,password;
	
	public user(String id, String password){
		this.id=id;
		this.password=password;
	}
	
	
};

public class Login {

	public static void main(String[] args) {
		
		String id,pass;
		int choice, index=0;
		ArrayList<user> list = new ArrayList<user>();
		int i;
		while(true) {
		System.out.print("================\n");
		System.out.print("1.Sign up \n");
		System.out.print("2.Login \n");
		System.out.print("3.Print All Users\n");
		System.out.print("4.Exit\n");
		System.out.print("================\n");
		Scanner input = new Scanner(System.in);
		System.out.print("번호를 입력하십시오: \n");
		choice=input.nextInt();
		
		switch(choice){
		case 1:
			input.nextLine();
			System.out.print("id: ");
			id=input.nextLine();
			System.out.print("password: ");
			pass=input.nextLine();
			list.add(new user(id,pass));
			index++;
			break;
		case 2:
			
			System.out.print("id: ");
			id=input.next();
			System.out.print("password: " );
			pass=input.next();
			boolean a=false;
			
			for(user obj:list) {
				if(id.equals(obj.id) && pass.equals(obj.password)) {
					System.out.println("로그인 성공!");
					a=true;
					break;}
				}
			
			if(!a)
				System.out.println("로그인실패!");
			break;
		case 3:
			for(user obj : list) {
				System.out.println("{"+obj.id+","+obj.password+"}");
			}
			break;
		case 4:
			return;
		default:
			System.out.println("번호를 다시 선택해주세요");
			continue;
		}
		
		}
		

	}

}
