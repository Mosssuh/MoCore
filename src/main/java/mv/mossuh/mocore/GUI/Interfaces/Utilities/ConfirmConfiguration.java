package mv.mossuh.mocore.GUI.Interfaces.Utilities;

import mv.mossuh.mocore.ITEM.MoSound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfirmConfiguration {
    private List<String> requestingMessage = new ArrayList<>();
    private String cancellerMessage = "cancel";
    private String cancelledMessage = "";
    private String wrongAnswerMessage = ""; // If the answer is number or something and is typing incorrect characters
    private MoSound wrongAnswerSound = MoSound.WRONG;


    public ConfirmConfiguration(List<String> requestingMessage, String cancellerMessage, String cancelledMessage, String wrongAnswerMessage, MoSound wrongAnswerSound) {
        if (requestingMessage != null) { this.requestingMessage = requestingMessage; }
        if (cancellerMessage != null) { this.cancellerMessage = cancellerMessage; }
        if (cancelledMessage != null) { this.cancelledMessage = cancelledMessage; }
        if (wrongAnswerMessage != null) { this.wrongAnswerMessage = wrongAnswerMessage; }
        if (wrongAnswerSound != null) { this.wrongAnswerSound = wrongAnswerSound; }
    }

    public ConfirmConfiguration(List<String> requestingMessage, String cancellerMessage, String cancelledMessage, String wrongAnswerMessage) {
        if (requestingMessage != null) { this.requestingMessage = requestingMessage; }
        if (cancellerMessage != null) { this.cancellerMessage = cancellerMessage; }
        if (cancelledMessage != null) { this.cancelledMessage = cancelledMessage; }
        if (wrongAnswerMessage != null) { this.wrongAnswerMessage = wrongAnswerMessage; }
    }

    public ConfirmConfiguration() { }

    public List<String> getRequestingMessage() { return requestingMessage; }
    public String getCanceller() { return cancellerMessage; }
    public String getCancelledMessage() { return cancelledMessage; }
    public String getWrongAnswerMessage() { return wrongAnswerMessage; }
    public MoSound getWrongAnswerSound() { return wrongAnswerSound; }

    public void setRequestingMessage(List<String> message) {
        if (message == null) return;
        this.requestingMessage = message;
    }
    public void setRequestingMessage(String... message) {
        if (message == null) return;
        this.requestingMessage = new ArrayList<>(Arrays.asList(message));
    }

    public void setCanceller(String canceller) {
        if (canceller != null) { this.cancellerMessage = canceller; }
    }
    public void setCancelledMessage(String cancelledMessage) {
        if (cancelledMessage != null) { this.cancelledMessage = cancelledMessage; }
    }
    public void setWrongAnswerMessage(String wrongAnswerMessage) {
        if (wrongAnswerMessage != null) { this.wrongAnswerMessage = wrongAnswerMessage; }
    }
    public void setWrongAnswerSound(MoSound sound) {
        if (sound != null) { this.wrongAnswerSound = sound; }
    }

}
