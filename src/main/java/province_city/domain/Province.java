package province_city.domain;

import java.util.List;

public class Province {
    private int pid;
    private String name;
    private List<City> cityList;

    public Province() {
    }

    public Province(int pid, String name, List<City> cityList) {
        this.pid = pid;
        this.name = name;
        this.cityList = cityList;
    }

    @Override
    public String toString() {
        return "Province{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", cityList=" + cityList +
                '}';
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
