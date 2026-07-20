package mv.mossuh.mocore.ACTIONS.ActionUtil;

import mv.mossuh.mocore.ACTIONS.OtherUtil.MoCooldown;
import mv.mossuh.mocore.ACTIONS.RequirementUtil.MoRequirements;
import mv.mossuh.mocore.ACTIONS.RewardUtil.MoRewards;
import mv.mossuh.mocore.ENUMS.EventType;

public class MoAction {


    private String actionName = "invalid";
    private boolean cancelAction = false;
    private MoCooldown cooldown = new MoCooldown(null, null, null);
    private EventType eventType = EventType.INVALID;
    private MoRequirements requirements = new MoRequirements(null, null);
    private MoRewards rewards = new MoRewards(null, null);
    private MoRewards elseRewards = new MoRewards(null, null);

    public MoAction(String actionName, Boolean cancelAction, MoCooldown MoCooldown, EventType eventType, MoRequirements requirements, MoRewards rewards, MoRewards elseRewards) {
        if (actionName != null) { this.actionName = actionName; }
        if (cancelAction != null) { this.cancelAction = cancelAction; }
        if (MoCooldown != null) { this.cooldown = MoCooldown; }
        if (eventType != null) { this.eventType = eventType; }
        if (requirements != null) { this.requirements = requirements; }
        if (rewards != null) { this.rewards = rewards; }
        if (elseRewards != null) { this.elseRewards = elseRewards; }
    }

    public boolean isAction() {
        return !actionName.equals("invalid");
    }

    public String getActionName() {
        return actionName;
    }
    public boolean isCancelled() {
        return cancelAction;
    }

    public MoCooldown getCooldown() { return cooldown; }

    public EventType getEventType() { return eventType; }

    public MoRequirements getRequirements() {
        return requirements;
    }
    public MoRewards getRewards() {
        return rewards;
    }
    public MoRewards getElseRewards() { return elseRewards; }
}
