import java.util.Stack;

public class Calculator {
    public java.util.Stack<Double> numbers;
    public java.util.Stack<Operator> operators;
    public java.util.LinkedList<Double> results;

    Calculator(){
        this.numbers = new java.util.Stack<>();
        this.operators = new java.util.Stack<>();
        this.results = new java.util.LinkedList<>();
    }

    public void pushDouble(double d){
        this.numbers.push(d);
    }

    public void pushOperator(Operator o){
        this.operators.push(o);
    }

    public double getResult(){
        if(this.numbers.empty()) throw new RuntimeException();
        return this.numbers.peek();
    }

    public int precedence(Operator op){
        if(op == Operator.PLUS || op == Operator.MINUS) return 2;
        if(op == Operator.MULT || op == Operator.DIV) return 3;
        return 0;
    }


    void executeBinOperator(Operator op) {
        double d1, d2, r;
        d1 = this.getResult();
        this.numbers.pop();
        d2 = this.getResult();
        this.numbers.pop();
        r = 0;
        switch (op){
            case DIV:
                r = d2 / d1;
                break;
            case MULT:
                r = d2 * d1;
                break;
            case PLUS:
                r = d2 + d1;
                break;
            case MINUS:
                r = d2 - d1;
                break;
        }
        this.numbers.push(r);
    }

    public void commandDouble(double d){
        pushDouble(d);
    }

    void commandOperator(Operator op){
        if(this.operators.empty()) pushOperator(op);
        else{
            while (!operators.empty() && precedence(op) <= precedence(this.operators.peek())) executeBinOperator(this.operators.pop());
            pushOperator(op);
        }
    }

    public void commandEqual(){
        while (!this.operators.empty()){
            this.executeBinOperator(this.operators.pop());
        }
        results.add(this.getResult());
    }

    void commandReadMemory(int i){
        this.commandDouble(this.results.get(this.results.size()-i));
    }

    void commandLPar(){
        pushOperator(Operator.OPEN);
    }

    void commandRPar(){
        while (this.operators.peek() != Operator.OPEN){
            this.executeBinOperator(this.operators.pop());
        }
        this.operators.pop();
    }

    void commandInit(){
        this.numbers = new java.util.Stack<>();
        this.operators = new java.util.Stack<>();
    }

    public String toString(){
        return this.numbers.toString() + "\n" + this.operators.toString();
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        calc.commandDouble(1.0);
        System.out.println(calc);
        calc.commandEqual();
        System.out.println(calc);
    }
}
