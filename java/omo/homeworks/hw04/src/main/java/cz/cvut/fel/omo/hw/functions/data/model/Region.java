package cz.cvut.fel.omo.hw.functions.data.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.Data;

@Data
@XmlRootElement(name = "KRAJ", namespace = Constants.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class Region {

    @XmlAttribute(name = "NAZ_KRAJ")
    private String name;
    @XmlElement(name = "OKRES", namespace = Constants.NAMESPACE)
    private List<District> districts;

}
