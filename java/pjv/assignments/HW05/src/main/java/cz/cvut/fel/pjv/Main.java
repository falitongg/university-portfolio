package cz.cvut.fel.pjv;
import cz.cvut.fel.pjv.impl.TreeImpl;
public class Main {
    public static void main(String[] args) {
        Tree tree = new TreeImpl();
        int[] values = {1, 2, 3, 4, 5, 6, 7};
        tree.setTree(values);
        System.out.println(tree.toString());
    }
}
