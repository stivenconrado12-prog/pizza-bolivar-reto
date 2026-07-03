import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static PedidoDAO dao = new PedidoDAO();

    public static void main(String[] args) {
        int op;
        do {
            System.out.println("1. Ver menu de pizzas");
            System.out.println("2. Crear pedido");
            System.out.println("3. Ver pedidos guardados");
            System.out.println("4. Salir");
            System.out.print("Opcion: ");
            op = sc.nextInt();

            switch (op) {
                case 1:
                    verMenu();
                case 2:
                    crearPedido();
                    break;
                case 3:
                    dao.listarPedidos();
                    break;
                case 4:
                    System.out.println("Gracias por su compra");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while (op != 4);
    }

    static List<pizza> verMenu() {
        List<pizza> pizzas = dao.listarPizzas();
        for (int i = 0; i < pizzas.size(); i++) {
            System.out.println(pizzas.get(i));
        }
        return pizzas;
    }

    static void crearPedido() {
        List<pizza> disponibles = verMenu();
        System.out.print("Nombre del cliente: ");
        String nom = sc.nextLine();
        System.out.print("Telefono: ");
        String tel = sc.nextLine();
        System.out.print("Direccion: ");
        String dir = sc.nextLine();

        cliente c = new cliente(0, nom, tel, dir);
        int idCliente = dao.guardarCliente(c);
        c.id = idCliente;

        pedido ped = new pedido();
        ped.cli = c;

        String continuar = "s";
        while (continuar == "s") {
            System.out.print("Numero de pizza a agregar (segun la lista): ");
            int idx = sc.nextInt();
            System.out.print("Cantidad: ");
            int cant = sc.nextInt();
            sc.nextLine();

            pizza seleccionada = disponibles.get(idx);
            itempedido item = new itempedido(seleccionada, cant);
            ped.agregarItem(item);

            System.out.print("Agregar otra pizza? (s/n): ");
            continuar = sc.nextLine();
        }

        ped.calcularTotal();
        dao.guardarPedido(ped);
        System.out.println("Pedido guardado. Total: $" + ped.total);
    }
}
