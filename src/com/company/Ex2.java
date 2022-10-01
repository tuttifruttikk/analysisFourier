package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Ex2 {
    private final ArrayList<Double> u1 = new ArrayList<>();
    private final ArrayList<Double> u2 = new ArrayList<>();

    private double f1 = 3;
    private double f2 = 5;
    private final double T = 1;
    private final double dt = 0.001;
    private double a = -T / 2;
    private double b = T / 2;

    public void createFile() throws IOException {
        FileWriter fileOne = new FileWriter("graphicOne.txt");
        FileWriter fileTwo = new FileWriter("graphicTwo.txt");
        double t = a;
        for (int i = 0; i < u1.size(); ++i) {
            String tmpOne = u1.get(i).toString();
            String tmpTwo = u2.get(i).toString();
            fileOne.write(t + "\t\t" + tmpOne + "\n");
            fileTwo.write(t + "\t\t" + tmpTwo + "\n");
            fileOne.flush();
            fileTwo.flush();
            t += dt;
        }
    }

    public double createFunction(double f, double t) {
        return Math.sin(2 * Math.PI * f * t);
    }

    public double integral(double a, double b) {
        double result = 0;
        for (double t = 0; t < ((b - a) / dt); ++t) {
            result += dt * createFunction(f1, a + t * dt) * createFunction(f2, a + t * dt);
        }
        return result;
    }

    /* 2.1 */
    public void buildGraphics() throws IOException {
        for (double t = a; t < b; t += dt) {
            u1.add(createFunction(f1, t));
            u2.add(createFunction(f2, t));
        }
        createFile();
    }

    /* 2.2 */
    public double calculatedScalar(double a, double b) {
        return (1 / (b - a)) * integral(a, b);
    }

    /* 2.3 */
    public double calculatedNormFunction(double a, double b, double f) {
        double result = 0;
        for (double t = 0; t < ((b - a) / dt); ++t) {
            result += createFunction(f, a + t * dt) * createFunction(f, a + t * dt);
        }
        return Math.sqrt(result * dt / (b - a));
    }

    /* 2.4 */
    public void orthogonalityCheck() {
        System.out.println(calculatedScalar(a, b));
    }


    /* 2.5 */
    public void orthonormalBasis() {
        double normOfu1 = calculatedNormFunction(a, b, f1);
        double normOfu2 = calculatedNormFunction(a, b, f2);

        ArrayList<Double> u1 = new ArrayList<>();
        ArrayList<Double> u2 = new ArrayList<>();

        for (int i = 0; i < this.u1.size(); ++i) {
            u1.add(this.u1.get(i) / normOfu1);
            u2.add(this.u2.get(i) / normOfu2);
        }

        System.out.println("Norm of new u1: " + calculatedNormFunction(a, b, u1));
        System.out.println("Norm of new u2: " + calculatedNormFunction(a, b, u2));
        System.out.println("Scalar: " + calculatedScalar(u1, u2, a, b));
    }

    public double calculatedNormFunction(double a, double b, ArrayList<Double> function) {
        double result = 0;
        for (int i = 0; i < function.size(); ++i) {
            result += function.get(i) * function.get(i);
        }
        return Math.sqrt(result * dt / (b - a));
    }

    private double calculatedScalar(ArrayList<Double> u1, ArrayList<Double> u2, double a, double b) {
        return (1 / (b - a)) * integral(a, b, u1, u2);
    }

    private double integral(double a, double b, ArrayList<Double> u1, ArrayList<Double> u2) {
        double result = 0;
        for (double t = 0; t < ((b - a) / dt); ++t) {
            result += dt * (u1.get((int) t) * u2.get((int) t));
        }
        return result;
    }

    /* 2.6 */
    public void willStayOrthonormalBasisOfWillDouble() {
        System.out.println("2.6.1 : f1, f2 will double");
        f1 *= 2;
        f2 *= 2;
        orthonormalBasis();
        f1 /= 2;
        f2 /= 2;
    }

    public void willStayOrthonormalBasisOfDoubleInterval() {
        System.out.println("\n2.6.2 : double interval ");
        a *= 2;
        b *= 2;

        ArrayList<Double> u1 = new ArrayList<>();
        ArrayList<Double> u2 = new ArrayList<>();

        for (double t = a; t < b; t += dt) {
            u1.add(createFunction(f1, t));
            u2.add(createFunction(f2, t));
        }

        double normOfu1 = calculatedNormFunction(a, b, f1);
        double normOfu2 = calculatedNormFunction(a, b, f2);

        ArrayList<Double> newu1 = new ArrayList<>();
        ArrayList<Double> newu2 = new ArrayList<>();

        for (int i = 0; i < u1.size(); ++i) {
            newu1.add(u1.get(i) / normOfu1);
            newu2.add(u2.get(i) / normOfu2);
        }

        System.out.println("Norm of new u1: " + calculatedNormFunction(a, b, newu1));
        System.out.println("Norm of new u2: " + calculatedNormFunction(a, b, newu2));
        System.out.println("Scalar: " + calculatedScalar(newu1, newu2, a, b));

        a /= 2;
        b /= 2;
    }

    public void willStayOrthonormalBasisIntervalIsLessHalf() {
        System.out.println("\n2.6.3 : the interval is less than half ");
        a /= 2;
        b /= 2;
        orthonormalBasis();
    }

    public static void main(String[] args) throws IOException {
        Ex2 exercise = new Ex2();
        System.out.println("/* 2.1 */\n" + "Building graphics...");
        exercise.buildGraphics();

        System.out.println("\n/* 2.2 */\n" + "Calculated scalar: ");
        System.out.println(exercise.calculatedScalar(exercise.a, exercise.b));

        System.out.println("\n/* 2.3 */\n" + "Calculated norm function f1: ");
        System.out.println(exercise.calculatedNormFunction(exercise.a, exercise.b, exercise.f1));

        System.out.println("\nCalculated norm function f2: ");
        System.out.println(exercise.calculatedNormFunction(exercise.a, exercise.b, exercise.f2));

        System.out.println("\n/* 2.4 */\n" + "Orthogonality check: ");
        exercise.orthogonalityCheck();

        System.out.println("\n/* 2.5 */\n" + "Orthonormal basis: ");
        exercise.orthonormalBasis();

        System.out.println("\n/* 2.6 */\n" + "Orthonormal basis check: ");
        exercise.willStayOrthonormalBasisOfWillDouble();
        exercise.willStayOrthonormalBasisOfDoubleInterval();
        exercise.willStayOrthonormalBasisIntervalIsLessHalf();

    }
}
