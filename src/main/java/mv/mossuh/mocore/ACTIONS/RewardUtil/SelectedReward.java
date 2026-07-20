package mv.mossuh.mocore.ACTIONS.RewardUtil;

import mv.mossuh.mocore.ENUMS.RewardReceiverType;
import mv.mossuh.mocore.ENUMS.RewardType;
import mv.mossuh.mocore.UTILITIES.REWARDS.RewardReceiver;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SelectedReward {

    private RewardType rewardType = RewardType.NONE;
    private RewardReceiver rewardReceiver = new RewardReceiver(null, null);
    private String reward = null;
    public SelectedReward(RewardType rewardType, RewardReceiver rewardReceiver, String reward) {
        if (rewardType != null) { this.rewardType = rewardType; }
        if (rewardReceiver != null) { this.rewardReceiver = rewardReceiver; }
        if (reward != null) { this.reward = reward; }
    }

    public RewardType getRewardType() {
        return rewardType;
    }
    public RewardReceiver getRewardReceiver() {
        return rewardReceiver;
    }
    public String getReward() {
        return reward;
    }
    public boolean hasReward() {
        if (reward != null) {
            return true;
        }
        return false;
    }


    public static List<SelectedReward> transformToRewards(String[] separatedRewards) {
        List<SelectedReward> selectedRewards = new ArrayList<>();

        for (String separatedReward : separatedRewards) {
            String[] rewardSplit = separatedReward.split("->", 2);
            if (rewardSplit[0] == null) {
                continue;
            }

            String[] rewardSplit2 = rewardSplit[0].replace(" ", "").split(":", 2);
            RewardType rewardType = RewardType.valueOf(rewardSplit2[0].toUpperCase());
            RewardReceiver rewardReceiver = getRewardReceiver(rewardSplit2);

            if (rewardSplit.length == 2) {
                String rewardString = null;
                if (rewardSplit[1].startsWith(" ")) {
                    rewardString = rewardSplit[1].replaceFirst(" ", "");
                }
                SelectedReward selectedReward = new SelectedReward(rewardType, rewardReceiver, rewardString);
                selectedRewards.add(selectedReward);
            } else {
                SelectedReward selectedReward = new SelectedReward(rewardType, rewardReceiver, "");
                selectedRewards.add(selectedReward);
            }
        }
        return selectedRewards;
    }

    @NotNull
    private static RewardReceiver getRewardReceiver(String[] rewardSplit2) {
        RewardReceiverType rewardReceiverType = RewardReceiverType.PLAYER;
        String rewardReceiverString = null;
        if (rewardSplit2.length == 2) {
            String receiver = rewardSplit2[1];
            if (!receiver.isEmpty()) {
                if (receiver.equalsIgnoreCase("target")) {
                    rewardReceiverType = RewardReceiverType.TARGET;
                } else {
                    rewardReceiverType = RewardReceiverType.PLACEHOLDER;
                    rewardReceiverString = receiver;
                }
            } else {
                rewardReceiverType = RewardReceiverType.NONE;
            }
        }

        return new RewardReceiver(rewardReceiverType, rewardReceiverString);
    }
}
