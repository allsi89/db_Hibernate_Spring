package judge.domain.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "problems")
public class Problem extends BaseEntity {
    private String name;
    private Set<Submission> submissions;
    private Set<User> contestants;
    private Contest contest;
    private Set<MaxResultForProblem> maxResultForProblems;
    private Set<Test> tests;

    public Problem() {
        this.submissions = new HashSet<>();
        this.contestants = new HashSet<>();
        this.maxResultForProblems = new HashSet<>();
        this.tests = new HashSet<>();
    }

    @Column
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "problem")
    public Set<Submission> getSubmissions() {
        return submissions;
    }

    @ManyToMany
    @JoinTable(
            name = "users_problems",
            joinColumns =
            @JoinColumn(
                    name = "problem_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns =
            @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            )
    )
    public Set<User> getContestants() {
        return contestants;
    }

    @ManyToOne
    public Contest getContest() {
        return contest;
    }

    @OneToMany(mappedBy = "problem")
    public Set<MaxResultForProblem> getMaxResultForProblems() {
        return maxResultForProblems;
    }

    @OneToMany(mappedBy = "problem")
    public Set<Test> getTests() {
        return tests;
    }

    public void setContestants(Set<User> contestants) {
        this.contestants = contestants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public void setMaxResultForProblems(Set<MaxResultForProblem> maxResultForProblems) {
        this.maxResultForProblems = maxResultForProblems;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }
}
