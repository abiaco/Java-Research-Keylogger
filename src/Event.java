
public class Event {
    String item;
    long duration;
    long timeStamp;
    Event linkedEvent;
    String type;

    public Event(String item, String type, long timeStamp){
        this.item = item;
        this.type = type;
        this.timeStamp = timeStamp;
        this.duration = 0;
        this.linkedEvent = null;
    }

    public Event(){
        this.item="";
        this.type="None";
        this.timeStamp = 0;
        this.duration = 0;
        this.linkedEvent = null;
    }

    public Event(Event other){
        this.item = other.item;
        this.type = other.type;
        this.timeStamp = other.timeStamp;
        this.duration = other.duration;
        this.linkedEvent = other.linkedEvent;
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

    public void setDuration(long duration){
        this.duration = duration;
    }

    public long getDuration(){
        return this.duration;
    }

    public void setLinkedEvent(Event e){
        this.linkedEvent = e;
    }

    public Event getLinkedEvent(){
        return this.linkedEvent;
    }

    public String toString(){
        return "Type: " + this.type + "; Item: " + this.item + "; at time: " + this.timeStamp;
    }

    public static  void copy(Event a, Event other){
        a.item = other.item;
        a.type = other.type;
        a.timeStamp = other.timeStamp;
        a.duration = other.duration;
        a.linkedEvent = other.linkedEvent;
    }
}
