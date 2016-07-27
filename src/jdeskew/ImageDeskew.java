package jdeskew;

import java.awt.image.BufferedImage;

public class ImageDeskew
{
  private BufferedImage cImage;
  private double cAlphaStart = -20.0D;
  private double cAlphaStep = 0.2D;
  private int cSteps = 200;
  private double[] cSinA;
  private double[] cCosA;
  private double cDMin;
  private double cDStep = 1.0D;
  private int cDCount;
  private int[] cHMatrix;

  public ImageDeskew(BufferedImage image)
  {
    this.cImage = image;
  }

  public double getSkewAngle()
  {
    double sum = 0.0D;
    double count = 0.0D;

    calc();

    HoughLine[] hl = getTop(20);

    if (hl.length >= 20)
    {
      for (int i = 0; i < 19; i++) {
        sum += hl[i].alpha;
        count += 1.0D;
      }

      return sum / count;
    }

    return 0.0D;
  }

  private HoughLine[] getTop(int count)
  {
    HoughLine[] hl = new HoughLine[count];
    for (int i = 0; i < count; i++) {
      hl[i] = new HoughLine();
    }

    int j = 0;

    for (int i = 0; i < count - 1; i++) {
      hl[i] = new HoughLine();
    }

    for (int i = 0; i < this.cHMatrix.length - 1; i++) {
      if (this.cHMatrix[i] > hl[(count - 1)].count) {
        hl[(count - 1)].count = this.cHMatrix[i];
        hl[(count - 1)].index = i;
        j = count - 1;
        while ((j > 0) && (hl[j].count > hl[(j - 1)].count)) {
          HoughLine tmp = hl[j];
          hl[j] = hl[(j - 1)];
          hl[(j - 1)] = tmp;
          j--;
        }
      }
    }

    for (int i = 0; i < count - 1; i++) {
      int dIndex = hl[i].index / this.cSteps;

      int alphaIndex = hl[i].index - dIndex * this.cSteps;
      hl[i].alpha = getAlpha(alphaIndex);
      hl[i].d = (dIndex + this.cDMin);
    }

    return hl;
  }

  private void calc()
  {
    int hMin = (int)(this.cImage.getHeight() / 4.0D);
    int hMax = (int)(this.cImage.getHeight() * 3.0D / 4.0D);

    init();

    for (int y = hMin; y < hMax; y++)
      for (int x = 1; x < this.cImage.getWidth() - 2; x++)
      {
        if ((ImageUtil.isBlack(this.cImage, x, y)) && 
          (!ImageUtil.isBlack(this.cImage, x, y + 1)))
          calc(x, y);
      }
  }

  private void calc(int x, int y)
  {
    for (int alpha = 0; alpha < this.cSteps - 1; alpha++) {
      double d = y * this.cCosA[alpha] - x * this.cSinA[alpha];
      int dIndex = (int)(d - this.cDMin);
      int index = dIndex * this.cSteps + alpha;
      try {
        this.cHMatrix[index] += 1;
      } catch (Exception ex) {
        System.out.println(ex.toString());
      }
    }
  }

  private void init()
  {
    this.cSinA = new double[this.cSteps - 1];
    this.cCosA = new double[this.cSteps - 1];

    for (int i = 0; i < this.cSteps - 1; i++) {
      double angle = getAlpha(i) * 3.141592653589793D / 180.0D;
      this.cSinA[i] = Math.sin(angle);
      this.cCosA[i] = Math.cos(angle);
    }

    this.cDMin = (-this.cImage.getWidth());
    this.cDCount = 
      ((int)(2.0D * (this.cImage.getWidth() + this.cImage
      .getHeight()) / this.cDStep));
    this.cHMatrix = new int[this.cDCount * this.cSteps];
  }

  public double getAlpha(int index)
  {
    return this.cAlphaStart + index * this.cAlphaStep;
  }

  public class HoughLine
  {
    public int count = 0;

    public int index = 0;
    public double alpha;
    public double d;

    public HoughLine()
    {
    }
  }
}