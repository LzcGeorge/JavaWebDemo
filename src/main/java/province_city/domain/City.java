package province_city.domain;

public class City {
    private int cid;
    private String name;
    private Province province; // 数据库中是 pid（外键）

    public City() {
    }

    public City(int cid, String name, Province province) {
        this.cid = cid;
        this.name = name;
        this.province = province;
    }

    @Override
    public String toString() {
        return "City{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", province=" + province +
                '}';
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
