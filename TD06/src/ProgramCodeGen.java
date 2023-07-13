import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.polytechnique.mjava.ast.TProcDef;
import edu.polytechnique.mjava.ast.VarDecl;
import edu.polytechnique.xvm.asm.opcodes.*;

public class ProgramCodeGen {
  public final CodeGen cg;

  public static String labelOfProcName(String name) {
    return String.format("__%s", name);
  }

  @SuppressWarnings("unused")
  public void codegen(TProcDef<AbstractExpr, AbstractInstruction> proc) {
    final List<VarDecl> args = proc.getArgs(); // Proc. arguments
    final List<VarDecl> locals = proc.getLocals(); // Proc. locals
    final AbstractInstruction is = proc.getBody(); // Proc. body

    cg.returnLabel = CodeGen.generateLabel();
    cg.pushLabel(labelOfProcName(proc.getName()));
    //cg.pushInstruction(new PUSH(0));
    //cg.pushInstruction(new PXR());
    for(int i=0; i<args.size(); i++) {
      cg.pushLocalVariable(args.get(i).getName(), i-args.size());
    }
    for(int j=0; j<locals.size(); j++) {
      cg.pushLocalVariable(locals.get(j).getName(), j+2);
      cg.pushInstruction(new PUSH(0));
    }
    is.codegen(cg);
    cg.clearLocals();
    cg.pushLabel(cg.returnLabel);
    cg.pushInstruction(new RET());

  }

  public void codegen(List<TProcDef<AbstractExpr, AbstractInstruction>> procs) {
    for (TProcDef<AbstractExpr, AbstractInstruction> proc : procs)
      this.codegen(proc);
  }

  public ProgramCodeGen() {
    this.cg = new CodeGen();
    this.cg.pushInstruction(new GSB(labelOfProcName("main")));
    this.cg.pushInstruction(new STOP());
  }

  public ProgramCodeGen(Map<String, RecordsInfo> types) {
    this.cg = new CodeGen(types);
    this.cg.pushInstruction(new GSB(labelOfProcName("main")));
    this.cg.pushInstruction(new STOP());
  }
}
