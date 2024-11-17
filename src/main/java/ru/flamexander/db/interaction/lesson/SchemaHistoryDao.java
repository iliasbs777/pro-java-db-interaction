package ru.flamexander.db.interaction.lesson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SchemaHistoryDao {
    private DataSource dataSource;

    public SchemaHistoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void init() throws SQLException {
        dataSource.getStatement().executeUpdate(
                "" +
                        "create table if not exists schema_history (" +
                        "    id          bigserial primary key," +
                        "    filename    varchar(255)" +
                        ")"
        );
    }

    public Optional<SchemaHistory> getSchemaHistoryByFileName(String fileName) {
        try (ResultSet rs = dataSource.getStatement().executeQuery("select * from schema_history where fileName = " + fileName)) {
            return Optional.of(new SchemaHistory(rs.getLong("id"), rs.getString("fileName")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void save(SchemaHistory schemaHistory) throws SQLException {
        dataSource.getStatement().executeUpdate(String.format("insert into schema_history (filename) values ('%s');", schemaHistory.getFilename()));
    }

    public List<SchemaHistory> getAllSchemaHistory() {
        List<SchemaHistory> result = new ArrayList<>();
        try (ResultSet rs = dataSource.getStatement().executeQuery("select * from schema_history")) {
            while (rs.next() != false) {
                result.add(new SchemaHistory(rs.getLong("id"), rs.getString("filename")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableList(result);
    }

    public List<String> getAllSchemaHistoryFileNames() {
        List<String> result = new ArrayList<>();
        try (ResultSet rs = dataSource.getStatement().executeQuery("select * from schema_history")) {
            while (rs.next() != false) {
                result.add(rs.getString("filename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableList(result);
    }

    public void executeCommand(String command) throws SQLException {
        dataSource.getStatement().executeUpdate(command);
    }

}
