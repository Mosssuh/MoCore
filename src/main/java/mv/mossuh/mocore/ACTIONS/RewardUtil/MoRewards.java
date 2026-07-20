package mv.mossuh.mocore.ACTIONS.RewardUtil;

import mv.mossuh.mocore.UTILITIES.ARGS.VariableArgs.VariableArg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoRewards {
    private List<MoReward> rewards = new ArrayList<>();
    private List<VariableArg> variables = new ArrayList<>();

    public MoRewards(List<MoReward> rewards, List<VariableArg> variables) {
        if (rewards != null) { this.rewards = rewards; }
        if (variables != null) { this.variables = variables; }
    }

    public List<MoReward> getRewards() { return rewards; }
    public void addRewards(MoReward... rewards) {
        this.rewards.addAll(Arrays.asList(rewards));
    }
    public void addRewards(List<MoReward> rewards) {
        this.rewards.addAll(rewards);
    }
    public void removeReward(MoReward... rewards) {
        this.rewards.removeAll(Arrays.asList(rewards));
    }
    public void setRewards(List<MoReward> rewards) {
        if (rewards != null) { this.rewards = rewards; }
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
