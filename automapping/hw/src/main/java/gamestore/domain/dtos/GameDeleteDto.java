package gamestore.domain.dtos;

public class GameDeleteDto {
    private String title;

    public GameDeleteDto(String title) {
        this.title = title;
    }

    public GameDeleteDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
