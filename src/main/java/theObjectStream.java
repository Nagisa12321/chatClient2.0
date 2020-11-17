import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class theObjectStream {
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    public theObjectStream(ObjectOutputStream out, ObjectInputStream in) {
        this.out = out;
        this.in = in;
    }

    public ObjectInputStream getObjectInputStream() {
        return in;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return out;
    }
}
