package model;

public class Game {
    private int id;
    private String title;
    private String publisher;
    private String available;

    public Game(int id, String title, String publisher, String available) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", available='" + available + '\'' +
                '}';
    }

}
