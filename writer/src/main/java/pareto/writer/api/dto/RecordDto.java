package pareto.writer.api.dto;

import java.util.List;

public class RecordDto {
    private final long id;
    private final String date;
    private final List<RecordRowDto> rows;

    public RecordDto(long id, String date, List<RecordRowDto> rows) {
        this.id = id;
        this.date = date;
        this.rows = rows;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public List<RecordRowDto> getRows() {
        return rows;
    }
}
