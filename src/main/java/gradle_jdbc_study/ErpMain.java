package gradle_jdbc_study;

import java.awt.EventQueue;

public class ErpMain {
	

	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println(System.getProperty("user.dir"));
					System.out.println("첫번째 수정");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	

	
	
	}
}