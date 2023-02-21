package mychati.app.Client.ClientTovarsAdaoter;

public class TovarsAdapter {

    private String MagLogo,MagName,TovarStatus,ProductTime,TovarName,TovarOpisanie,TovarPrice,TovarImage1,ShopPhone,ShopUid,TovarImage2,TovarImage3,TovarImage4,TovarImage5;

    public TovarsAdapter(){

    }

    public TovarsAdapter(String MagLogo,String MagName,String TovarStatus,String ProductTime,String tovarName, String tovarOpisanie, String tovarPrice, String tovarImage1, String shopPhone, String shopUid,String tovarImage2,String tovarImage3,String tovarImage4,String tovarImage5) {
       this. TovarName = tovarName;
      this.  TovarOpisanie = tovarOpisanie;
      this.MagName=MagName;
      this.MagLogo=MagLogo;
      this.  TovarPrice = tovarPrice;
      this.  TovarImage1 = tovarImage1;
        this.  TovarImage2 = tovarImage2;
        this.  TovarImage3 = tovarImage3;
        this.  TovarImage4 = tovarImage4;
        this.  TovarImage5 = tovarImage5;
      this.TovarStatus=TovarStatus;
       this. ShopPhone = shopPhone;
     this.   ShopUid = shopUid;
     this.ProductTime=ProductTime;
    }

    public String getTovarImage1() {
        return TovarImage1;
    }

    public void setTovarImage1(String tovarImage1) {
        TovarImage1 = tovarImage1;
    }

    public String getTovarImage2() {
        return TovarImage2;
    }

    public void setTovarImage2(String tovarImage2) {
        TovarImage2 = tovarImage2;
    }

    public String getTovarImage3() {
        return TovarImage3;
    }

    public void setTovarImage3(String tovarImage3) {
        TovarImage3 = tovarImage3;
    }

    public String getTovarImage4() {
        return TovarImage4;
    }

    public void setTovarImage4(String tovarImage4) {
        TovarImage4 = tovarImage4;
    }

    public String getTovarImage5() {
        return TovarImage5;
    }

    public void setTovarImage5(String tovarImage5) {
        TovarImage5 = tovarImage5;
    }

    public String getMagLogo() {
        return MagLogo;
    }

    public void setMagLogo(String magLogo) {
        MagLogo = magLogo;
    }

    public String getMagName() {
        return MagName;
    }

    public void setMagName(String magName) {
        MagName = magName;
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
