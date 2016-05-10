import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MouseListener implements NativeMouseInputListener{
    PrintWriter file;


    public MouseListener() throws FileNotFoundException {
        Date date = new Date();
        try {
            String name = "logs\\" + "MouseLog" + new SimpleDateFormat("MM_dd_yyyy_hh-mm-ss").format(date)+".txt";
            file = new PrintWriter(name, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public MouseListener(PrintWriter file) throws FileNotFoundException {
        this.file = file;
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
        System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        System.out.println("Mouse Pressed: " + e.getButton());
        Event event = new Event(e.getButton(), "mouse_pressed", e.getWhen());
        file.println(event);
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        System.out.println("Mouse Released: " + e.getButton());
        Event event = new Event(e.getButton(), "mouse_released", e.getWhen());
        file.println(event);
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
       // System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
       // System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }
}
