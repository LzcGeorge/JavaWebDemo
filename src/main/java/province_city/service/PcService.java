package province_city.service;

import province_city.dao.PcDao;
import province_city.domain.City;
import province_city.domain.Province;

import java.util.List;

public class PcService {
    private PcDao pcDao = new PcDao();

    public List<Province> findAllProvince() {
        return pcDao.findAllProvince();
    }

    public List<City> findByProvince(int pid) {
        return pcDao.findByProvince(pid);
    }
}
