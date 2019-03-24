package pareto.writer.api.dto;

public class RawRecordDto {

    private String text;

    public RawRecordDto() {
    }

    public RawRecordDto(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
