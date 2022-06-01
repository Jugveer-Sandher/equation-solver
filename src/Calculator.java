
import org.w3c.dom.Node;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    private ExpressionTree expTree;
    private HashMap<String, String> variableTable;

    public Calculator() {
        variableTable = new HashMap<String, String>();
        expTree = null;
    }
    //Add any other method that you need


    /*
     * run() is the main method which will be called to evaluate an input file
     * It should:
     * 	read the file,
     *      store the variables in the hash (VariableTable)
     *      build the expression tree, like:   expTree = stringToExpressionTree()
     *      evaluate tree, you should have a method evaluate for class ExpressionTree
     *			like:   result = expTree.evaluate()
     *      print result
     */
    public void run(String inputFile) throws FileNotFoundException {
        //to be implemented
        Scanner scanner = new Scanner(new FileReader(inputFile));

        while (scanner.hasNext()) {
            String next = scanner.nextLine();
            if (!next.contains("=")) {
                ExpressionTree tree = stringToExpressionTree(next);
                System.out.println(tree.evaluate());
            }
            else {
                String[] vars = next.split(" ");
                String var = vars[0];
                String value = vars[2];
                variableTable.put(var, value);
            }
        }
    }

    private int precedenceLevel(String s) {
        switch (s) {
            case"+":
            case"-":
                return 1;
            case"*":
            case"/":
            case"%":
                return 2;
            case"(":
                return 0;
            default:
                System.err.println("No such operator: " + s);
                System.exit(-1);
                return -1;
        }
    }

    /*
     * method to convert a string to an expression tree
     */
    private ExpressionTree stringToExpressionTree(String infixExpression){
        Stack<ExpressionTree> treeStack = new Stack<ExpressionTree>();
        Stack<String> opStack = new Stack<String>();
        ExpressionTree nullTree = new ExpressionTree();
        ExpressionTree t = new ExpressionTree(null, 0);
        ExpressionTree t1, t2;
        String operator;

        /* you can convert the infixExpression to a list of tokens
         * by spliting it based on whitespace
         * for example if the input is "x + ( 10 - y )"
         * list of tokens will be ["x", "+", "(", "10", "-", "y", ")"]
         */
        String[] tokens = infixExpression.trim().split("\\s+");

        for (String token : tokens) {
            if (Character.isLetter(token.charAt(0))) {
                if (variableTable.containsKey(token)) {
                    t = new ExpressionTree(variableTable.get(token), Integer.parseInt(variableTable.get(token)));
                    treeStack.push(t);
                } else {
                    System.err.println("Undefined Variable Exception: the input " + token + " is invalid.");
                    System.exit(-1);
                }
            }
            else if (token.equals("(")) {
                opStack.push(token);
            }
            else if (!nullTree.IsOperator(token) && !token.equals(")")) {
                if (token.matches(".*\\d.*")) {
                    t = new ExpressionTree(token, Integer.parseInt(token));
                    treeStack.push(t);
                }
                else {
                    System.err.println("Syntax Error Exception: the input " + token + " is invalid.");
                    System.exit(-1);
                }
            }
            else if (nullTree.IsOperator(token)) {
                if (opStack.isEmpty()) {
                    opStack.push(token);
                } else {
                    while (precedenceLevel(opStack.peek()) >= precedenceLevel(token) && !opStack.peek().equals(")")) {
                        operator = opStack.pop();
                        if (treeStack.size() < 2) {
                            System.err.println("Syntax Error Exception: Invalid expression uneven amount of operators or parenthesis");
                            System.exit(-1);
                        }
                        t1 = treeStack.pop();
                        t2 = treeStack.pop();
                        t.merge(operator, t2, t1);
                        treeStack.push(t);
                        if (opStack.isEmpty()) {
                            break;
                        }
                    }
                    opStack.push(token);

                }
            }
            else if (token.equals(")")) {
                if (opStack.isEmpty()) {
                    System.err.println("Syntax Error Exception: Invalid expression uneven amount of operators or parenthesis");
                    System.exit(-1);
                }
                while (!opStack.peek().equals("(")) {
                    if (treeStack.size() < 2) {
                        System.err.println("Syntax Error Exception: Invalid expression uneven amount of operators or parenthesis");
                        System.exit(-1);
                    }
                    operator = opStack.pop();
                    t1 = treeStack.pop();
                    t2 = treeStack.pop();
                    t.merge(operator, t2, t1);
                    treeStack.push(t);
                    if (opStack.isEmpty()) {
                        System.err.println("Syntax Error Exception: Invalid expression uneven amount of operators or parenthesis");
                        System.exit(-1);
                    }
                }
                opStack.pop();
            }
        }
        while(!opStack.isEmpty()) {
            if (opStack.peek().equals("(") || treeStack.size() < 2) {
                System.err.println("Syntax Error Exception: Invalid expression uneven amount of operators or parenthesis");
                System.exit(-1);
            }
            operator = opStack.pop();
            t1 = treeStack.pop();
            t2 = treeStack.pop();
            t.merge(operator, t2, t1);
            treeStack.push(t);
        }
        return treeStack.pop();
    }
}