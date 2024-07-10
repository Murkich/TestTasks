package main.java.ru.clevertec.model;

import java.math.BigDecimal;

public class Product {
    private final int id;
    private final String description;
    private final BigDecimal price;
    private final int quantityInStock;
    private final boolean isWholesale;

    public Product(ProductBuilder productBuilder) {
        this.id = productBuilder.id;
        this.description = productBuilder.description;
        this.price = productBuilder.price;
        this.quantityInStock = productBuilder.quantityInStock;
        this.isWholesale = productBuilder.isWholesale;
    }

    public static class ProductBuilder {
        private int id;
        private String description;
        private BigDecimal price;
        private int quantityInStock;
        private boolean isWholesale;

        public ProductBuilder setId(int id) {
            this.id = id;
            return this;
        }
        public ProductBuilder setDescription(String description) {
            this.description = description;
            return this;
        }
        public ProductBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }
        public ProductBuilder setQuantityInStock(int quantityInStock) {
            this.quantityInStock = quantityInStock;
            return this;
        }
        public ProductBuilder setWholesale(boolean isWholesale) {
            this.isWholesale = isWholesale;
            return this;
        }
        public Product build() {
            return new Product(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public boolean isWholesale() {
        return isWholesale;
    }
}
