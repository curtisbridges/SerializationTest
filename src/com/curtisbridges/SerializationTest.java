package com.curtisbridges;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SerializationTest implements Serializable {
    List<Tester> list;

    public SerializationTest() {
        list = new LinkedList<Tester>();
    }

    public synchronized void printContents() {
        System.out.println(this.toString());
    }

    public synchronized void add(Tester test) {
        list.add(test);
    }

    public synchronized void clear() {
        list.clear();
    }

    public String toString() {
        String temp = "[";
        for (Tester test : list) {
            temp += test.toString() + ", ";
        }
        return temp + "]";
    }

    private static class TestSerializer {
        private String filename = "filename.ser";

        public TestSerializer(String name) {
            filename = name;
        }

        public void save(SerializationTest test) throws IOException {
            // Serialize to a file
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(test);
        }

        public SerializationTest restore() throws IOException, FileNotFoundException, ClassNotFoundException {
            // Deserialize from a file
            File file = new File(filename);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            SerializationTest test = (SerializationTest) in.readObject();
            return test;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SerializationTest test = new SerializationTest();
        String array[] = { "one", "two", "three", "four" };

        for (String string : array) {
            test.add(new Tester(string));
        }

        System.out.println("Start: ");
        test.printContents();

        try {
            TestSerializer serializer = new TestSerializer("filename.ser");
            serializer.save(test);
            test.clear();

            System.out.println("Cleared? ");
            test.printContents();

            test = serializer.restore();

            System.out.println("Restored? ");
            test.printContents();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
