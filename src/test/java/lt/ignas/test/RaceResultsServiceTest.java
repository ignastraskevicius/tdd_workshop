package lt.ignas.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

/**
 * Practical Unit Testing with TestNG and Mockito - source code for examples.
 * Visit http://practicalunittesting.com for more information.
 *
 * @author Tomek Kaczanowski
 */
@Test
public class RaceResultsServiceTest {

    private RaceResultsService raceResults;
    private Client clientA, clientB;
    private Message message;
    private Category categoryA;
    private Category categoryB;
    private Logger log;
    private TimeProvider timeProvider;

    @BeforeMethod
    public void setUp() {
        raceResults = new RaceResultsService();
        clientA = mock(Client.class);
        clientB = mock(Client.class);
        message = mock(Message.class);
        categoryA = Category.F1;
        categoryB = Category.Horses;


        log = mock(Logger.class);
        raceResults.setLogger(log);


        timeProvider = mock(TimeProvider.class);
        raceResults.setTimeProvider(timeProvider);

    }

    public void notSubscribedClientShouldNotReceiveMessage() {
        raceResults.send(message, categoryA);
        verify(clientA, never()).receive(message);
        verify(clientB, never()).receive(message);
    }

    public void oneSubscribedClientShouldReceiveMessage() {
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.send(message, categoryA);
        verify(clientA).receive(message);
    }

    public void allSubscribedClientsShouldReceiveMessages() {
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.addSubscriber(clientB, categoryA);
        raceResults.send(message, categoryA);
        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }

    public void shouldSendOnlyOneMessageToMultiSubscriberOfOneCategory() {
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.send(message, categoryA);
        //verify(clientA, times(1)).receive(message); // same as below - times(1) is the default
        verify(clientA).receive(message);
    }

    // removing multisubscriber of one category once should be enough to unsubscribe
    @Test
    public void removingMultisubscriberOfOneCategoryOnceShouldBeEnoughToUnsubscribe() {
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.removeSubscriber(clientA, categoryA);
        raceResults.send(message, categoryA);

        verify(clientA, never()).receive(message);
    }

    public void unsubscribedClientShouldNotReceiveMessages() {
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.removeSubscriber(clientA, categoryA);
        raceResults.send(message, categoryA);
        verify(clientA, never()).receive(message);
    }

    // refactor that categoryA should include in subscription. Same categoryA when subscribing and sending a message should end with message beeing sent.
    // subscriber should not get message if categoryA is not the same subscriber subscribed for

    @Test
    public void subscriberShouldNotGetMessageIfCategoryNameIsNotTheSameHeSubscribedFor() {
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.send(message, categoryB);
        verify(clientA, never()).receive(message);
    }

    // subscriber should receive message if categoryA both
    // ohe he subscribed and one message is send for have same names
    @Test
    public void subscriberShouldReceiveMessgeIfCategoryBothOneHeSubscribedAndOneMessageIsSentForHaveSameNames() {

        raceResults.addSubscriber(clientA, categoryA);
        raceResults.send(message, Category.F1);
        verify(clientA).receive(message);
    }

    // subscriber should subscriber for more than one category
    @Test
    public void subscriberShouldSubscribeForMoreThanOneCategory() {
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.addSubscriber(clientA, categoryB);
        raceResults.send(message, categoryA);
        raceResults.send(message, categoryB);

        verify(clientA, times(2)).receive(message);
    }

    // remove Subscriber Should Not Remove Subscriber From All Categories
    @Test
    public void allCategoriesShouldNotBeUnsubscribedWhenUbsubscribingOnlyOne() {
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.addSubscriber(clientA, categoryB);
        raceResults.removeSubscriber(clientA, categoryA);

        raceResults.send(message, categoryB);

        verify(clientA).receive(message);
    }

    // removal of one subscriber of a category should not remove all its subscribers
    @Test
    public void allSubscribersShouldNotBeRemovedWhenRemovingOnlyOne() {
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.addSubscriber(clientB, categoryA);
        raceResults.removeSubscriber(clientA, categoryA);

        raceResults.send(message, categoryA);

        verify(clientB).receive(message);
    }

    // should not log sending when no message were sent
    @Test
    public void shouldNotLogSendingWhenNoMessageWereSent() {
        raceResults.send(message, categoryA);
        verify(log, never()).log(anyString(), anyString());
    }

    @DataProvider
    public static final Object[][] getTimes() {
        return new Object[][] {
            {"10:10"},
            {"11:20"},
            {"12:30"}
        };
    }


    // more cases
    // should log time
    @Test (dataProvider = "getTimes")
    public void shouldLogTime(String time) {
        when(timeProvider.getTime()).thenReturn(time);

        raceResults.addSubscriber(clientA, categoryA);
        raceResults.send(message, categoryA);
        verify(log).log(eq(time), anyString());
    }
    // should log message String
    // should log string
    @Test
    public void shouldLogMessageString() {

        when(message.getText()).thenReturn("aa");

        raceResults.addSubscriber(clientA, categoryA);
        raceResults.send(message, categoryA);
        verify(log).log(anyString(), eq("aa"));
    }
}

