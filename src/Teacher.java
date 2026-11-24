import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;

import java.util.*;

import java.util.stream.Collectors;

public class Teacher {

    static int dimension = 26;

    static float alpha = 0.0f;

    static float beta = 0.0f;

    static float bias = 0.0f;

    static String datafile;

    static String datatestfile;

    static int numofiterations = 0;

    static ArrayList<TestRecord> records = new ArrayList<>();

    static ArrayList<TestRecord> testrecords = new ArrayList<>();

    static HashMap<String,Perceptron> trained = new HashMap<>();

    public static void setNumofiterations(int numofiterations) {

        Teacher.numofiterations = numofiterations;

    }

    public static void setAlpha(float alpha) {

        Teacher.alpha = alpha;

    }

    public static void setBeta(float beta) {

        Teacher.beta = beta;

    }

    public static void setBias(float bias) {

        Teacher.bias = bias;

    }

    public static void setDatafile(String datafile) {

        Teacher.datafile = datafile;

    }

    public static void setDatatestfile(String datatestfile) {

        Teacher.datatestfile = datatestfile;

    }

    public static void prepareData() throws IOException {

        {

            FileReader fr = new FileReader(datafile);

            BufferedReader br = new BufferedReader(fr);

            while (br.ready()) {

                String line = br.readLine();

                line = Util.cleanString(line);

                String[] fields = line.split(" ");

                String key = fields[0];

                StringBuilder rest = new StringBuilder();

                for (int i = 1; i < fields.length; i++) {

                    rest.append(fields[i]);

                }

                TestRecord record = new TestRecord(key, Util.mapMyString(rest.toString()));

                records.add(record);

            }

        }

        {

            FileReader fr = new FileReader(datatestfile);

            BufferedReader br = new BufferedReader(fr);

            while (br.ready()) {

                String line = br.readLine();

                line = Util.cleanString(line);

                String[] fields = line.split(" ");

                String key = fields[0];

                StringBuilder rest = new StringBuilder();

                for (int i = 1; i < fields.length; i++) {

                    rest.append(fields[i]);

                }

                TestRecord record = new TestRecord(key, Util.mapMyString(rest.toString()));

                testrecords.add(record);

            }

        }

    }

    public static Perceptron train(String PropertyThatActsAsOne, int dimension){

        Perceptron lala = new Perceptron(dimension);

        lala.setAlpha(alpha);

        lala.setBeta(beta);

        lala.setBias(bias);

        for(int i = 0; i < numofiterations; i++) {

            Collections.shuffle(records);

            for (var entry : records) {

                String key = entry.name;

                int decision = key.equals(PropertyThatActsAsOne) ? 1 : 0;

                lala.learn(entry.properties, decision);

            }

        }

        return lala;

    }

    public static void initializePerceptrons(){

        ArrayList<String> keys = new ArrayList<>(records.stream().map(e->e.name).distinct().collect(Collectors.toList()));

        for(var key : keys)

        {

            Perceptron perceptron = train(key,dimension);

            trained.put(key,perceptron);

        }

    }

    public static String Decision(ArrayList<Float> input){

        double biggest = Double.NEGATIVE_INFINITY;

        String decision = "cant decide";

        for(var percep : trained.entrySet()){

            if(percep.getValue().computeRAW(input)>biggest){

                biggest = percep.getValue().computeRAW(input);

                decision = percep.getKey();

            }

        }

        return decision;

    }

    public static void work(){

        testrecords.sort((a,b)->a.name.compareTo(b.name));

        int hit =0;

        int all =0;

        String currentlytested=testrecords.get(0).name;

        for(var record : testrecords){

            if(!record.name.equals(currentlytested)){

                float acc = all==0?0:hit/(all*1.0f)*100f;

                System.out.println("accuracy for "+currentlytested+": "+acc+" %");

                hit=0;

                all=0;

                currentlytested=record.name;

            }

            if(Decision(record.properties).equals(record.name))

                hit++;

            /*else

                System.out.println("wyszło: "+Decision(record.properties)+" powinno byc: "+record.name);*/

            all++;

        }

        float acc = all==0?0:hit/(all*1.0f)*100f;

        System.out.println("accuracy for "+currentlytested+": "+acc+" %");

    }

    public static void ui(){

        while(true){

            Scanner sc = new Scanner(System.in);

            String input = sc.nextLine();

            input = Util.cleanString(input);

            String[] temp = input.split(" ");

            StringBuilder a = new StringBuilder();

            for (int i = 0; i < temp.length; i++) {

                a.append(temp[i]);

            }

            System.out.println(Decision(Util.mapMyString(a.toString())));

        }

    }

}

