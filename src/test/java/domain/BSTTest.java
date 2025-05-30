package domain;

import org.junit.jupiter.api.Test;
import util.Utility;

import java.util.ArrayList;
import java.util.List;

class BSTTest {
    @Test
    void testBST() {
        BST bst = new BST();
        // a. Crear 3 árboles para insertar en bst (el árbol principal)
        BTree arbolNumeros = new BTree();
        BST arbolLetras = new BST();
        BTree arbolNombres = new BTree();

        // a.1. Insertar 100 números aleatorios entre 200 y 500 en arbolNumeros
        for (int i = 0; i < 100; i++)
            arbolNumeros.add(Utility.Random(200, 500));

        // a.2. Insertar letras del alfabeto en arbolLetras (BST)
        for (char letra = 'A'; letra <= 'Z'; letra++) {
            try {
                arbolLetras.add(letra);
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }

        // a.3. Insertar 10 nombres en arbolNombres
        String[] nombres = {"Ana", "Luis", "Pedro", "Maria", "Carlos", "Lucia", "Juan", "Marta", "Diego", "Elena"};
        for (String nombre : nombres)
            arbolNombres.add(nombre);

        // Insertar los 3 árboles en el BST principal, usando su tamaño como criterio
        try {
            bst.add(arbolNumeros);
            bst.add(arbolLetras);
            bst.add(arbolNombres);
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }

        // b. toString() y recorridos
        System.out.println("Contenido del árbol BST con árboles insertados:");
        System.out.println(bst);

        // c. size, min, max
        try {
            System.out.println("Tamaño: " + bst.size());
            System.out.println("Mínimo (árbol con menor tamaño): " + ((Tree) bst.min()).size());
            System.out.println("Máximo (árbol con mayor tamaño): " + ((Tree) bst.max()).size());
        } catch (TreeException e) {
            e.printStackTrace();
        }

        // d. contains para los árboles insertados
        try {
            System.out.println("¿Contiene arbolNumeros?: " + bst.contains(arbolNumeros));
            System.out.println("¿Contiene arbolLetras?: " + bst.contains(arbolLetras));
            System.out.println("¿Contiene arbolNombres?: " + bst.contains(arbolNombres));
        } catch (TreeException e) {
            e.printStackTrace();
        }

        // d. contains para verificar existencia de 5 elementos de cada tipo
        System.out.println("\nVerificación de existencia (contains):");
        try {
            for (int i = 0; i < 5; i++) {
                int num = Utility.Random(200, 500);
                System.out.println(((Tree)bst.max()).contains(num)
                        ? "Árbol binario simple contiene " + num
                        : "Árbol binario simple no contine " + num
                );
                char c = (char) (65+i);
                System.out.println(((Tree)bst.root.left.data).contains(c)
                        ? "Árbol binario de búsqueda contiene " + c
                        : "Árbol binario de búsqueda no contine " + c
                );
                String nombre = nombres[i];
                System.out.println(((Tree)bst.min()).contains(nombre)
                        ? "Árbol binario simple contiene " + nombre
                        : "Árbol binario simple no contine " + nombre
                );
            }
        } catch (TreeException e) {
            System.out.println("Error en contains: " + e.getMessage());
        }

        // e. Insertar 10 números aleatorios entre 10 y 50 como árboles pequeños
        for (int i = 0; i < 10; i++) {
            BTree arbolTemp = new BTree();
            try {
                arbolTemp.add(Utility.Random(10, 50));
                bst.add(arbolTemp);
            } catch (TreeException e) {
                throw new RuntimeException(e);
            }
        }

        // f. Verificar si el árbol está balanceado
        System.out.println("¿Está balanceado?: " + bst.isBalanced());

        // g. Eliminar 5 árboles (uno de los ya insertados)
        try {
            bst.remove(arbolNumeros);
            bst.remove(arbolLetras);
            bst.remove(arbolNombres);
            // eliminar otros 2 pequeños árboles agregados
            bst.remove(bst.max());
            bst.remove(bst.max());
            System.out.println("Se eliminaron 5 árboles.");
        } catch (TreeException e) {
            e.printStackTrace();
        }

        // h. Mostrar contenido del árbol
        System.out.println("Contenido después de eliminar:");
        System.out.println(bst);

        // i. Verificar si está balanceado
        System.out.println("¿Está balanceado ahora?: " + bst.isBalanced());

        // j. Mostrar altura de cada árbol restante en el BST
        try {
            for (Object o : obtenerElementos(bst)) {
                int altura = bst.height(o);
                System.out.println("Altura del árbol con tamaño " + ((Tree) o).size() + ": " + altura);
            }
        } catch (TreeException e) {
            e.printStackTrace();
        }
    }

    // Metodo auxiliar para recorrer el árbol y obtener los objetos almacenados
    private static List<Object> obtenerElementos(BST bst) throws TreeException {
        List<Object> elementos = new ArrayList<>();
        recorrerInOrder(bst.getRoot(), elementos);
        return elementos;
    }

    private static void recorrerInOrder(BTreeNode node, List<Object> elementos) {
        if (node != null) {
            recorrerInOrder(node.left, elementos);
            elementos.add(node.data);
            recorrerInOrder(node.right, elementos);
        }
    }

    @Test
    void test() throws TreeException {
        BST bst = new BST();
        for (int i = 0; i <20 ; i++) {
            bst.add(util.Utility.random(50)+1);
        }
        System.out.println(bst); //toString
        try {
            System.out.println("BST size: "+bst.size()+". BST height: "+bst.height());
            System.out.println("BST min: " + bst.min() + ". BST max: " + bst.max());

        } catch (TreeException e) {
            throw new RuntimeException(e);
        }
    }
}