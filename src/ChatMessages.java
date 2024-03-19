import java.util.Observable;
public class ChatMessages extends Observable {
    private String message;

    public ChatMessages() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        setChanged();
        notifyObservers(message);
    }
}