package cz.cvut.fel.omo.hw.functions.data.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;
import lombok.Data;

@Data
@XmlRootElement(name = "STAT", namespace = Constants.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class Country {

    @XmlAttribute(name = "ZKRATKA")
    private String abbr;
    @XmlAttribute(name = "NAZEV")
    private String name;
    @XmlElement(name = "HODN_KAND", namespace = Constants.NAMESPACE)
    private List<Vote> votes;
    @XmlElement(name = "UCAST", namespace = Constants.NAMESPACE)
    private VoterTurnout voterTurnout;
}
