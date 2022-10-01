package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ex4 {
    private final int width, height;
    private final BufferedImage bufferedImage;

    public static void main(String[] args) throws IOException {
        Ex4 exercise = new Ex4();
        exercise.tranformImage(0.3, 0.3);
        exercise.tranformImage(0.75, 1.25);
        exercise.tranformImage(1.5, 0.25);
        exercise.tranformImage(1.5, 1.5);
    }

    public Ex4() throws IOException {
        File file = new File("image.bmp");
        bufferedImage = ImageIO.read(file);
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
    }

    public void tranformImage(double n, double m) throws IOException {
        double[][] tmpRed = new double[width][];
        double[][] tmpGreen = new double[width][];
        double[][] tmpBlue = new double[width][];

        int resWidth = (int)(width * n);
        int resHeight = (int)(height * m);
        BufferedImage result  = new BufferedImage(resWidth, resHeight, bufferedImage.getType());

        for (int x = 0; x < width; ++x) {
            tmpRed[x] = new double[(resHeight)];
            tmpGreen[x] = new double[(resHeight)];
            tmpBlue[x] = new double[(resHeight)];
        }

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < resHeight; ++y) {
                double red = 0;
                double green = 0;
                double blue = 0;
                for (int k = 0; k < height; ++k) {
                    Color color = new Color(bufferedImage.getRGB(x, k));
                    double sinc = sinc(Math.PI * (y / m - k));
                    red += color.getRed() * sinc;
                    green += color.getGreen() * sinc;
                    blue += color.getBlue() * sinc;
                }
                tmpRed[x][y] = red;
                tmpGreen[x][y] = green;
                tmpBlue[x][y] = blue;
            }
        }

        for (int x = 0; x < resWidth; ++x) {
            for (int y = 0; y < resHeight; ++y) {
                double red = 0;
                double green = 0;
                double blue = 0;
                for (int k = 0; k < width; ++k) {
                    double sinc = sinc( Math.PI * (x / n - k));
                    red += tmpRed[k][y] * sinc;
                    green += tmpGreen[k][y] * sinc;
                    blue += tmpBlue[k][y] * sinc;
                }

                Color color = new Color(correctColor(red),correctColor(green), correctColor(blue));
                result.setRGB(x,  y, color.getRGB());
            }
        }

        File output = new File("newImage" + n + "x" + m + ".bmp");
        ImageIO.write(result, "bmp", output);
    }

    public int correctColor(double color) {
        int maxColor = 256;
        if (color >= maxColor) {
            return (int) (color - maxColor);
        }
        if (color < 0) {
            return (int) (-color);
        }
        return (int) color;
    }

    public double sinc(double x) {
        if (x != 0) {
            return (Math.sin(x) / x);
        } else {
            return 1;
        }
    }
}
