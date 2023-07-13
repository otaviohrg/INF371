import edu.polytechnique.xvm.asm.opcodes.*;
import java.util.Optional;

@SuppressWarnings("unused")
public final class IReturn extends AbstractInstruction {
  public final Optional<AbstractExpr> result; // Value to return

  public IReturn() {
    this(Optional.empty());
  }

  public IReturn(Optional<AbstractExpr> result) {
    this.result = result;
  }

  @Override
  public void codegen(CodeGen cg) {
    if(result.isPresent()){
      result.get().codegen(cg);
      cg.pushInstruction(new PXR());
    }
    cg.pushInstruction(new GTO(cg.returnLabel));
  }
}
