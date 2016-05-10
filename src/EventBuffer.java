import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventBuffer {
    PrintWriter file;
    Thread t;
    final static String logName = "log_" + new Date();
    final static int BUFFER_SIZE = 11;
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
        t = new Thread(() -> {
            writeToFile();
        });
    }

    public EventBuffer(String filename){
        arrayLock = new Object();
        this.readBuffer = new Event[BUFFER_SIZE];
        this.writeBuffer = new Event[BUFFER_SIZE];
        Date date = new Date();
        try {
            String name = "logs\\" + filename + new SimpleDateFormat("MM_dd_yyyy_hh-mm-ss").format(date)+".txt";
            file = new PrintWriter(name, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        t = new Thread(() -> {
            writeToFile();
        });
        readBufIndex = -1;
    }

    public void read(Event e){
        readBufIndex++;
        readBuffer[readBufIndex] = e;
        if (readBufIndex > BUFFER_SIZE - 2) {
            synchronized (readBuffer) {
                copyToWriteBuffer();
            }
            t.start();
        }

    }


    public void copyToWriteBuffer() {
        for (int i = 0; i < readBufIndex; i++) {
            this.writeBuffer[i] = new Event(this.readBuffer[i]);
        }
        synchronized (readBuffer){
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
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("closing file");
        file.flush();
        file.close();
        System.out.println("closed");
        System.exit(0);
    }

    void printReadBuffer(){
        for (int i = 0 ; i < readBufIndex ; i++){
            System.out.println(readBuffer[i]);
        }
    }
}
