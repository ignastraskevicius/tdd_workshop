package lt.ignas.test;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
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
    private Collection<Category> categoryList = new HashSet<Category>();


    public void addSubscriber(Client client, Category category) {
        this.categoryList.add(category);
        clients.add(client);
    }

    public void send(Message message, final Category category) {
        if(categoryList != null && isCategoryListContainsItemWithName(categoryList, category.getName())) {
            for (Client client : clients) {
                client.receive(message);
            }
        }
    }

    public void removeSubscriber(Client client, Category category) {
        categoryList.remove(category);
    }

    private boolean isCategoryListContainsItemWithName(Collection<Category> categories, final Category.Name name) {
        boolean contains = Iterables.any(categories, new Predicate<Category>() {
            @Override
            public boolean apply(Category ithCategory) {
                return (name == ithCategory.getName());
            }
        });
        return contains;
    }

}
