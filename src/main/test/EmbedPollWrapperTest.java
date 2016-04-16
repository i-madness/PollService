import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;
import net.imadness.util.EmbedPollWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EmbedPollWrapperTest {

    private EmbedPollWrapper pollWrapper;
    private Poll poll;
    private String hostName;

    @Before
    public void setUp() {
        List<Question> questions = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        Question question1 = new Question("Why am I just a test question?");
        Question question2 = new Question("Why do I even exist?");
        Option option1 = new Option("Just because",true);
        Option option2 = new Option("All because of you",false);
        options.add(option1);
        options.add(option2);
        question1.setOptions(options);
        question2.setOptions(options);
        questions.add(question1);
        questions.add(question2);
        this.poll = new Poll("The best poll in the world","Sure!");
        this.poll.setQuestions(questions);
        this.hostName = "localhost:8080";
        this.pollWrapper = new EmbedPollWrapper(poll,hostName);
    }

    @Test
    public void getOutput() {
        System.out.println(this.pollWrapper.toString());
    }
}
