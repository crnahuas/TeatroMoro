package com.mycompany.funcionalidadesteatromoro;

import java.util.ArrayList;
import java.util.Scanner;

public class FuncionalidadesTeatroMoro {

    // Variables estáticas (estadísticas globales)
    static final String NOMBRE_TEATRO = "Teatro Moro";
    static int entradasDisponibles = 100;
    static double ingresosTotales = 0.0;
    static int contadorEntradas = 0;

    // Simulación de variables de instancia con listas
    static ArrayList<Integer> numeros = new ArrayList<>();
    static ArrayList<String> ubicaciones = new ArrayList<>();
    static ArrayList<String> tiposClientes = new ArrayList<>();
    static ArrayList<Double> preciosFinales = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        System.out.println("\n¡Hola! Bienvenido al sistema de reservas del Teatro Moro");
        
        while (!salir) {
            System.out.println("\n=== " + NOMBRE_TEATRO + " ===");
            System.out.println("Entradas disponibles: " + entradasDisponibles);
            System.out.println("1. Vender entrada");
            System.out.println("2. Mostrar promociones");
            System.out.println("3. Buscar entrada");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Ver todas las entradas vendidas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> venderEntrada(scanner);
                case 2 -> mostrarPromociones();
                case 3 -> buscarEntrada(scanner);
                case 4 -> eliminarEntrada(scanner);
                case 5 -> mostrarTodasLasEntradas();
                case 6 -> {
                    salir = true;
                    System.out.printf("Ingresos totales: $%.2f\n", ingresosTotales);
                    System.out.println("Entradas vendidas: " + numeros.size());
                    System.out.println("¡Hasta pronto!");
                }
                default -> System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }

    private static void venderEntrada(Scanner scanner) {
        if (entradasDisponibles <= 0) {
            System.out.println("No hay entradas disponibles.");
            return;
        }

        String ubicacion;
        String tipoCliente;
        int precioBase = 0;
        double descuento = 0;

        while (true) {
            System.out.print("Ubicación (VIP $10000 / Platea $15000 / General $20000): ");
            ubicacion = scanner.nextLine().toLowerCase();
            if (ubicacion.equals("vip")) {
                precioBase = 10000;
                break;
            } else if (ubicacion.equals("platea")) {
                precioBase = 15000;
                break;
            } else if (ubicacion.equals("general")) {
                precioBase = 20000;
                break;
            } else {
                System.out.println("Ubicación no válida. Intente nuevamente.");
            }
        }

        while (true) {
            System.out.print("Tipo de cliente (normal / estudiante / tercera edad): ");
            tipoCliente = scanner.nextLine().toLowerCase();
            if (tipoCliente.equals("normal")) {
                break;
            } else if (tipoCliente.equals("estudiante")) {
                descuento = precioBase * 0.10;
                break;
            } else if (tipoCliente.equals("tercera edad")) {
                descuento = precioBase * 0.15;
                break;
            } else {
                System.out.println("Tipo no válido. Intente nuevamente.");
            }
        }

        double precioFinal = precioBase - descuento;
        contadorEntradas++;
        entradasDisponibles--;
        ingresosTotales += precioFinal;

        numeros.add(contadorEntradas);
        ubicaciones.add(ubicacion);
        tiposClientes.add(tipoCliente);
        preciosFinales.add(precioFinal);

        System.out.printf("Entrada vendida con éxito. Número: %d, Ubicación: %s, Tipo: %s, Precio final: $%.2f\n",
                contadorEntradas, ubicacion, tipoCliente, precioFinal);

        if (entradasDisponibles == 0) {
            System.out.println("¡Última entrada vendida! No hay más disponibles.");
        }
    }

    private static void mostrarPromociones() {
        System.out.println("PROMOCIONES DISPONIBLES:");
        System.out.println("- 10% de descuento para estudiantes.");
        System.out.println("- 15% de descuento para personas de la tercera edad.");
        System.out.println("- Compra más de 5 entradas y obtén una sorpresa especial (próximamente).\n");
    }

    private static void buscarEntrada(Scanner scanner) {
        System.out.print("Ingrese número, ubicación o tipo de cliente: ");
        String criterio = scanner.nextLine().toLowerCase();
        boolean encontrada = false;

        for (int i = 0; i < numeros.size(); i++) {
            String num = String.valueOf(numeros.get(i));
            String ubic = ubicaciones.get(i);
            String tipo = tiposClientes.get(i);

            if (num.equals(criterio) || ubic.equals(criterio) || tipo.equals(criterio)) {
                mostrarEntrada(i);
                encontrada = true;
            }
        }

        if (!encontrada) {
            System.out.println("No se encontraron entradas con ese criterio.");
        }
    }

    private static void eliminarEntrada(Scanner scanner) {
        System.out.print("Ingrese el número de entrada a eliminar: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numeros.size(); i++) {
            if (numeros.get(i) == numero) {
                ingresosTotales -= preciosFinales.get(i);
                entradasDisponibles++;

                numeros.remove(i);
                ubicaciones.remove(i);
                tiposClientes.remove(i);
                preciosFinales.remove(i);

                System.out.println("Entrada eliminada con éxito.");
                return;
            }
        }
        System.out.println("No se encontró una entrada con ese número.");
    }

    private static void mostrarEntrada(int i) {
        System.out.printf("Entrada %d | Ubicación: %s | Tipo: %s | Precio: $%.2f\n",
                numeros.get(i), ubicaciones.get(i), tiposClientes.get(i), preciosFinales.get(i));
    }

    private static void mostrarTodasLasEntradas() {
        if (numeros.isEmpty()) {
            System.out.println("No se han vendido entradas todavía.");
        } else {
            System.out.println("Listado de entradas vendidas:");
            for (int i = 0; i < numeros.size(); i++) {
                mostrarEntrada(i);
            }
        }
    }
}
