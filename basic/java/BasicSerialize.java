
import java.io.*;

public class BasicSerialize {
  public static void main(String [] args) {
    try {
      JustAClass justAnObject = new JustAClass(42, "The answer");
      String fname = "object.ser";

      // serializing an object
      FileOutputStream fout = new FileOutputStream(fname);
      ObjectOutputStream oout = new ObjectOutputStream(fout);
      oout.writeObject(justAnObject);   // actual serialization
      oout.close();
      fout.close();
      System.out.println("\nThe object was written to " + fname);

      // deserializing an object
      FileInputStream fin = new FileInputStream(fname);
      ObjectInputStream oin = new ObjectInputStream(fin);
      JustAClass deserialized = (JustAClass) oin.readObject();   // actual deserialization
      oin.close();
      fin.close();
      System.out.println("\nThe object was read from " + fname + ":");
      System.out.println(deserialized);
      System.out.println();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
