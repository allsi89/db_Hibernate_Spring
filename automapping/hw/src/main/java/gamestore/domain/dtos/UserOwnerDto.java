package gamestore.domain.dtos;

import java.util.ArrayList;
import java.util.List;

public class UserOwnerDto {
    private List<GameOwnedDto> games;

    public UserOwnerDto() {
        this.games = new ArrayList<>();
    }

    public List<GameOwnedDto> getGames() {
        return games;
    }

    public void setGames(List<GameOwnedDto> games) {
        if (games.size() != 0){
            this.games = games;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        games.forEach(g->sb.append(g.toString()).append(System.lineSeparator()));
        return sb.toString().trim();
    }
}
