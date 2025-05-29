package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTest {

    @Test
    void test() throws TreeException {
        AVL avl = new AVL();
        for (int i = 0; i < 30; i++) {
            avl.add(util.Utility.Random(20,200));
        }
        System.out.println("-------Tree AVL-------");
        System.out.println(avl); //toString

        System.out.println("Size: "+ avl.size());
        System.out.println("Minimum : "+ avl.min());
        System.out.println("Maximum: "+ avl.max());

        System.out.println("Tree is balanced: "+ avl.isBalanced());

        avl.remove(6);
        avl.remove(24);
        avl.remove(37);
        avl.remove(54);
        avl.remove(67);

        System.out.println(avl);

        System.out.println("Tree is balanced AVL: "+ avl.isAVL());

        System.out.println("----Test method father-------");
        System.out.println(avl.father(20));
        System.out.println("----Test method brother-------");
        System.out.println(avl.brother(30));
        System.out.println("----Test method children-------");
        System.out.println(avl.children(40));
    }
}