package process;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import javax.imageio.ImageIO;

public class CannyEdge
{
  private static int height;
  private static int width;

  public static BufferedImage canny(BufferedImage sourceImage, int lowThreshold, int highThreshold)
  {
    height = sourceImage.getHeight();
    width = sourceImage.getWidth();
    int picsize = width * height;

    ColorSpace grayCS = ColorSpace.getInstance(1003);
    ColorConvertOp colorConvertOp = new ColorConvertOp(grayCS, null);
    sourceImage = colorConvertOp.filter(sourceImage, null);

    sourceImage = gaussianSmooth(sourceImage);

    int[] gradeMagnitude = new int[picsize];

    int[] gradeOrientation = new int[picsize];

    grade(sourceImage, gradeMagnitude, gradeOrientation);

    int[] edgeImage = NonmMaxSuppress(gradeMagnitude, gradeOrientation);

    return thresholdingTracker(edgeImage, gradeMagnitude, lowThreshold, highThreshold);
  }

  private static BufferedImage gaussianSmooth(BufferedImage sourceImage)
  {
    float[] elements = 
      { 0.0625F, 0.125F, 0.0625F, 
      0.125F, 0.25F, 0.125F, 
      0.0625F, 0.125F, 0.0625F };

    BufferedImage bi = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), 1);
    Graphics2D big = bi.createGraphics();
    big.drawImage(sourceImage, 0, 0, null);

    Kernel kernel = new Kernel(3, 3, elements);
    ConvolveOp blur = new ConvolveOp(kernel, 1, null);
    sourceImage = blur.filter(bi, null);

    return sourceImage;
  }

  private static void grade(BufferedImage grayImage, int[] gradeMagnitude, int[] gradeOrientation)
  {
    int height = grayImage.getHeight();
    int width = grayImage.getWidth();
    int[] srcGray = new int[width * height];
    for (int i = 0; i < width; i++)
      for (int j = 0; j < height; j++)
        srcGray[(i + j * width)] = (0xFF & grayImage.getRGB(i, j));
    for (int i = 1; i < width - 1; i++)
    {
      for (int j = 1; j < height - 1; j++)
      {
        int x = srcGray[(i + 1 + j * width)] - srcGray[(i - 1 + j * width)];
        int y = srcGray[(i + (j + 1) * width)] - srcGray[(i + (j - 1) * width)];

        gradeMagnitude[(i + j * width)] = ((int)Math.sqrt(x * x + y * y));

        gradeOrientation[(i + j * width)] = (x * y);
      }
    }
  }

  private static int[] NonmMaxSuppress(int[] gradeMagnitude, int[] gradeOrientation)
  {
    int[] edgeImage = new int[width * height];

    for (int i = 1; i < width - 1; i++)
    {
      for (int j = 1; j < height - 1; j++)
      {
        if (gradeMagnitude[(i + width * j)] == 0)
        {
          edgeImage[(i + width * j)] = 0;
        }
        else {
          int n1 = 0; int n2 = 0;

          if (gradeOrientation[(i + width * j)] > 0)
          {
            n1 = gradeMagnitude[(i - 1 + width * (j - 1))];
            n2 = gradeMagnitude[(i + 1 + width * (j + 1))];
          }
          else
          {
            n1 = gradeMagnitude[(i + 1 + width * (j - 1))];
            n2 = gradeMagnitude[(i - 1 + width * (j + 1))];
          }

          if ((gradeMagnitude[(i + width * j)] >= n1) && (gradeMagnitude[(i + width * j)] >= n2))
          {
            edgeImage[(i + width * j)] = 128;
          }
          else
          {
            edgeImage[(i + width * j)] = 0;
          }
        }
      }
    }
    return edgeImage;
  }

  private static BufferedImage thresholdingTracker(int[] edgeImage, int[] gradeMagnitude, int lowThreshold, int highThreshold)
  {
    for (int i = 0; i < width; i++)
    {
      for (int j = 0; j < height; j++)
      {
        if ((edgeImage[(i + width * j)] == 128) && (gradeMagnitude[(i + width * j)] >= highThreshold))
        {
          edgeImage[(i + width * j)] = 255;

          follow(edgeImage, i, j, gradeMagnitude, lowThreshold);
        }
      }
    }

    BufferedImage destImage = new BufferedImage(width, height, 1);
    for (int i = 0; i < width; i++)
    {
      for (int j = 0; j < height; j++) {
        if (edgeImage[(i + width * j)] == 255)
          destImage.setRGB(i, j, -16777216);
        else
          destImage.setRGB(i, j, -1);
      }
    }
    return destImage;
  }

  private static void follow(int[] edgeImage, int x, int y, int[] gradeMagnitude, int lowThreshold)
  {
    int[][] dir = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0}, { 1, 1 } };
    for (int k = 0; k < 8; k++)
    {
      int ii = x + dir[k][0];
      int jj = y + dir[k][1];

      if ((edgeImage[(ii + width * jj)] == 128) && (gradeMagnitude[(ii + width * jj)] >= lowThreshold))
      {
        edgeImage[(ii + width * jj)] = 255;

        follow(edgeImage, ii, jj, gradeMagnitude, lowThreshold);
      }
    }
  }

  public static void main(String[] args)
    throws Exception
  {
    BufferedImage img = null;
    File testDataDir = new File("D://testdata");
    for (File file : testDataDir.listFiles()) {
      img = ImageIO.read(file);
      img = canny(img, 50, 80);

      ImageIO.write(img, "jpg", new File(file.getAbsolutePath().replace(".", "_c.")));
    }
  }
}