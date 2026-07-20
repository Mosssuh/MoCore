package mv.mossuh.mocore.ACTIONS.OtherUtil;

public class MoCooldown {

    private long cooldown = 0;
    private boolean bypass = false;
    private String message = "";
    public MoCooldown(Long cooldown, Boolean bypass, String message) {
        if (cooldown != null) { this.cooldown = cooldown; }
        if (bypass != null) { this.bypass = bypass; }
        if (message != null) { this.message = message; }
    }

    public long getCooldown() { return cooldown; }
    public boolean isByPass() { return bypass; }
    public String getMessage() { return message; }
    public boolean isCooldown() {
        return cooldown > 0;
    }
    public boolean hasMessage() {
        return !message.isEmpty();
    }
}
