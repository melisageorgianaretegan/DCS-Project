package Lab4.lab2fuzzy.exercise1pg7;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class ComparatorInput {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		File file = new File("D:\\All_Petri_FW_IntelliJ_N\\All_Petri_FW\\All_Petri_FW\\src\\Lab4\\Lab2Fuzzy\\Ex1Page7\\comparator.txt");
		Files.deleteIfExists(file.toPath());
		FileWriter fw = new FileWriter(file.getPath());
		double f1, f2;
		for (float i = 0; i < 500; i++) {
			f1 = Math.sin(i);
			f2 = Math.sin(i);
			fw.write("P0:"+f1+"F"+","+"P1:"+f2+"F\n");
		}
		fw.close();
		System.out.println("Done!");
	}
}
