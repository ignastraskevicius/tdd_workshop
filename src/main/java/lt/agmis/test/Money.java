package lt.agmis.test;

import org.apache.commons.lang3.ObjectUtils;

/**
 * Created with IntelliJ IDEA.
 * User: ignas
 * Date: 9/17/13
 * Time: 9:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class Money {
    private final int amount;
    private final String currency;

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money buildMoneyAndValidate(int amount, String currency) {
        if(amount < 0 || currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return new Money(amount, currency);
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Money) {
            Money money = (Money) o;
            return ObjectUtils.equals(getCurrency(), money.getCurrency())  && getAmount() == money.getAmount();
        }
        return false;
    }
}
