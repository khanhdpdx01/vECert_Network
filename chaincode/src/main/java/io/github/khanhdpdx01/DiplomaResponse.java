package io.github.khanhdpdx01;

import com.owlike.genson.annotation.JsonProperty;

import java.util.Objects;

public class DiplomaResponse {
    private Diploma diploma;
    private String tx;

    public DiplomaResponse(@JsonProperty("diploma") Diploma diploma,
                           @JsonProperty("tx") String tx) {
        this.diploma = diploma;
        this.tx = tx;
    }

    public Diploma getDiploma() {
        return diploma;
    }

    public String getTx() {
        return tx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiplomaResponse response = (DiplomaResponse) o;
        return Objects.equals(diploma, response.diploma) && Objects.equals(tx, response.tx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diploma, tx);
    }

    @Override
    public String toString() {
        return "DiplomaResponse{" +
                "diploma=" + diploma +
                ", tx='" + tx + '\'' +
                '}';
    }
}
