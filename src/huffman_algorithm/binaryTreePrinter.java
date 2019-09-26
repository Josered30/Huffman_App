package huffman_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class binaryTreePrinter {


    public static <T extends Comparable<T>> void printNode(node<T> root) {
        int maxLevel = binaryTreePrinter.maxLevel(root);
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<T>> void printNodeInternal(List<node<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || binaryTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        binaryTreePrinter.printWhitespaces(firstSpaces);

        List<node<T>> newNodes = new ArrayList<node<T>>();

        for (node<T> node : nodes) {
            if (node != null) {
                System.out.print(node.data+" "+node._char);
                newNodes.add(node.left_child);
                newNodes.add(node.right_child);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            binaryTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                binaryTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    binaryTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left_child != null)
                    System.out.print("/");
                else
                    binaryTreePrinter.printWhitespaces(1);

                binaryTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right_child != null)
                    System.out.print("\\");
                else
                    binaryTreePrinter.printWhitespaces(1);

                binaryTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<T>> int maxLevel(node<T> node) {
        if (node == null)
            return 0;

        return Math.max(binaryTreePrinter.maxLevel(node.left_child), binaryTreePrinter.maxLevel(node.right_child)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}