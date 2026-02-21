package cz.cvut.fel.omo.hw.functions.data.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.Data;

@Data
@XmlRootElement(name = "OKRES", namespace = Constants.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class District {

    @XmlAttribute(name = "NAZ_OKRES")
    private String name;
    @XmlElement(name = "OBEC", namespace = Constants.NAMESPACE)
    private List<City> cities;

}
