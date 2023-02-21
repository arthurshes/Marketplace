package mychati.app.Shops.MyTovarAdapter;

public class MyTovarAdapter {
    private String ProductTime,TovarName,TovarOpisanie,TovarPrice,TovarImage1,ShopPhone,ShopUid,TovarStatus;
public MyTovarAdapter(){

}

    public MyTovarAdapter(String tovarStatus,String ProductTime,String tovarName, String tovarOpisanie, String tovarPrice, String tovarImage1, String shopPhone, String shopUid) {
       this. TovarName = tovarName;
       this. TovarOpisanie = tovarOpisanie;
       this.TovarStatus=tovarStatus;
     this.   TovarPrice = tovarPrice;
      this.  TovarImage1 = tovarImage1;
      this.ProductTime=ProductTime;
      this.  ShopPhone = shopPhone;
      this.  ShopUid = shopUid;
    }

    public String getTovarImage1() {
        return TovarImage1;
    }

    public void setTovarImage1(String tovarImage1) {
        TovarImage1 = tovarImage1;
    }

    public String getTovarStatus() {
        return TovarStatus;
    }

    public void setTovarStatus(String tovarStatus) {
        TovarStatus = tovarStatus;
    }

    public String getProductTime() {
        return ProductTime;
    }

    public void setProductTime(String productTime) {
        ProductTime = productTime;
    }

    public String getTovarName() {
        return TovarName;
    }

    public void setTovarName(String tovarName) {
        TovarName = tovarName;
    }

    public String getTovarOpisanie() {
        return TovarOpisanie;
    }

    public void setTovarOpisanie(String tovarOpisanie) {
        TovarOpisanie = tovarOpisanie;
    }

    public String getTovarPrice() {
        return TovarPrice;
    }

    public void setTovarPrice(String tovarPrice) {
        TovarPrice = tovarPrice;
    }

    public String getShopPhone() {
        return ShopPhone;
    }

    public void setShopPhone(String shopPhone) {
        ShopPhone = shopPhone;
    }

    public String getShopUid() {
        return ShopUid;
    }

    public void setShopUid(String shopUid) {
        ShopUid = shopUid;
    }
}
