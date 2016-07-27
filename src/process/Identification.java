package process;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Identification
{
  public static boolean check(BufferedImage img)
  {
    int h = img.getHeight();
    int w = img.getWidth();
    img = img.getSubimage((int)(w * 0.05D), (int)(h * 0.05D), (int)(w * 0.85D), (int)(h * 0.86D));
    byte[][] pixels = Utils.OSTUbinarition(img);
    img = Utils.rectify(pixels);
    pixels = Utils.binarition(img, 750);
    h = img.getHeight();
    w = img.getWidth();
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        if ((pixels[y][x] != 0) && (x * y != 0) && (x != w - 1) && (y != h - 1))
        {
          if (pixels[(y - 1)][x] + pixels[(y + 1)][x] + pixels[y][(x - 1)] + 
            pixels[y][(x + 1)] < 2) {
            pixels[y][x] = 0;
          }
        }
      }
    }

    img = Utils.pixels2Image(pixels);

    int blockH = h / 25;
    int[] row = Utils.Tohisto(img, "row");
    ArrayList<Integer> seperator = new ArrayList<>();
    for (int y = 0; y < h; y++) {
      int up = 0;
      int down = 0;
      for (int i = y; i < h - 1; i++) {
        if ((row[i] > 110) && (row[(i + 1)] > 110)) {
          y = i + 1;
          up = i;
          break;
        }
        if (i == h - 2) {
          y = h;
        }
      }
      for (int i = y; i < h - 1; i++) {
        if (((row[i] < 20) && (row[(i + 1)] < 20)) || (i == h - 2)) {
          y = i;
          down = i;
          break;
        }
      }
      if (down - up > blockH) {
        seperator.add(Integer.valueOf(up));
        seperator.add(Integer.valueOf(down));
      }
      if (seperator.size() > 7) {
        return false;
      }
    }
    System.out.println(seperator.size());
    if ((seperator.size() == 4) || (seperator.size() == 6)) {
      return true;
    }
    return false;
  }

  public static void main(String[] args)
  {
    File dir = new File("test");
    for (File file : dir.listFiles()) {
      System.out.println(file.getName());
      try {
        BufferedImage img = ImageIO.read(file);
        img = Main.removeStamp(img);
        if (check(img))
          System.out.println("yes");
        else
          System.out.println("no");
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}