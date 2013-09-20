package lt.agmis.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/17/13
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */



@Test
public class MoneyTest {

    private final static int VALID_AMOUNT = 5;
    private final static String VALID_CURRENCY = "USD";

    public void constructorShouldSetAmountAndCurrency() {
        Money sut = new Money(10, "USD");

        assertEquals(sut.getAmount(), 10);
        assertEquals(sut.getCurrency(), "USD");
    }

    @DataProvider
    public static final Object[][] getMoney() {
        return new Object[][] {
                {10, "USD"},
                {20, "EUR"}
        };
    }

    @Test(dataProvider = "getMoney")
    public void constructorShouldSetAmountAndCurrency(int amount, String currency) {
        Money sut = new Money(amount, currency);

        assertEquals(sut.getAmount(), amount);
        assertEquals(sut.getCurrency(), currency);
    }

    @DataProvider
    private static final Object[][] getInvalidAmount() {
        return new Integer[][] {{-10555}, {-5}, {-1}};
    }

    @Test(dataProvider = "getInvalidAmount", expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIAEforInvalidAmount(int invalidAmount) {
        Money sut = Money.buildMoneyAndValidate(invalidAmount, VALID_CURRENCY);
    }

    @DataProvider
    private static final Object[][] getInvalidCurrency() {
        return new String[][] {{"null"}, {""}};
    }

    @Test(dataProvider = "getInvalidCurrency", expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowIAEforInvalidAmount(String invalidCurrency) {
        Money sut = Money.buildMoneyAndValidate(VALID_AMOUNT, invalidCurrency);
    }
}

