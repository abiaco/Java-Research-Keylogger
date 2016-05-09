
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.FileNotFoundException;

public class KeyboardListener implements NativeKeyListener{
    public EventBuffer buffer;

    public KeyboardListener() throws FileNotFoundException {
        this.buffer = new EventBuffer("KeyLog");
    }
    public KeyboardListener(EventBuffer b){
        this.buffer = b;
    }
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        System.out.println("time: " + e.getWhen());

        Event event = new Event(NativeKeyEvent.getKeyText(e.getKeyCode()), "key_pressed",  e.getWhen());
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
                buffer.clearBuffer();
            }
            catch (NativeHookException ex){
                System.err.println("There was a problem un-registering the native hook.");
                System.err.println(ex.getMessage());

                System.exit(1);
            }
        }
        this.buffer.read(event);
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        Event event = new Event(NativeKeyEvent.getKeyText(e.getKeyCode()), "key_released",  e.getWhen());
        buffer.updatePressed(event);
        this.buffer.read(event);
    }

    public void nativeKeyTyped(NativeKeyEvent e) {

        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

}
