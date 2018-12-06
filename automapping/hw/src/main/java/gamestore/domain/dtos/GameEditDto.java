package gamestore.domain.dtos;


import gamestore.constants.Constants;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

import static gamestore.constants.Constants.INVALID_INPUT;
import static gamestore.constants.Constants.TITLE_REGEXP;

public class GameEditDto {
    private String id;
    private String title;
    private BigDecimal price;
    private Double size;
    private String trailer;
    private String imageThumbnail;
    private String description;
    private LocalDate releaseDate;

    public GameEditDto() {
    }

    public GameEditDto(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Pattern(regexp = TITLE_REGEXP)
    @Size(min = 3, max = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Range(min = 1L)
    @Digits(integer = 20, fraction = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Range(min = 1L)
    @Digits(integer = 10, fraction = 1)
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Size(min = 11, max = 11, message = Constants.INVALID_TRAILER_LINK)
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        if (imageThumbnail.startsWith("http://")|| imageThumbnail.startsWith("https://")) {
            this.imageThumbnail = imageThumbnail;
        } else {
            throw new IllegalArgumentException(INVALID_INPUT);
        }

    }

    @Size(min = 20, message = Constants.DESCRIPTION_SHOULD_BE_AT_LEAST_20_WSYMBOLS_LONG)
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


}
