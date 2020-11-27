package saooperation.dao.domain;

public class Product {
    //`id` bigint(11) NOT NULL AUTO_INCREMENT,
    //  `productName` varchar(50) DEFAULT NULL,
    //  `dir_id` bigint(11) DEFAULT NULL,
    //  `salePrice` double(10,2) DEFAULT NULL,
    //  `supplier` varchar(50) DEFAULT NULL,
    //  `brand` varchar(50) DEFAULT NULL,
    //  `cutoff` double(2,2) DEFAULT NULL,
    //  `costPrice` double(10,2) DEFAULT NULL,
    private Long id;
    private String productName;
    private Long dir_id;
    private Double salePrice;
    private String supplier;
    private String brand;
    private Double cutoff;
    private Double costPrice;

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", dir_id=" + dir_id +
                ", salePrice=" + salePrice +
                ", supplier='" + supplier + '\'' +
                ", brand='" + brand + '\'' +
                ", cutoff=" + cutoff +
                ", costPrice=" + costPrice +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getDir_id() {
        return dir_id;
    }

    public void setDir_id(Long dir_id) {
        this.dir_id = dir_id;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getCutoff() {
        return cutoff;
    }

    public void setCutoff(Double cutoff) {
        this.cutoff = cutoff;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }
}
