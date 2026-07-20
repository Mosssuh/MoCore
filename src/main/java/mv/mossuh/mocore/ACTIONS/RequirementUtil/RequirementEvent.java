package mv.mossuh.mocore.ACTIONS.RequirementUtil;

import mv.mossuh.mocore.ENUMS.RequirementType;
import mv.mossuh.mocore.UTILITIES.REQUIREMENTS.EntityRequirement;

import java.util.ArrayList;
import java.util.List;

public class RequirementEvent implements Requirement {
    private static final RequirementType requirementType = RequirementType.EVENT;
    private List<EntityRequirement> requirements = new ArrayList<>();
    public RequirementEvent(List<EntityRequirement> requirements) {
        if (requirements != null) { this.requirements = requirements; }
    }


    public List<EntityRequirement> getRequirements() {
        return requirements;
    }

    public boolean hasRequirements() {
        return !requirements.isEmpty();
    }

    @Override
    public RequirementType getRequirementType() {
        return requirementType;
    }


    public static RequirementEvent getRequirementEvent(String requirement) {
        List<EntityRequirement> requirements = EntityRequirement.stringToEntities(requirement);
        return new RequirementEvent(requirements);
    }

}
