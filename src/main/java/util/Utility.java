package util;

import domain.Tree;
import domain.TreeException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class Utility {
    private static Random random;

    //constructor estatico, inicializador estatico
    static {
        // semilla para el random
        long seed = System.currentTimeMillis();
        random = new Random(seed);

    }

    // Método para generar un número aleatorio en un rango
    public static int Random(int min, int max) {
        // Generación de un número aleatorio en el rango [min, max]
        return 1 + random.nextInt(max - min + 1);
    }

    public static int random(int bound) {
        //return (int)Math.floor(Math.random()*bound); //forma 1
        return 1 + random.nextInt(bound);
    }

    // Método para llenar un arreglo con valores aleatorios
    public static void fillArray(int[] a, int min, int max) {
        for (int i = 0; i < a.length; i++) {
            a[i] = Random(min, max);
        }
    }

    public static void fill(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = random(99);
        }
    }

    // Método para formatear números largos
    public static String format(long n) {
        return new DecimalFormat("###,###,###.##").format(n);
    }

    // Método para obtener el mínimo de dos números
    public static int min(int x, int y) {
        return (x < y) ? x : y;
    }

    // Método para obtener el máximo de dos números
    public static int max(int x, int y) {
        return (x > y) ? x : y;
    }

    // Método para mostrar el contenido de un arreglo
    public static String show(int[] a) {
        String result = "";
        for (int item : a) {
            if (item == 0) break;
            result += item + " ";
        }
        return result.trim(); // Elimina el espacio extra al final
    }

    // Método para comparar dos objetos de distintos tipos
    public static int compare(Object a, Object b) throws TreeException {
        switch (instanceOf(a, b)) {
            case "Integer":
                Integer int1 = (Integer) a;
                Integer int2 = (Integer) b;
                return int1 < int2 ? -1 : int1 > int2 ? 1 : 0;
            case "String":
                String str1 = (String) a;
                String str2 = (String) b;
                return str1.compareTo(str2) < 0 ? -1 : str1.compareTo(str2) > 0 ? 1 : 0;
            case "Character":
                Character ch1 = (Character) a;
                Character ch2 = (Character) b;
                return ch1.compareTo(ch2) < 0 ? -1 : ch1.compareTo(ch2) > 0 ? 1 : 0;
            case "Tree":
                int size1 = ((Tree) a).size();
                int size2 = ((Tree) b).size();
                return size1 < size2 ? -1 : size1 > size2 ? 1 : 0;
            default:
                return 2; // Unknown
        }
    }

    private static int getPriority(char c) {
        return switch (c) {
            case '+', '-' -> 1; //prioridad mas baja
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> 0;
        };
    }

    // Método para obtener el tipo de instancia de dos objetos
    public static String instanceOf(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) return "Integer";
        if (a instanceof String && b instanceof String) return "String";
        if (a instanceof Character && b instanceof Character) return "Character";
        if (a instanceof Tree && b instanceof Tree) return "Tree";
        return "Unknown";
    }
}

