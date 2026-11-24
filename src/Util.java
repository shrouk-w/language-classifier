import java.util.ArrayList;

import java.util.HashMap;

import java.util.stream.Collectors;

public class Util {

    public static String cleanString(String str) {

        str = str.toLowerCase();

        int i = str.indexOf(',');

        if(i != -1) {

            str = str.substring(0, i) + " " + str.substring(i + 1, str.length());

        }

        str = str.replaceAll("[^a-z ]", "");

        str = str.replaceAll(" +", " ");

        return str;

    }

    public static ArrayList<Float> mapMyString(String str) {

        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 'a'; i <= 'z'; i++) {

            map.put((char) i,0);

        }

        for(int i = 0; i < str.length(); i++) {

            map.compute(str.charAt(i), (k, v) -> v + 1);

        }

        ArrayList<Float> list = new ArrayList<>(

                    map.entrySet().stream()

                        .map(e -> e.getValue()/(26f))

                        .collect(Collectors.toList()));

        return list;

    }

}

