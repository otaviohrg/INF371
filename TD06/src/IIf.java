import edu.polytechnique.xvm.asm.opcodes.*;

@SuppressWarnings("unused")
public final class IIf extends AbstractInstruction {
  public final AbstractExpr        condition; // condition (<> 0 => true)
  public final AbstractInstruction iftrue   ; // if "true  (<> 0)" branch
  public final AbstractInstruction iffalse  ; // if "false (== 0)" branch

  public IIf(AbstractExpr condition,
             AbstractInstruction iftrue,
             AbstractInstruction iffalse)
  {
    this.condition = condition;
    this.iftrue = iftrue;
    this.iffalse = iffalse;
  }

  @Override
  public void codegen(CodeGen cg) {
    String lblElse = CodeGen.generateLabel();
    String lblEnd = CodeGen.generateLabel();

    this.condition.codegen(cg);
    cg.pushInstruction(new GTZ(lblElse));
    this.iftrue.codegen(cg);
    cg.pushInstruction(new GTO(lblEnd));
    cg.pushLabel(lblElse);
    this.iffalse.codegen(cg);
    cg.pushLabel(lblEnd);

  }
}
