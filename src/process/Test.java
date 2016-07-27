package process;

import java.io.File;

public class Test {
	String[] args = new String[2];
	
	public static void main(String[] args){
		long time = System.currentTimeMillis();
		File dir = new File("test");
		String[] strings = new String[2];
		for (File file : dir.listFiles()) {
			long time1 = System.currentTimeMillis();
			strings[0] = file.getPath();
			strings[1] = "1.txt";
			Main.main(strings);
			long time2 = System.currentTimeMillis() - time1;
			System.out.println(time2);
		}
		time = System.currentTimeMillis() - time;
		System.out.println(time);
	}
}
