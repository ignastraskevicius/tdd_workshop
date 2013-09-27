package lt.ignas.test;

import com.google.common.collect.HashMultimap;

public class RaceResultsService {

    private HashMultimap<Client, Category> clientsSubscribedCategories = HashMultimap.create();

    public void addSubscriber(Client client, Category category) {
        clientsSubscribedCategories.put(client, category);
    }

    public void send(Message message, final Category category) {
        for (Client client : clientsSubscribedCategories.keySet()) {
            if(clientsSubscribedCategories.containsEntry(client, category)) {
                client.receive(message);
            }
        }
    }

    public void removeSubscriber(Client client, Category category) {
        clientsSubscribedCategories.remove(client, category);
    }


}

