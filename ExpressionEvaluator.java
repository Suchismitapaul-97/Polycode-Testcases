import java.util.*;

public class ExpressionEvaluator {

    public int evaluate(String expr) {
        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            if (Character.isDigit(ch)) {
                int num = 0;
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    num = num * 10 + (expr.charAt(i++) - '0');
                }
                nums.push(num);
                i--; // adjust for loop
            } else if (ch == '+' || ch == '-') {
                while (!ops.isEmpty()) applyOp(nums, ops.pop());
                ops.push(ch);
            }
        }

        while (!ops.isEmpty()) {
            applyOp(nums, ops.pop());
        }

        return nums.pop();
    }

    private void applyOp(Stack<Integer> nums, char op) {
        int b = nums.pop();
        int a = nums.pop();
        if (op == '+') nums.push(a + b);
        else if (op == '-') nums.push(a - b);
    }
}
