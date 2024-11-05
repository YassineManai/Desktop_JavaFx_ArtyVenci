package entities;



public class Product {
    int Id_Product , Id_User , ForSale ;
    double Price;
    String Title , Description , CreationDate, ProductImage;

    /*default constructor*/

    public Product(){}

    /*constructor*/

    public Product(int id_Product, int id_User, int forSale, double price, String title, String description, String creationDate, String productImage) {
        Id_Product = id_Product;
        Id_User = id_User;
        ForSale = forSale;
        Price = price;
        Title = title;
        Description = description;
        CreationDate = creationDate;
        ProductImage = productImage;
    }

    /*constructor without primary & secondary key*/

    public Product(int id_User,int forSale, double price, String title, String description, String creationDate, String productImage) {
        Id_User = id_User;
        ForSale = forSale;
        Price = price;
        Title = title;
        Description = description;
        CreationDate = creationDate;
        ProductImage = productImage;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id_Product=" + Id_Product +
                ", Id_User=" + Id_User +
                ", ForSale=" + ForSale +
                ", Price=" + Price +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", CreationDate='" + CreationDate + '\'' +
                ", ProductImage='" + ProductImage + '\'' +
                '}';
    }

    /*getter & setters*/
    public int getId_Product() {
        return Id_Product;
    }

    public void setId_Product(int id_Product) {
        Id_Product = id_Product;
    }

    public int getId_User() {
        return Id_User;
    }

    public void setId_User(int id_User) {
        Id_User = id_User;
    }

    public int getForSale() {
        return ForSale;
    }

    public void setForSale(int forSale) {
        ForSale = forSale;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getProductImage() { return ProductImage;}

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

}
