package mv.mossuh.mocore.UTILITIES.REWARDS;

import mv.mossuh.mocore.ENUMS.RewardReceiverType;

public class RewardReceiver {

    private RewardReceiverType rewardReceiverType = RewardReceiverType.NONE;
    private String rewardReceiver = "";
    public RewardReceiver(RewardReceiverType rewardReceiverType, String rewardReceiver) {
        if (rewardReceiverType != null) { this.rewardReceiverType = rewardReceiverType; }
        if (rewardReceiver != null) { this.rewardReceiver = rewardReceiver; }
    }

    public RewardReceiverType getRewardReceiverType() { return rewardReceiverType; }
    public String getRewardReceiver() { return rewardReceiver; }
    public boolean hasRewardReceiver() { return !rewardReceiver.isEmpty(); }
}
