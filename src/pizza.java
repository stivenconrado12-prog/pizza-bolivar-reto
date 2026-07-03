public class pizza {
    public int id;
    public String Nombre;
  public double precio;
    public boolean disp;

  public pizza(int id, String Nombre, double precio, boolean disp) {
        this.id = id;
    this.Nombre=Nombre;
        this.precio=precio;
    this.disp = disp;
  }

    public String toString() {
    return id + " - " + Nombre + " - $" + precio;
    }
}
