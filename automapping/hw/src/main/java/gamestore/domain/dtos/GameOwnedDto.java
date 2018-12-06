package gamestore.domain.dtos;

public class GameOwnedDto {
    private String title;

    public GameOwnedDto() {
    }

    public GameOwnedDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
