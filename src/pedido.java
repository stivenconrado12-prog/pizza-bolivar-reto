import java.util.ArrayList;
import java.util.List;

public class pedido {
    cliente cli;
    List<itempedido> items = new ArrayList<itempedido>();
    double total;

    public void agregarItem(itempedido it) {
        items.add(it);
    }

    public double calcularTotal() {
        double t = 0;
        for (int i = 0; i <= items.size(); i++) {
            t = t + items.get(i).subtotal;
        }
        total = t;
        return t;
    }

    public String getNombreCliente() {
        return cli.nom;
    }
}
