package gamestore.domain.dtos;

import gamestore.constants.Constants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GameDetailsDto {
    private String title;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;


    public GameDetailsDto() {
    }

    public GameDetailsDto(String title, BigDecimal price, String description, LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(String.format(Constants.TITLE_S, this.title))
                .append(System.lineSeparator())
                .append(String.format(Constants.PRICE_2_F, this.price))
                .append(System.lineSeparator())
                .append(String.format(Constants.PRICE_S, this.description))
                .append(System.lineSeparator())
                .append(String.format(Constants.PRICE_IS, this.releaseDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .toString();

    }
}
