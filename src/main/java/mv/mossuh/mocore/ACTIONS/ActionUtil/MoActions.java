package mv.mossuh.mocore.ACTIONS.ActionUtil;


import java.util.ArrayList;
import java.util.List;

public class MoActions {

    private List<MoAction> actions = new ArrayList<>();

    public MoActions(List<MoAction> actions) {
        if (actions != null) { this.actions = actions; }
    }

    public List<MoAction> getActions() {
        return actions;
    }
    public MoAction getAction(String actionName) {
        for (MoAction action : actions) {
            String name = action.getActionName();
            if (name.equalsIgnoreCase(actionName)) {
                return action;
            }
        }
        return new MoAction(null, null, null, null, null, null, null);
    }
}
