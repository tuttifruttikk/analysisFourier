package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Ex3 {
    private final double F = 10;
    private final double Fd = 10000;
    private final double T = 10 / F;
    private final double dt = T / Fd;

    private final double fd1 = 1.5 * F;
    private final double fd2 = 1.75 * F;
    private final double fd3 = 2 * F;
    private final double fd4 = 3 * F;
    private final double fd5 = 1000 * F;

    ArrayList<Double> time = new ArrayList<>();

    public double createFunction(double t) {
        return (Math.sin(2 * Math.PI * F * t));
    }

    public double sinc(double x) {
        if (x != 0) {
            return (Math.sin(x) / x);
        } else {
            return 1;
        }
    }

    public void createTime() {
        for (double i = 0; i < T; i += dt) {
            time.add(i);
        }
    }

    public void createFile(ArrayList<Double> time, ArrayList<Double> values, String name) throws IOException {
        FileWriter file = new FileWriter( name + ".txt");
        for (int i = 0; i < values.size(); ++i) {
            String tmp = values.get(i).toString();
            file.write(time.get(i) + "\t\t" + tmp + "\n");
            file.flush();
        }
    }

    public ArrayList<Double> calculatedFunction(double t1, double t2, double dt) {
        int size = (int) ((t2 - t1) / dt);
        ArrayList<Double> result = new ArrayList<>();
        double value;
        double t = t1;

        for (int i = 0; i < size; ++i) {
            value = createFunction(t);
            result.add(value);
            t += dt;
        }
        return result;
    }


    public ArrayList<Double> recoverySignal(ArrayList<Double> signalValues, double dt1, double t1, double t2, double dt) {
        int sizeDiscrete = signalValues.size();
        int size = (int) ((t2 - t1) / dt);
        ArrayList<Double> result = new ArrayList<>();
        double t = t1;

        for (int i = 0; i < size; ++i){
            double total = 0;
            for (int k = 0; k < sizeDiscrete; ++k){
                total += signalValues.get(k) * sinc(Math.PI * (t / dt1 - k));
            }
            result.add(total);
            t += dt;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        Ex3 exercise = new Ex3();
        exercise.createTime();

        ArrayList<Double> original = exercise.calculatedFunction(0, exercise.T, exercise.dt);

        ArrayList<Double> discreteSignal1 = exercise.calculatedFunction(0, exercise.T, exercise.T / exercise.fd1);
        ArrayList<Double> recoverySignal1 = exercise.recoverySignal(discreteSignal1, exercise.T / exercise.fd1, 0 , exercise.T, exercise.dt);

        ArrayList<Double> discreteSignal2 = exercise.calculatedFunction(0, exercise.T, exercise.T / exercise.fd2);
        ArrayList<Double> recoverySignal2 = exercise.recoverySignal(discreteSignal2, exercise.T / exercise.fd2, 0 , exercise.T, exercise.dt);

        ArrayList<Double> discreteSignal3 = exercise.calculatedFunction(0, exercise.T, exercise.T / exercise.fd3);
        ArrayList<Double> recoverySignal3 = exercise.recoverySignal(discreteSignal3, exercise.T / exercise.fd3, 0 , exercise.T, exercise.dt);

        ArrayList<Double> discreteSignal4 = exercise.calculatedFunction(0, exercise.T, exercise.T / exercise.fd4);
        ArrayList<Double> recoverySignal4 = exercise.recoverySignal(discreteSignal4, exercise.T / exercise.fd4, 0 , exercise.T, exercise.dt);

        ArrayList<Double> discreteSignal5 = exercise.calculatedFunction(0, exercise.T, exercise.T / exercise.fd5);
        ArrayList<Double> recoverySignal5 = exercise.recoverySignal(discreteSignal5, exercise.T / exercise.fd5, 0 , exercise.T, exercise.dt);

        exercise.createFile(exercise.time, original, "original");
        exercise.createFile(exercise.time, recoverySignal1, "signal1.5F");
        exercise.createFile(exercise.time, recoverySignal2, "signal1.75F");
        exercise.createFile(exercise.time, recoverySignal3, "signal2F");
        exercise.createFile(exercise.time, recoverySignal4, "signal3F");
        exercise.createFile(exercise.time, recoverySignal5, "signal1000F");
    }
}
