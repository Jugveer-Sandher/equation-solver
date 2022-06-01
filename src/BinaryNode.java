public class BinaryNode {
    private int value; // the associated value of this subtree
    private String token; // â€˜aâ€™, â€˜+â€™, â€˜3â€™
    private boolean isOperator; // internal or leaf node

    public BinaryNode right; // right child
    public BinaryNode left; // left child


    //Constructor to create internal node (operators)
    public BinaryNode(String token, BinaryNode lt, BinaryNode rt , boolean isOp) {
        this.token = token;
        this.isOperator = true;
        this.left    = lt;
        this.right   = rt;
        this.value = 0;   //if the node is corresponding to an operator the value will be calculated later
    }

    //Constructor to create leaf nodes (operands)
    public BinaryNode(String token, BinaryNode lt, BinaryNode rt , int value) {
        this.token = token;
        this.isOperator = false;
        this.left    = lt;
        this.right   = rt;
        this.value = value;
    }

    public void printInOrder() {
        if( left != null )
            left.printInOrder( );            // Left
        System.out.print( token + " " );       // Node
        if( right != null )
            right.printInOrder( );
    }

    public void printPostOrder() {
        if (left != null) {
            left.printPostOrder();
        }
        if (right != null) {
            right.printPostOrder();
        }
        System.out.println(token + " ");
    }

    public void printPreOrder() {
        System.out.println(token + " ");
        if (left != null) {
            left.printPostOrder();
        }
        if (right != null) {
            right.printPostOrder();
        }
    }

    public int evaluate() {
        if (left == null && right == null) {
            return Integer.parseInt(token);
        }
        int leftEval = 0;
        int rightEval = 0;
        if (left != null) {
            leftEval = left.evaluate();
        }
        if (right != null) {
            rightEval = right.evaluate();
        }
        switch (token) {
            case"+":
                 return leftEval + rightEval;
            case"-":
                return leftEval - rightEval;
            case"*":
                return leftEval * rightEval;
            case"/":
                return leftEval / rightEval;
            case"%":
                return leftEval % rightEval;
            default:
                System.err.println("No such operator: " + token);
                System.exit(-1);
                return 0;
        }
    }
}