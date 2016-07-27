package process;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import jdeskew.ImageDeskew;
import jdeskew.ImageUtil;

public class Utils
{
  private static final double MINIMUM_DESKEW_THRESHOLD = 0.05D;
  private static Set<Point> info = new HashSet();

  public static int[] Tohisto(byte[][] pixels, String type) { int width = pixels[0].length;
    int height = pixels.length;
    if (type == "row") {
      int[] histo = new int[height];
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          if (pixels[y][x] == 1) {
            histo[y] += 1;
          }
        }
      }
      return histo;
    }
    if (type == "col") {
      int[] histo = new int[width];
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          if (pixels[y][x] == 1) {
            histo[x] += 1;
          }
        }
      }
      return histo;
    }
    return new int[0];
  }

  public static boolean isRed(int colorInt)
  {
    Color color = new Color(colorInt);
    int r = color.getRed();
    if (r > 180)
    {
      return true;
    }
    return false;
  }

  public static int isBlack(int colorInt, int threshhold)
  {
    Color color = new Color(colorInt);
    if (color.getRed() + color.getGreen() + color.getBlue() <= 250)
    {
      return 1;
    }
    return 0;
  }

  public static int[] Tohisto(BufferedImage img, String type)
  {
    int width = img.getWidth();
    int height = img.getHeight();
    int[] histo = null;
    if (type == "row") {
      histo = new int[height];
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          if (isBlack(img.getRGB(x, y), 250) == 1) {
            histo[y] += 1;
          }
        }
      }
    }
    if (type == "col") {
      histo = new int[width];
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          if (isBlack(img.getRGB(x, y), 250) == 1) {
            histo[x] += 1;
          }
        }
      }
    }
    return histo;
  }

  public static int ostu(int[][] gray, int w, int h)
  {
    int[] histData = new int[w * h];

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        int red = 0xFF & gray[y][x];
        histData[red] += 1;
      }

    }

    int total = w * h;

    float sum = 0.0F;
    for (int t = 0; t < 256; t++) {
      sum += t * histData[t];
    }
    float sumB = 0.0F;
    int wB = 0;
    int wF = 0;

    float varMax = 0.0F;
    int threshold = 0;

    for (int t = 0; t < 256; t++)
    {
      wB += histData[t];
      if (wB != 0)
      {
        wF = total - wB;
        if (wF == 0) {
          break;
        }
        sumB += t * histData[t];

        float mB = sumB / wB;
        float mF = (sum - sumB) / wF;

        float varBetween = wB * wF * (mB - mF) * (mB - mF);

        if (varBetween > varMax)
        {
          varMax = varBetween;
          threshold = t;
        }
      }
    }
    return threshold;
  }
  public static byte[][] OSTUbinarition(BufferedImage img) {
    int h = img.getHeight();
    int w = img.getWidth();
    int[][] colorPixels = new int[h][w];
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        colorPixels[y][x] = img.getRGB(x, y);
        if (isRed(colorPixels[y][x])) {
          colorPixels[y][x] = Color.white.getRGB();
        }
      }
    }

    int[][] gray = new int[h][w];
    gray = graying(colorPixels);
    int threshold = ostu(gray, w, h) + 20;
    byte[][] pixels = new byte[h][w];
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        if (gray[y][x] < threshold + 30)
        {
          pixels[y][x] = 1;
        }
      }
    }
    return pixels;
  }

  public static int[][] graying(int[][] colorPixels)
  {
    int h = colorPixels.length;
    int w = colorPixels[0].length;

    int[][] gray = new int[h][w];
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        int argb = colorPixels[y][x];

        int r = (int)((argb >> 16 & 0xFF) * 1.1D + 30.0D);
        int g = (int)((argb >> 8 & 0xFF) * 1.1D + 30.0D);
        int b = (int)((argb >> 0 & 0xFF) * 1.1D + 30.0D);
        if (r >= 255)
        {
          r = 255;
        }
        if (g >= 255)
        {
          g = 255;
        }
        if (b >= 255)
        {
          b = 255;
        }
        gray[y][x] = 
          ((int)Math.pow(Math.pow(r, 2.2D) * 0.2973D + Math.pow(g, 2.2D) * 
          0.6274D + Math.pow(b, 2.2D) * 0.07530000000000001D, 0.4545454545454545D));
      }
    }
    return gray;
  }

  public static BufferedImage pixels2Image(byte[][] pixels)
  {
    int h = pixels.length;
    int w = pixels[0].length;
    BufferedImage img = new BufferedImage(w, h, 12);
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        if (pixels[y][x] == 0) {
          img.setRGB(x, y, Color.white.getRGB());
        }
      }
    }
    return img;
  }

  public static byte[][] erode(byte[][] pixels, Point[] H)
  {
    int h = pixels.length;
    int w = pixels[0].length;
    int Hlen = H.length;
    boolean flag = true;

    byte[][] temp = new byte[h][w];
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        if (pixels[y][x] == 1) {
          flag = true;
          for (int i = 0; i < Hlen; i++) {
            if ((x + H[i].x < w) && (x + H[i].x >= 0) && 
              (y + H[i].y < h) && (y + H[i].y >= 0) && 
              (pixels[(y + H[i].y)][(x + H[i].x)] == 0)) {
              flag = false;
              break;
            }
          }
          if (flag) {
            temp[y][x] = 1;
          }
        }
      }
    }
    return temp;
  }

  public static byte[][] dilate(byte[][] pixels, Point[] H)
  {
    int h = pixels.length;
    int w = pixels[0].length;
    int Hlen = H.length;
    byte[][] temp = new byte[h][w];

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        if (pixels[y][x] == 1) {
          for (int i = 0; i < Hlen; i++) {
            if ((x + H[i].x < w) && (x + H[i].x >= 0) && 
              (y + H[i].y < h) && (y + H[i].y >= 0)) {
              temp[(y + H[i].y)][(x + H[i].x)] = 1;
            }
          }
        }
      }
    }
    return temp;
  }

  public static byte[][] open(byte[][] pixels, Point[] H)
  {
    pixels = erode(pixels, H);
    pixels = dilate(pixels, H);
    return pixels;
  }

  public static byte[][] repair(byte[][] pixels) {
    int h = pixels.length;
    int w = pixels[0].length;

    Point[] H = new Point[4];
    int n = 0;
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        H[(n++)] = new Point(i, j);
      }
    }
    pixels = dilate(pixels, H);
    pixels = erode(pixels, H);

    Point[] hRow = new Point[(int)(w * 0.07000000000000001D)];
    for (int i = 0; i < hRow.length; i++) {
      hRow[i] = new Point(i - (int)(w * 0.035D), 0);
    }

    Point[] hCol = new Point[(int)(h * 0.045D)];
    for (int i = 0; i < hCol.length; i++) {
      hCol[i] = new Point(0, i - (int)(h * 0.02D));
    }

    byte[][] rowPixels = open(pixels, hRow);
    byte[][] colPixels = open(pixels, hCol);
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        rowPixels[y][x] = ((byte)(rowPixels[y][x] | colPixels[y][x]));
        int tmp240_238 = x;
        byte[] tmp240_237 = pixels[y]; tmp240_237[tmp240_238] = ((byte)(tmp240_237[tmp240_238] - rowPixels[y][x]));
      }

    }

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        if ((pixels[y][x] != 0) && (x * y != 0) && (x != w - 1) && (y != h - 1))
        {
          if (pixels[(y - 1)][x] + pixels[(y + 1)][x] + pixels[y][(x - 1)] + 
            pixels[y][(x + 1)] < 1) {
            pixels[y][x] = 0;
          }
        }
      }
    }

    return pixels;
  }

  public static byte[][] binarition(BufferedImage img, int threshhold) {
    int h = img.getHeight();
    int w = img.getWidth();

    int[] pixels = new int[h * w];
    byte[][] biPixels = new byte[h][w];
    img.getRGB(0, 0, w, h, pixels, 0, w);
    for (int i = 0; i < h * w; i++) {
      if (!isRed(pixels[i]))
      {
        int r = (int)((pixels[i] >> 16 & 0xFF) * 1.1D + 30.0D);
        int g = (int)((pixels[i] >> 8 & 0xFF) * 1.1D + 30.0D);
        int b = (int)((pixels[i] >> 0 & 0xFF) * 1.1D + 30.0D);
        if (r + g + b < threshhold) {
          biPixels[(i / w)][(i % w)] = 1;
        }
      }
    }

    return biPixels;
  }

  public static int[][] mediumFilter(int[][] gray)
  {
    int w = gray[0].length;
    int h = gray.length;

    int[][] result = new int[h][w];
    int[] temp = new int[9];
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        if ((x * y == 0) || (x == w - 1) || (y == h - 1)) {
          result[y][x] = 'ÿ';
        }
        else {
          int index = 0;
          for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
              temp[(index++)] = gray[i][j];
            }
          }
          Arrays.sort(temp);
          result[y][x] = temp[5];
        }
      }
    }
    return result;
  }
  public static BufferedImage rectify(byte[][] pixels) {
    int h = pixels.length;
    int w = pixels[0].length;
    BufferedImage img = pixels2Image(pixels);

    int lenH = (int)(h * 0.005D);
    int lenW = (int)(w * 0.02D);
    Point[] H = new Point[lenH * lenW];
    int n = 0;
    for (int i = 0; i < lenW; i++) {
      for (int j = 0; j < lenH; j++) {
        H[(n++)] = new Point(i, j);
      }
    }
    pixels = dilate(pixels, H);
    BufferedImage txtImg = pixels2Image(pixels);
    txtImg = CannyEdge.canny(txtImg, 50, 80);
    ImageDeskew deskew = new ImageDeskew(txtImg);
    double imageSkewAngle = deskew.getSkewAngle();
    if ((imageSkewAngle > 0.05D) || (imageSkewAngle < -0.05D)) {
      img = ImageUtil.rotate(img, -imageSkewAngle, w, h);
    }
    return img;
  }

  public static void getColorArea(byte[][] pixels, int x, int y)
  {
    int height = pixels.length;
    int width = pixels[0].length;
    if ((x < 0) || (x > width - 1) || (y < 0) || (y > height - 1)) {
      return;
    }
    if ((pixels[y][x] == 2) || (pixels[y][x] == 0))
      return;
    pixels[y][x] = 2;
    info.add(new Point(x, y));

    getColorArea(pixels, x - 1, y + 1);
    getColorArea(pixels, x, y + 1);

    getColorArea(pixels, x + 1, y + 1);
    getColorArea(pixels, x + 1, y);
    getColorArea(pixels, x + 1, y - 1);
  }
  public static void main(String[] args) {
    try {
      long time = System.currentTimeMillis();
      File dir = new File("test");
      for (File file : dir.listFiles()) {
        System.out.println(file.getName());
        BufferedImage img = ImageIO.read(file);
        int h = img.getHeight();
        int w = img.getWidth();
//        img = Main.removeStamp(img);
        img = img.getSubimage((int)(w * 0.1D), (int)(h * 0.05D), (int)(w * 0.8D), (int)(h * 0.86D));
//        byte[][] pixels = OSTUbinarition(img);
        byte[][] pixels = binarition(img, 750);
        img = rectify(pixels);
        pixels = binarition(img, 750);
        pixels = repair(pixels);
        img = pixels2Image(pixels);
        ImageIO.write(img, "png", new File("result/" + file.getName() + ".png"));
      }
      time = System.currentTimeMillis() - time;
      System.out.println(time);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}