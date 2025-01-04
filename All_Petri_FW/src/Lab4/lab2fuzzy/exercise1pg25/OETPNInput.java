package Lab4.lab2fuzzy.exercise1pg25;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

//For OETPN-c 
public class OETPNInput {
	public static void main(String[] args) throws InterruptedException, IOException {
		File file = new File("D:\\All_Petri_FW_IntelliJ_N\\All_Petri_FW\\All_Petri_FW\\src\\Lab4\\Lab2Fuzzy\\Ex1Page25\\OETPNInput.txt");
		Files.deleteIfExists(file.toPath());
		FileWriter fw = new FileWriter(file.getPath());
		Float command = 0.55F;

		for (float i = 0; i < 150; i++) {
			if (i > 50)
				command = 0.35f;

			fw.write("r:" + command + "F\n");
		}
		fw.close();
		System.out.println("Done!");
	}
}
