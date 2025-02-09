package net.vkolev.htmxjsonbexample.notes;


import java.util.List;

public class NoteInput {
    private String title;
    private List<String> lang;
    private List<String> content;

    public NoteInput(String title, List<String> lang, List<String> content) {
        this.title = title;
        this.lang = lang;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLang() {
        return lang;
    }

    public void setLang(List<String> lang) {
        this.lang = lang;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
