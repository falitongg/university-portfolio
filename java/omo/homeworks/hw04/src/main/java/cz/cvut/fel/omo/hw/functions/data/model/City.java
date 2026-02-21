package cz.cvut.fel.omo.hw.functions.data.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.Data;

@Data
@XmlRootElement(name = "OBEC", namespace = Constants.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class City {

    @XmlAttribute(name = "NAZ_OBEC")
    private String name;
    @XmlAttribute(name = "TYP_OBEC")
    private String cityType;
    @XmlElement(name = "HODN_KAND", namespace = Constants.NAMESPACE)
    private List<Vote> votes;
    @XmlElement(name = "UCAST", namespace = Constants.NAMESPACE)
    private VoterTurnout voterTurnout;

}
