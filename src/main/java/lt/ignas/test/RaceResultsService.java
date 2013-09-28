package lt.ignas.test;

import com.google.common.collect.HashMultimap;

public class RaceResultsService {

    private HashMultimap<Client, Category> clientsSubscribedCategories = HashMultimap.create();
    private Logger logger;
    private TimeProvider timeProvider;

    public void addSubscriber(Client client, Category category) {
        clientsSubscribedCategories.put(client, category);
    }

    public void send(Message message, final Category category) {
        for (Client client : clientsSubscribedCategories.keySet()) {
            if(clientsSubscribedCategories.containsEntry(client, category)) {
                client.receive(message);
                logger.log(timeProvider.getTime());
            }
        }

    }

    public void removeSubscriber(Client client, Category category) {
        clientsSubscribedCategories.remove(client, category);
    }


    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }
}

