package mv.mossuh.mocore.ACTIONS.RewardUtil;

import mv.mossuh.mocore.ENUMS.ChanceType;

import java.util.ArrayList;
import java.util.List;

public class MoReward {

    private ChanceType chanceType = ChanceType.NONE;
    private String chance = "100";
    private List<AvailableRewards> availableRewards = new ArrayList<>();
    public MoReward(ChanceType chanceType, String chance, List<AvailableRewards> availableRewards) {
        if (chanceType != null) { this.chanceType = chanceType; }
        if (chance != null) { this.chance = chance; }
        if (availableRewards !=  null) { this.availableRewards = availableRewards; }
    }

    public ChanceType getChanceType() { return chanceType; }
    public String getChance() { return chance; }
    public List<AvailableRewards> getAvailableRewards() { return availableRewards; }

}
