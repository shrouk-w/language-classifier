import java.util.ArrayList;

public class TestRecord {

    public String name;

    public ArrayList<Float> properties;

    public TestRecord(String name, ArrayList<Float> properties) {

        this.name = name;

        this.properties = properties;

    }

    @Override

    public String toString() {

        return "TestRecord{" +

                "name='" + name + '\'' +

                ", properties=" + properties +

                '}';

    }

}

