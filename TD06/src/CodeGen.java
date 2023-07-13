import java.util.*;

import edu.polytechnique.xvm.asm.*;
import edu.polytechnique.xvm.asm.interfaces.*;

public final class CodeGen {
  private Vector<AsmInstruction> instructions;
  private Map<String, Integer>   labels;
  private Map<String, Integer>   offsets;

  private Map<String, RecordsInfo> types;


  public String returnLabel;
  public String methodName;

  public CodeGen() {
    this.instructions = new Vector<AsmInstruction>();
    this.labels = new HashMap<String, Integer>();
    this.offsets = new HashMap<String, Integer>();
    this.types = new HashMap<String, RecordsInfo>();
  }

  public CodeGen(Map<String, RecordsInfo> types) {
    this();
    this.types = types;
  }

  @SuppressWarnings("unused")
  private static int labelc = 0;

  public static String generateLabel() {
    return "l" + labelc++;
  }

  public void pushLabel(String label) {
    labels.put(label,instructions.size());
  }

  public void pushInstruction(AsmInstruction asm) {
    instructions.add(asm);
  }

  public void pushLocalVariable(String name, int offset) {
    offsets.put(name, offset);
  }

  public int getOffset(String name){
    return offsets.get(name);
  }

  public RecordsInfo getRecord(String name){
    return types.get(name);
  }
  
  public void clearLocals() {
    this.offsets.clear();
  }
  
  @Override
  public String toString() {
    return Printer.programToString(this.instructions, this.labels);
  }
}
