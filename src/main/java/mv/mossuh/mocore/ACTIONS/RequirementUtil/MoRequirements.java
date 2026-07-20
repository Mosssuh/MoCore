package mv.mossuh.mocore.ACTIONS.RequirementUtil;

import mv.mossuh.mocore.UTILITIES.ARGS.VariableArgs.VariableArg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoRequirements {
    private List<MoRequirement> requirements = new ArrayList<>();
    private List<VariableArg> variables = new ArrayList<>();

    public MoRequirements(List<MoRequirement> requirements, List<VariableArg> variables) {
        if (requirements != null) { this.requirements = requirements; }
        if (variables != null) { this.variables = variables; }
    }

    public List<MoRequirement> getRequirements() { return requirements; }

    public void addRequirements(MoRequirement... requirements) {
        this.requirements.addAll(Arrays.asList(requirements));
    }
    public void addRequirements(List<MoRequirement> requirements) {
        this.requirements.addAll(requirements);
    }
    public void removeRequirements(MoRequirement... requirements) {
        this.requirements.removeAll(Arrays.asList(requirements));
    }
    public void setRequirements(List<MoRequirement> requirements) {
        this.requirements.removeAll(requirements);
    }


    public List<VariableArg> getVariables() { return variables; }
    public void addVariables(VariableArg... variables) {
        this.variables.addAll(Arrays.asList(variables));
    }
    public void addVariables(List<VariableArg> variables) {
        this.variables.addAll(variables);
    }

    public void removeVariables(VariableArg... variables) {
        this.variables.removeAll(Arrays.asList(variables));
    }

    public void setVariables(List<VariableArg> variables) {
        if (variables != null) { this.variables = variables; }
    }
}
