package lt.ignas.test;

import java.util.Collection;
import java.util.HashSet;

/**
 * Practical Unit Testing with TestNG and Mockito - source code for examples.
 * Visit http://practicalunittesting.com for more information.
 *
 * @author Tomek Kaczanowski
 */
public class RaceResultsService {

    private Collection<Client> clients = new HashSet<Client>();
    private Category registeredCategory;


    public void addSubscriber(Client client, Category category) {
        this.registeredCategory = category;
        clients.add(client);
    }

    public void send(Message message, Category category) {
        if(this.registeredCategory == category) {
            for (Client client : clients) {
                client.receive(message);
            }
        }
    }

    public void removeSubscriber(Client client) {
        clients.remove(client);
    }
}
