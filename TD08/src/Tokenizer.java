public class Tokenizer {
    public boolean isStart;
    public boolean isIntNum;
    public boolean isNonIntNum;
    public boolean isMinUnary;
    public boolean isNegative;
    public boolean readMemory;
    public double num;
    public int decimalDigits;
    public Calculator calc;

    Tokenizer(){
        this.isStart = true;
        this.isIntNum = false;
        this.isNonIntNum = false;
        this.isMinUnary = true;
        this.isNegative = false;
        this.readMemory = false;
        this.num = 0;
        this.decimalDigits = 0;
        this.calc = new Calculator();
    }

    public void readChar(char c){
        if(Character.isDigit(c)) {
            if(this.isStart){
                this.isStart = false;
                this.isIntNum = true;
                this.isMinUnary = false;
                this.num = Character.getNumericValue(c);
            }
            else {
                this.num = 10 * this.num;
                this.num += Character.getNumericValue(c);
            }
            if(this.isNonIntNum) this.decimalDigits++;
        }
        else if (c == '.'){
            this.isNonIntNum = true;
        }
        else if (c == '-' && this.isMinUnary) this.isNegative = true;
        else {
            if(!this.isStart){
                double negFactor = 1.0;
                if(this.readMemory){
                    calc.commandReadMemory((int) this.num);
                }
                else{
                    if(this.isNegative) negFactor = -1.0;
                    calc.commandDouble(negFactor * this.num/Math.pow(10,this.decimalDigits));
                }
            }
            switch (c){
                case '+':
                    calc.commandOperator(Operator.PLUS);
                    this.isMinUnary = true;
                    break;
                case '-':
                    calc.commandOperator(Operator.MINUS);
                    this.isMinUnary = true;
                    break;
                case '*':
                    calc.commandOperator(Operator.MULT);
                    this.isMinUnary = true;
                    break;
                case '/':
                    calc.commandOperator(Operator.DIV);
                    this.isMinUnary = true;
                    break;
                case '(':
                    calc.commandLPar();
                    this.isMinUnary = true;
                    break;
                case ')':
                    calc.commandRPar();
                    break;
                case '=':
                    calc.commandEqual();
                    this.isMinUnary = true;
                    break;
                case 'C':
                    calc.commandInit();
                    this.isMinUnary = true;
                    break;
                case '$':
                    this.readMemory = true;
                    break;
            }
            this.isStart = true;
            this.isIntNum = false;
            this.isNonIntNum = false;
            this.decimalDigits = 0;
            this.isNegative = false;
            this.num = 0;
        }
    }

    void readString(String s){
        s.chars().forEach(c -> readChar((char) c));
    }

    public static void main(String[] args) {
        Tokenizer t = new Tokenizer();
        String chain = "(-0.25-5)-(28-13)=";
        t.readString(chain);
    }
}
