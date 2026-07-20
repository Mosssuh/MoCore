package mv.mossuh.mocore.ACTIONS.RequirementUtil;

import mv.mossuh.mocore.ENUMS.RequirementType;

public interface Requirement {
    boolean hasRequirements();
    RequirementType getRequirementType();
}
