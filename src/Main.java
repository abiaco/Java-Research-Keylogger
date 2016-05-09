import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        try {
            GlobalScreen.addNativeKeyListener(new KeyboardListener());
        } catch (FileNotFoundException e) {
            System.out.println("not found key output file");
        }

        // Construct the example object.
        //MouseListener example = new MouseListener();

        // Add the appropriate listeners.
       // GlobalScreen.addNativeMouseListener(example);
       // GlobalScreen.addNativeMouseMotionListener(example);
    }

}