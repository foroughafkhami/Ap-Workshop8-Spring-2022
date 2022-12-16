import java.io.Serializable;
import java.time.LocalDateTime;

public class Note implements Serializable {
    private String title;
    private String text;
    /*The LocalDateTime class in Java is an immutable date-time object
    that represents a date in the yyyy-MM-dd-HH-mm-ss. zzz format. */
    private LocalDateTime dateTime;

    public Note(String title, String text, LocalDateTime dateTime) {
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getText() {
        return text;
    }
}
