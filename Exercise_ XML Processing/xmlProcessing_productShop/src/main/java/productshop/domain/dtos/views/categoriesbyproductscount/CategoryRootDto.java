package productshop.domain.dtos.views.categoriesbyproductscount;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryRootDto  {
    @XmlElement(name = "category")
    private List<CategoryByProductsCountDto> categories;

    public CategoryRootDto() {
        this.categories = new ArrayList<>();
    }

    public List<CategoryByProductsCountDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryByProductsCountDto> categories) {
        this.categories = categories;
    }
}
