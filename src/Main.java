import java.util.List;
import java.util.Scanner;

public class Main {

    // Cambiado a variables de instancia privadas si fuera el caso, pero estáticas para este diseño simple
    private static final Scanner sc = new Scanner(System.in);
    private static final PedidoDAO dao = new PedidoDAO();

    public static void main(String[] args) {
        int op;
        do {
            System.out.println("\n--- PIZZA BOLIVAR ---");
            System.out.println("1. Ver menu de pizzas");
            System.out.println("2. Crear pedido");
            System.out.println("3. Ver pedidos guardados");
            System.out.println("4. Salir");
            System.out.print("Opcion: ");
            
            while (!sc.hasNextInt()) { // Validación por si digitan letras por error
                System.out.print("Por favor, introduce un numero valido: ");
                sc.next();
            }
            op = sc.nextInt();
            sc.nextLine(); // Corregido: Limpia el búfer del salto de línea de la opción

            switch (op) {
                case 1:
                    verMenu();
                    break; // Corregido: Evita que pase directo a crear pedido
                case 2:
                    crearPedido();
                    break;
                case 3:
                    dao.listarPedidos();
                    break;
                case 4:
                    System.out.println("Gracias por su compra. ¡Vuelva pronto!");
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo.");
            }
        } while (op != 4);
    }

    static List<Pizza> verMenu() { // Corregido: Uso de la clase Pizza en mayúscula
        List<Pizza> pizzas = dao.listarPizzas();
        System.out.println("\n--- NUESTRO MENU ---");
        for (int i = 0; i < pizzas.size(); i++) {
            // Mostramos un índice amigable (1, 2, 3...) en vez de empezar en 0
            System.out.println((i + 1) + ". " + pizzas.get(i).getNombre() + " - $" + pizzas.get(i).getPrecio());
        }
        return pizzas;
    }

    static void crearPedido() {
        List<Pizza> disponibles = verMenu();
        if (disponibles.isEmpty()) {
            System.out.println("No hay pizzas disponibles en este momento.");
            return;
        }

        System.out.println("\n--- DATOS DEL CLIENTE ---");
        System.out.print("Nombre del cliente: ");
        String nom = sc.nextLine(); // Ahora sí funcionará sin saltarse
        System.out.print("Telefono: ");
        String tel = sc.nextLine();
        System.out.print("Direccion: ");
        String dir = sc.nextLine();

        // Corregido: Instanciación con la clase Cliente respetando encapsulamiento
        Cliente c = new Cliente(0, nom, tel, dir);
        int idCliente = dao.guardarCliente(c);
        c.setId(idCliente); // Usa el setter seguro

        Pedido ped = new Pedido(c); // Corregido: El constructor ya asocia al cliente de forma segura

        String continuar = "s";
        // Corregido: Uso de equalsIgnoreCase para comparar contenido de texto de forma segura
        while (continuar.equalsIgnoreCase("s")) {
            System.out.print("\nNumero de pizza a agregar (segun el menu): ");
            int menuIdx = sc.nextInt();
            System.out.print("Cantidad: ");
            int cant = sc.nextInt();
            sc.nextLine(); // Limpia el búfer después de leer números

            // Corregido: Restamos 1 para ajustar el índice amigable (1,2,3) al índice de Java (0,1,2)
            int realIdx = menuIdx - 1; 
            if (realIdx >= 0 && realIdx < disponibles.size()) {
                Pizza seleccionada = disponibles.get(realIdx);
                ItemPedido item = new ItemPedido(seleccionada, cant);
                ped.agregarItem(item);
                System.out.println("¡" + seleccionada.getNombre() + " agregada al pedido!");
            } else {
                System.out.println("Numero de pizza inválido. No se agrego nada.");
            }

            System.out.print("¿Desea agregar otra pizza? (s/n): ");
            continuar = sc.nextLine();
        }

        ped.calcularTotal();
        dao.guardarPedido(ped);
        System.out.println("\n============= FACTURA =============");
        System.out.println("Pedido guardado con éxito.");
        System.out.println("Cliente: " + ped.getNombreCliente());
        System.out.println("Total a pagar: $" + ped.getTotal());
        System.out.println("===================================");
    }
}