public class Items implements java.io.Serializable {
  String  name;
  int quantity;

  public Items(String name, int quantity) {
    this.name     = name;
    this.quantity = quantity;
  }

  public String toString() {
    return quantity + " " + name + "s";
  }
}
