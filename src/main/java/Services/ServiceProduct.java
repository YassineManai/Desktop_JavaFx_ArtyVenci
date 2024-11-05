package services;

import entities.Product;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduct implements IServiceF<Product>{
    private Connection con;
    public ServiceProduct(){
        con = MyDB.getInstance().getConnection();
    }
    @Override
    public void ajouter(Product product) throws SQLException {
        String req = "insert into product (Id_User,Title,Description,ForSale,Price,CreationDate,ProductImage)"+
                "values ('"+product.getId_User()+"','"+product.getTitle()+"','"+product.getDescription()+"','"+product.getForSale()+"','"+product.getPrice()+"','"+product.getCreationDate()+"','"+product.getProductImage()+"')";
        Statement ste = con.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void modifier(Product product) throws SQLException {
        String req = "update product set Title=? , Description=? , ForSale=? , Price=? , CreationDate=? , ProductImage=? where Id_Product=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1,product.getTitle());
        pre.setString(2,product.getDescription());
        pre.setInt(3,product.getForSale());
        pre.setDouble(4,product.getPrice());
        pre.setString(5,product.getCreationDate());
        pre.setString(6, product.getProductImage());
        pre.setInt(7,product.getId_Product());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(Product product)throws SQLException {
        String req ="delete from  product where Id_Product=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,product.getId_Product());
        pre.execute();
        System.out.println("deleted");
    }
    public int CountProduct() throws SQLException {
        String produit = "SELECT COUNT(*) FROM Product";
        try (PreparedStatement pre = con.prepareStatement(produit)) {
            try (ResultSet resultSet = pre.executeQuery()) {
                if (resultSet.next()) {
                    int total = resultSet.getInt(1);
                    return total;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error counting users: " + e.getMessage());
            // Optionally, you may throw an exception or return a default value here
        }
        return 0; // Return 0 if an error occurs
    }

    public List<Product> maList(int id) throws SQLException {
        List<Product> pro = new ArrayList<>();
        String req ="select * from product where Id_User=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,id);
        ResultSet res = pre.executeQuery();
        while (res.next()){
            Product p = new Product();
            p.setId_Product(res.getInt(1));
            p.setId_User(res.getInt(2));
            p.setTitle(res.getString("Title"));
            p.setDescription(res.getString("Description"));
            p.setForSale(res.getInt("ForSale"));
            p.setPrice(res.getDouble("Price"));
            p.setCreationDate(res.getString("CreationDate"));
            p.setProductImage(res.getString("ProductImage"));
            pro.add(p);
        }
        return pro;
    }

    public List<Product> saleList() throws SQLException {
        List<Product> pro = new ArrayList<>();
        String req ="select * from product where ForSale=1";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res= pre.executeQuery();
        while (res.next()){
            Product p = new Product();
            p.setId_Product(res.getInt(1));
            p.setId_User(res.getInt(2));
            p.setTitle(res.getString("Title"));
            p.setDescription(res.getString("Description"));
            p.setForSale(res.getInt("ForSale"));
            p.setPrice(res.getDouble("Price"));
            p.setCreationDate(res.getString("CreationDate"));
            p.setProductImage(res.getString("ProductImage"));
            pro.add(p);
        }
        return pro;
    }

    public List<Product> NosaleList() throws SQLException {
        List<Product> pro = new ArrayList<>();
        String req ="select * from product where ForSale=0";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res= pre.executeQuery();
        while (res.next()){
            Product p = new Product();
            p.setId_Product(res.getInt(1));
            p.setId_User(res.getInt(2));
            p.setTitle(res.getString("Title"));
            p.setDescription(res.getString("Description"));
            p.setForSale(res.getInt("ForSale"));
            p.setPrice(res.getDouble("Price"));
            p.setCreationDate(res.getString("CreationDate"));
            p.setProductImage(res.getString("ProductImage"));
            pro.add(p);
        }
        return pro;
    }

    public List<Product> afficher() throws SQLException {
        List<Product> pro = new ArrayList<>();

        String req = "select * from product";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet res= pre.executeQuery();
        while (res.next()){
            Product p = new Product();
            p.setId_Product(res.getInt(1));
            p.setId_User(res.getInt(2));
            p.setTitle(res.getString("Title"));
            p.setDescription(res.getString("Description"));
            p.setForSale(res.getInt("ForSale"));
            p.setPrice(res.getDouble("Price"));
            p.setCreationDate(res.getString("CreationDate"));
            p.setProductImage(res.getString("ProductImage"));
            pro.add(p);
        }


        return pro;
    }

    public List<Product> FilterShow(String title) throws SQLException {
        List<Product> pro = new ArrayList<>();
        String req ="select * from product where Title=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1,title);
        ResultSet res = pre.executeQuery();
        while (res.next()){
            Product p = new Product();
            p.setId_Product(res.getInt(1));
            p.setId_User(res.getInt(2));
            p.setTitle(res.getString("Title"));
            p.setDescription(res.getString("Description"));
            p.setForSale(res.getInt("ForSale"));
            p.setPrice(res.getDouble("Price"));
            p.setCreationDate(res.getString("CreationDate"));
            p.setProductImage(res.getString("ProductImage"));
            pro.add(p);
        }
        return pro;
    }

}
