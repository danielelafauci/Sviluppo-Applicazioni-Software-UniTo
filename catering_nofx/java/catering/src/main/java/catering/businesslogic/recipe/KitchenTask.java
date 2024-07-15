package catering.businesslogic.recipe;

import catering.businesslogic.user.User;

public class KitchenTask {
    private int id;
    private String name;
    private boolean published;
    private String description;
    private User author;
    private User owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return name;
    }
}
