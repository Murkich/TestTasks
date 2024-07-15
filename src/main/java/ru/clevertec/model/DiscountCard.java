package main.java.ru.clevertec.model;

import java.math.BigDecimal;

public class DiscountCard {
    private final int id;
    private final String number;
    private final BigDecimal discount;

    public DiscountCard(DiscountCardBuilder discountCardBuilder) {
        this.id = discountCardBuilder.id;
        this.number = discountCardBuilder.number;
        this.discount = discountCardBuilder.discount;
    }

    public static class DiscountCardBuilder {
        private int id;
        private String number;
        private BigDecimal discount;

        public DiscountCardBuilder setId(int id) {
            this.id = id;
            return this;
        }
        public DiscountCardBuilder setNumber(String number) {
            this.number = number;
            return this;
        }
        public DiscountCardBuilder setDiscount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }
        public DiscountCard build() {
            return new DiscountCard(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public BigDecimal getDiscount() {return discount;}
}
