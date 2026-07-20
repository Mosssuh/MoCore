package mv.mossuh.mocore.ACTIONS.RewardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AvailableRewards {

    private static final Random random = new Random();
    private List<SelectedReward> availableSelectedRewards = new ArrayList<>();
    public AvailableRewards(List<SelectedReward> availableSelectedRewards) {
        if (availableSelectedRewards != null) {
            this.availableSelectedRewards = availableSelectedRewards;
        }
    }

    public List<SelectedReward> getAvailableRewards() {
        return availableSelectedRewards;
    }

    public SelectedReward getReward(int position_list) {
        return availableSelectedRewards.get(position_list);
    }

    public boolean hasReward() {
        if (!availableSelectedRewards.isEmpty()) {
            return true;
        }
        return false;
    }
    public SelectedReward getRandomReward() {
        int randomIndex = random.nextInt(availableSelectedRewards.size());
        return availableSelectedRewards.get(randomIndex);
    }
}
