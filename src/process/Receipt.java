package process;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Receipt
  implements FileCode
{
  public static boolean isWhite(int colorInt)
  {
    Color color = new Color(colorInt);
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    if (r + g + b > 630) {
      return true;
    }
    return false;
  }

  public static boolean isGreen(int colorInt) {
    Color color = new Color(colorInt);
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    if ((r < 120) && (g > 135) && (g < 230) && (b > 140) && (b < 230)) {
      return true;
    }
    return false;
  }

  public static boolean isBlue(int colorInt)
  {
    Color color = new Color(colorInt);
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    if ((r > 100) && (g > 120) && (g < 190) && (b > 160)) {
      return true;
    }
    return false;
  }

  public static boolean isBOrange(int colorInt)
  {
    Color color = new Color(colorInt);
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    if ((r > 220) && (g > 110) && (g < 190) && (b < 120)) {
      return true;
    }
    return false;
  }

  public static boolean isOrange(int colorInt)
  {
    Color color = new Color(colorInt);
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    if ((r > 230) && (g > 150) && (g < 180) && (b > 100) && (b < 130)) {
      return true;
    }
    return false;
  }

  public static String check(BufferedImage img)
  {
    int h = img.getHeight();
    int w = img.getWidth();
    int total = h * w;
    float BCount = 0.0F;
    float GCount = 0.0F;
    float OCount = 0.0F;
    float BOCount = 0.0F;
    int[] pixels = new int[h * w];
    img.getRGB(0, 0, w, h, pixels, 0, w);
    for (int i = 0; i < h * w; i++) {
      if (isBlue(pixels[i])) {
        BCount += 1.0F;
      }
      if (isGreen(pixels[i])) {
        GCount += 1.0F;
      }
      if (isOrange(pixels[i])) {
        OCount += 1.0F;
      }
      if (isBOrange(pixels[i])) {
        BOCount += 1.0F;
      }
    }
    if (((BCount / total > 0.03D) && (BOCount / total > 0.0012D)) || (OCount / total > 0.03D))
      return "4";
    if (GCount / total > 0.003D) {
      return "3";
    }
    return "";
  }

  public static void main(String[] args)
  {
    File dir = new File("test");
    for (File file : dir.listFiles()) {
      System.out.println(file.getName());

      float count = 0.0F;
      try {
        BufferedImage img = ImageIO.read(file);
        int h = img.getHeight();
        int w = img.getWidth();

        int[] pixels = new int[h * w];
        img.getRGB(0, 0, w, h, pixels, 0, w);
        for (int i = 0; i < h * w; i++) {
          if (isBOrange(pixels[i]))
          {
            count += 1.0F;
          }

        }

        System.out.println(count + "___" + h * w + "___" + count / (h * w));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}