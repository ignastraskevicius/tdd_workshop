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
    private Category category;


    @BeforeMethod
    public void setUp() {
        raceResults = new RaceResultsService();
        clientA = mock(Client.class);
        clientB = mock(Client.class);
        message = mock(Message.class);
        category = mock(Category.class);
    }

    public void notSubscribedClientShouldNotReceiveMessage() {
        raceResults.send(message, category);
        verify(clientA, never()).receive(message);
        verify(clientB, never()).receive(message);
    }

    public void oneSubscribedClientShouldReceiveMessage() {
        raceResults.addSubscriber(clientA, category);
        raceResults.send(message, category);
        verify(clientA).receive(message);
    }

    public void allSubscribedClientsShouldReceiveMessages() {
        raceResults.addSubscriber(clientA, category);
        raceResults.addSubscriber(clientB, category);
        raceResults.send(message, category);
        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }

    public void shouldSendOnlyOneMessageToMultiSubscriber() {
        raceResults.addSubscriber(clientA, category);
        raceResults.addSubscriber(clientA, category);
        raceResults.send(message, category);
        //verify(clientA, times(1)).receive(message); // same as below - times(1) is the default
        verify(clientA).receive(message);
    }

    public void unsubscribedClientShouldNotReceiveMessages() {
        raceResults.addSubscriber(clientA, category);
        raceResults.removeSubscriber(clientA);
        raceResults.send(message, category);
        verify(clientA, never()).receive(message);
    }

    // refactor that category should include in subscription. Same category when subscribing and sending a message should end with message beeing sent.


}

