package tech.shuidikeji.shuidijinfu.pojo;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.List;

public class ProvincePojo implements Serializable,IPickerViewData {

    private String name;
    private List<City> city;

    public String getName() {
        return name;
    }

    public List<City> getCity() {
        return city;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }

    public static class City implements Serializable,IPickerViewData{
        private String name;
        private List<String> area;

        @Override
        public String getPickerViewText() {
            return name;
        }

        public String getName() {
            return name;
        }

        public List<String> getArea() {
            return area;
        }
    }
}
