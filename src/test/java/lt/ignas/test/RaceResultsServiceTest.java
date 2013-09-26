package lt.ignas.test;

import org.testng.annotations.BeforeMethod;
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


    @BeforeMethod
    public void setUp() {
        raceResults = new RaceResultsService();
        clientA = mock(Client.class);
        clientB = mock(Client.class);
        message = mock(Message.class);
        categoryA = mock(Category.class);
        categoryB = mock(Category.class);
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
        when(categoryA.getName()).thenReturn(Category.Name.F1);
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
        when(categoryB.getName()).thenReturn(Category.Name.F1);
        when(categoryA.getName()).thenReturn(Category.Name.Horses);
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.send(message, categoryB);
        verify(clientA, never()).receive(message);
    }

    // subscriber should receive message if categoryA both
    // ohe he subscribed and one message is send for have same names
    @Test
    public void subscriberShouldReceiveMessgeIfCategoryBothOneHeSubscribedAndOneMessageIsSentForHaveSameNames() {

        when(categoryB.getName()).thenReturn(Category.Name.F1);
        when(categoryA.getName()).thenReturn(Category.Name.F1);
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.send(message, categoryB);
        verify(clientA).receive(message);
    }

    // subscriber should subscriber for more than one category
    @Test
    public void subscriberShouldSubscribeForMoreThanOneCategory() {
        when(categoryB.getName()).thenReturn(Category.Name.F1);
        when(categoryA.getName()).thenReturn(Category.Name.Horses);
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.addSubscriber(clientA, categoryB);
        raceResults.send(message, categoryA);
        raceResults.send(message, categoryB);

        verify(clientA, times(2)).receive(message);
    }

    // remove Subscriber Should Not Remove Subscriber From All Categories
    @Test
    public void removeShubscriberShouldNotRemoveSubscriberFromAllLCategories() {
        when(categoryB.getName()).thenReturn(Category.Name.F1);
        when(categoryA.getName()).thenReturn(Category.Name.Horses);
        raceResults.addSubscriber(clientA, categoryA);
        raceResults.addSubscriber(clientA, categoryB);
        raceResults.removeSubscriber(clientA, categoryA);

        raceResults.send(message, categoryB);

        verify(clientA).receive(message);
    }


}

