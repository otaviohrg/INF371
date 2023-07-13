import edu.polytechnique.xvm.asm.opcodes.*;
import edu.polytechnique.mjava.ast.LValue;
import java.util.Optional;

@SuppressWarnings("unused")
public final class IAssign extends AbstractInstruction {
  public final Optional<LValue<AbstractExpr>> lvalue; // (optional) left-value
  public AbstractExpr           rvalue; // right-value (expression)

  public IAssign(Optional<String> lvalue, AbstractExpr rvalue) {
    this.lvalue = lvalue.map((x) -> new LValue<AbstractExpr>(x));
    this.rvalue = rvalue;
  }

  public IAssign(LValue<AbstractExpr> lvalue, AbstractExpr rvalue) {
    this.lvalue = Optional.of(lvalue);
    this.rvalue = rvalue;
  }

  @Override
  public void codegen(CodeGen cg) {
    boolean isClass = false;
    String register = "";
    rvalue.codegen(cg);
    if(lvalue.isPresent()){
      if(lvalue.get().target.isPresent()){
        if(lvalue.get().target.get().getType().isPresent()){
          isClass = true;
          register = lvalue.get().target.get().getType().get().toString();
          System.out.println(register);
        }
      }
      //if(isClass) new WFR(cg.getOffset(lvalue.get().name)+cg.getRecord(register).offsets.get());
      cg.pushInstruction(new WFR(cg.getOffset(lvalue.get().name)));
    }
    else cg.pushInstruction(new POP());
  }
}
