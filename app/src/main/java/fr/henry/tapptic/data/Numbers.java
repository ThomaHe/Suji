package fr.henry.tapptic.data;

public class Numbers {
    private String name;
    private String image;
    private String text;

    public Numbers(String name, String image, String text) {
        this.name = name;
        this.image = image;
        this.text = text;
    }

    public Numbers() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
