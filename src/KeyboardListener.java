
import com.sun.org.apache.xpath.internal.SourceTree;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyboardListener implements NativeKeyListener{
    public EventBuffer buffer;
    PrintWriter file;

    public KeyboardListener() throws FileNotFoundException {
        this.buffer = new EventBuffer("KeyLog");
        Date date = new Date();
        try {
            String name = "logs\\" + "KeyLog" + new SimpleDateFormat("MM_dd_yyyy_hh-mm-ss").format(date)+".txt";
            file = new PrintWriter(name, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public KeyboardListener(EventBuffer b){
        this.buffer = b;
    }
    public void nativeKeyPressed(NativeKeyEvent e) {
        Event event = new Event(NativeKeyEvent.getKeyText(e.getKeyCode()), "key_pressed",  e.getWhen());
//        synchronized (buffer.readBuffer) {
//            this.buffer.read(event);
//        }
        file.println(event);
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
                //buffer.clearBuffer();
                file.flush();
                file.close();

            }
            catch (NativeHookException ex){
                System.err.println("There was a problem un-registering the native hook.");
                System.err.println(ex.getMessage());
                file.flush();
                file.close();
                System.exit(1);
            }
        }

    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        Event event = new Event(NativeKeyEvent.getKeyText(e.getKeyCode()), "key_released",  e.getWhen());
//        synchronized (buffer.readBuffer) {
//            this.buffer.read(event);}
        if (event.getItem()!= "Escape")
            file.println(event);

    }

    public void nativeKeyTyped(NativeKeyEvent e) {

       //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

}
