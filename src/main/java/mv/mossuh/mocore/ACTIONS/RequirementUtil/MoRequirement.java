package mv.mossuh.mocore.ACTIONS.RequirementUtil;

import mv.mossuh.mocore.ENUMS.RequirementType;

public class MoRequirement {


    private RequirementType requirementType = RequirementType.NONE;
    private Requirement requirement = null;

    public MoRequirement(RequirementType requirementType, Requirement requirement) {
        if (requirementType != null) { this.requirementType = requirementType; }
        if (requirement != null) { this.requirement = requirement; }
    }

    public RequirementType getRequirementType() {
        return requirementType;
    }

    public boolean isRequirement() {
        if (!requirementType.equals(RequirementType.NONE)) {
            if (requirement != null) {
                return requirement.getRequirementType() != RequirementType.NONE;
            }
        }
        return false;
    }

    public boolean isRequirement(RequirementType requirementType) {
        return this.requirementType == requirementType && requirement != null && requirement.getRequirementType() == requirementType;
    }
    public Requirement getRequirement() {
        return requirement;
    }
}
