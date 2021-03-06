package judge.domain.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "contests")
public class Contest extends BaseEntity {
    private String name;
    private Category category;
    private Set<Problem> problems;
    private Set<User> contestants;
    private Set<MaxResultForContest> maxResultsForContest;

    public Contest() {
        this.problems = new HashSet<>();
        this.contestants = new HashSet<>();
        this.maxResultsForContest = new HashSet<>();
    }

    @Column
    public String getName() {
        return name;
    }

    @ManyToOne(targetEntity = Category.class)
    public Category getCategory() {
        return category;
    }

    @OneToMany(mappedBy = "contest")
    public Set<Problem> getProblems() {
        return problems;
    }

    @ManyToMany(mappedBy = "contests")
    public Set<User> getContestants() {
        return contestants;
    }

    @OneToMany(mappedBy = "contest")
    public Set<MaxResultForContest> getMaxResultsForContest() {
        return maxResultsForContest;
    }


    public void setContestants(Set<User> contestants) {
        this.contestants = contestants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }

    public void setMaxResultsForContest(Set<MaxResultForContest> maxResultsForContest) {
        this.maxResultsForContest = maxResultsForContest;
    }
}
