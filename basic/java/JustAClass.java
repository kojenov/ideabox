
public class JustAClass implements java.io.Serializable {
  int    id;
  String name;

  public JustAClass(int id, String name) {
    this.id       = id;
    this.name     = name;
  }

  public String toString() {
    return name + ": " + id;
  }
}
