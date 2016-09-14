package id.or.codelabs.realmjson2.model;

import io.realm.RealmObject;

/**
 * Created by FitriFebriana on 9/8/2016.
 */
public class Book extends RealmObject {
    private String id;
    private String title;
    private String author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
