
public class Event {
    String item;
    long timeStamp;
    String type;

    public Event(String item, String type, long timeStamp){
        this.item = item;
        this.type = type;
        this.timeStamp = timeStamp;

    }

    public Event(int item, String type, long timeStamp){
        this.item = Integer.toString(item);
        this.type = type;
        this.timeStamp = timeStamp;

    }

    public Event(){
        this.item="";
        this.type="None";
        this.timeStamp = 0;

    }

    public Event(Event other){
        this.item = other.item;
        this.type = other.type;
        this.timeStamp = other.timeStamp;

    }

    public String getItem(){
        return this.item;
    }

    public String getType(){
        return this.type;
    }

    public long getTimeStamp(){
        return this.timeStamp;
    }


    public String toString(){
        return "Type: " + this.type + "; Item: " + this.item + "; at time: " + this.timeStamp;
    }

    public static  void copy(Event a, Event other){
        a.item = other.item;
        a.type = other.type;
        a.timeStamp = other.timeStamp;
    }
}
