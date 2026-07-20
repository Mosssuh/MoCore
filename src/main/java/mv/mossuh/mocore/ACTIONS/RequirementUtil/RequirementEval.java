package mv.mossuh.mocore.ACTIONS.RequirementUtil;

import mv.mossuh.mocore.ENUMS.RequirementType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequirementEval implements Requirement {

    private static final RequirementType requirementType = RequirementType.EVAL;
    private List<String> availableEvals = new ArrayList<>();
    public RequirementEval(List<String> availableEvals) {
        if (availableEvals != null) {
            this.availableEvals = availableEvals;
        }
    }

    public List<String> getRequirements() {
        return availableEvals;
    }
    public boolean hasRequirements() {
        return !availableEvals.isEmpty();
    }

    public RequirementType getRequirementType() { return requirementType; }

    public static RequirementEval separateEvals(String string) {
        String[] splitString = string.split(" \\|\\| ");
        return new RequirementEval(new ArrayList<>(Arrays.asList(splitString)));
    }
}
