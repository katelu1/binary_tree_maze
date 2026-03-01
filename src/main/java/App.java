import project5.BST;


public class App {
    public static void main(String[] args) {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        
        for (String element : elements) {
            bst.add(element);
        }

        System.out.println(bst.toStringTreeFormat());
    }
}