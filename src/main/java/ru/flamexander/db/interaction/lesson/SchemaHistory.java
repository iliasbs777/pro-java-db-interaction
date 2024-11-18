package ru.flamexander.db.interaction.lesson;

@RepositoryTable(title = "schema_history")
public class SchemaHistory {
    @RepositoryIdField
    private Long id;
    @RepositoryField
    private String filename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public SchemaHistory() {
    }

    public SchemaHistory(Long id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "SchemaHistory{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                '}';
    }

}
