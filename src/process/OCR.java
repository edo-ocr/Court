package process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class OCR
{
  private static final String EOL = System.getProperty("line.separator");

  public static String recognizeText(File imageFile, String PageSetMode) {
    File outputFile = new File(imageFile.getParentFile(), imageFile.getName().replaceAll("_c.*", "_t"));
    StringBuffer strB = new StringBuffer();

    String command = "tesseract " + imageFile.getAbsolutePath() + 
      " " + outputFile.getAbsolutePath() + " " + 
      "-l" + " chi_sim " + PageSetMode;
    try
    {
      Process process = Runtime.getRuntime().exec(command);

      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()), 4096);
      String s;
      while ((s = bufferedReader.readLine()) != null);
      BufferedReader in = new BufferedReader(new InputStreamReader(
        new FileInputStream(outputFile.getAbsolutePath() + ".txt"), "UTF-8"));
      String str;
      while ((str = in.readLine()) != null)
      {
//        String str;
        strB.append(str).append(EOL);
      }
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    new File(outputFile.getAbsolutePath() + ".txt").delete();
    String str = strB.toString().replaceAll(" |\r|\n", "");
    return str;
  }
  public static void main(String[] args){
	  File file = new File("001.jpg");
	  System.out.println(recognizeText(file, ""));
  }
}