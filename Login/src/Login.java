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
		System.out.print("��ȣ�� �Է��Ͻʽÿ�: \n");
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
					System.out.println("�α��� ����!");
					a=true;
					break;}
				}
			
			if(!a)
				System.out.println("�α��ν���!");
			break;
		case 3:
			for(user obj : list) {
				System.out.println("{"+obj.id+","+obj.password+"}");
			}
			break;
		case 4:
			return;
		default:
			System.out.println("��ȣ�� �ٽ� �������ּ���");
			continue;
		}
		
		}
		

	}

}
