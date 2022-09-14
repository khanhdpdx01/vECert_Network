package io.github.khanhdpdx01;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public class DiplomaQueryResult {
    @Property()
    private final String tx;
    @Property()
    private final Diploma diploma;

    public DiplomaQueryResult(@JsonProperty("diploma") Diploma diploma,
                              @JsonProperty("tx") String tx) {
        this.diploma = diploma;
        this.tx = tx;
    }

    public String getTx() {
        return tx;
    }

    public Diploma getDiploma() {
        return diploma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiplomaQueryResult response = (DiplomaQueryResult) o;
        return Objects.equals(tx, response.tx) && Objects.equals(diploma, response.diploma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tx, diploma);
    }

    @Override
    public String toString() {
        return "DiplomaResponse{" +
                "tx='" + tx + '\'' +
                ", diploma=" + diploma +
                '}';
    }
}
