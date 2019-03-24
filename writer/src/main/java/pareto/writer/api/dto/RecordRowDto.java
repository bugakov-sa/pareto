package pareto.writer.api.dto;

public class RecordRowDto {
    private final String rowType;
    private final String rowText;

    public RecordRowDto(String rowType, String rowText) {
        this.rowType = rowType;
        this.rowText = rowText;
    }

    public String getRowType() {
        return rowType;
    }

    public String getRowText() {
        return rowText;
    }
}
