import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class EventBuffer {
    PrintWriter file;
    final static String logName = "log_" + new Date();
    final static int BUFFER_SIZE = 2000;
    final static int safeIndex = 1950;
    private Object arrayLock;
    int readBufIndex;
    Event[] readBuffer;
    Event[] writeBuffer;

    public EventBuffer() throws FileNotFoundException {
        arrayLock = new Object();
        this.readBuffer = new Event[BUFFER_SIZE];
        this.writeBuffer = new Event[BUFFER_SIZE];
        file = new PrintWriter(this.logName);
        readBufIndex = -1;
    }

    public EventBuffer(String filename) throws FileNotFoundException {
        arrayLock = new Object();
        this.readBuffer = new Event[BUFFER_SIZE];
        this.writeBuffer = new Event[BUFFER_SIZE];
        Date date = new Date();
        File f = new File("logs\\" + filename + "_" + date);
        if(!f.exists() && !f.isDirectory())
        {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        file = new PrintWriter(f);
        readBufIndex = -1;
    }

    boolean checkEmptyDurations(){
        for (int i = readBufIndex; i > readBufIndex - 10; i--){
            if (readBuffer[i].type == "key_pressed" && readBuffer[i].getDuration() == 0) return false;
        }
        return true;
    }
    public void read(Event e){
        if (readBufIndex >= safeIndex){
            if (readBufIndex == BUFFER_SIZE - 1){
                copyToWriteBuffer();
                new Thread(() -> writeToFile()).start();
            }
            else if(checkEmptyDurations() && e.getType() == "key_pressed") {
                copyToWriteBuffer();
                new Thread(() -> writeToFile()).start();
            }
        }
        readBuffer[++readBufIndex] = e;
    }

    public void updatePressed(Event e){
        for (int i = readBufIndex; i >= 0; i--){
            if (readBuffer[i].item == e.item && readBuffer[i].type == "key_pressed"){
                readBuffer[i].setLinkedEvent(e);
                readBuffer[i].setDuration(e.getTimeStamp() - readBuffer[i].getTimeStamp());
                break;
            }
        }
    }

    public void copyToWriteBuffer() {
        synchronized (arrayLock) {
            for (int i = 0; i < readBufIndex; i++) {
                this.writeBuffer[i] = new Event(this.readBuffer[i]);
            }
            this.readBuffer = new Event[BUFFER_SIZE];
            this.readBufIndex = -1;
        }
    }

    public void writeToFile(){
        for (Event e : this.writeBuffer){
            file.println(e);
        }
        this.writeBuffer = new Event[BUFFER_SIZE];
    }

    public void clearBuffer(){
        copyToWriteBuffer();
        new Thread(() -> writeToFile()).start();
        file.flush();
        file.close();
    }

}
