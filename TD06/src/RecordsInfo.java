import java.util.*;

import edu.polytechnique.mjava.ast.TType;
import edu.polytechnique.mjava.ast.TTypeDef;
import org.antlr.v4.runtime.misc.Pair;

public class RecordsInfo {
  public TTypeDef             definition;
  public int                  size;
  public Map<String, Integer> offsets;

  public RecordsInfo(TTypeDef definition, int size, Map<String, Integer> offsets)
  {
    this.definition = definition;
    this.size = size;
    this.offsets = offsets;
  }

  public static void visit1(Map<String, TTypeDef> mtypes,
                            Map<String, RecordsInfo> infos,
                            TTypeDef ty)
  {
    // Compute the informations (size & field offsets) of `ty`
    // and store it in `infos`. The map `mtypes` contains all
    // the records declarations.
    Map<String, Integer> offsetsTy = new HashMap<>();
    int i = 0;
    Optional<TTypeDef> parent = ty.getParent();
    Stack<TTypeDef> types = new Stack<>();
    types.push(ty);
    while (parent.isPresent()){
      types.push(parent.get());
      parent = parent.get().getParent();
    }
    while (!types.empty()){
      TTypeDef record = types.pop();
      for (Pair<TType, String> type : mtypes.get(record.getName()).getFields()){
        offsetsTy.putIfAbsent(type.b, i);
        i++;
      }
    }
    RecordsInfo recordTy = new RecordsInfo(ty, i++, offsetsTy);
    infos.put(ty.getName(), recordTy);
  }

  public static Map<String, RecordsInfo> visit(List<TTypeDef> types) {
    Map<String, TTypeDef> mtypes
        = new HashMap<String, TTypeDef>();
    Map<String, RecordsInfo> result
        = new HashMap<String, RecordsInfo>();

    for (TTypeDef def : types)
      mtypes.put(def.getName(), def);
    for (TTypeDef def : types)
      visit1(mtypes, result, def);
    return result;
  }
}
