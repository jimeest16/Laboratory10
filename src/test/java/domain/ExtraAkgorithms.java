package domain;

import org.junit.jupiter.api.Test;

public class ExtraAkgorithms {

    @Test
    public void test() {
        try {
            AVL tree = new AVL();

            // Insertar elementos para formar el árbol
            tree.add(50);
            tree.add(30);
            tree.add(70);
            tree.add(20);
            tree.add(40);
            tree.add(60);
            tree.add(80);

            System.out.println("Padre de 20: " + tree.father(20));
            System.out.println("Padre de 40: " + tree.father(40));
            System.out.println("Hermano de 40: " + tree.brother(40));
            System.out.println("Hijos de 30: " + tree.children(30));
            System.out.println("Hijos de 80: " + tree.children(80));
            System.out.println("Hijos de 25: " + tree.children(25));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
