package model;

import jakarta.persistence.*;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private File file;

    public Event() {
    }

    public Event(User user, File file) {
        this.user = user;
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=User{" + "id=" + user.getId() +
                ", name=" + user.getName() + "}" +
                ", file=" + file +
                '}';
    }
}
