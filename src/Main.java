import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException  {

        Teacher.setAlpha(0.1f);
        Teacher.setBeta(0.1f);
        Teacher.setBias(1f);
        Teacher.setDatafile("lang2.train.csv");
        Teacher.setDatatestfile("lang2.test.csv");
        Teacher.setNumofiterations(100);

        Teacher.prepareData();
        Teacher.initializePerceptrons();
        Teacher.work();
        Teacher.ui();

    }
}