package cz.cvut.fel.omo.hw.functions.data.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;


@Data
@XmlRootElement(name = "PE_REGKAND", namespace = Constants.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class Candidates {

    @XmlElement(name = "PE_REGKAND_ROW", namespace = Constants.NAMESPACE)
    private List<Candidate> candidatesList = new ArrayList<>();

}
