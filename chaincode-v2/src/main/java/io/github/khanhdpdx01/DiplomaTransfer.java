package io.github.khanhdpdx01;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.ArrayList;
import java.util.List;

@Contract(name = "chaincode")
@Default
public class DiplomaTransfer implements ContractInterface {
    private final Genson genson = new Genson();

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Diploma createDiploma(final Context ctx, String serialNumber, String userId, String firstName, String lastName,
                                 String dateOfBirth, String gender, String placeOfBirth, String grade, String level,
                                 String rank, String modeOfStudy, String speciality, String graduation, String dateOfGraduation,
                                 String refNumber, String status, String GPA, String totalCredits,
                                 String trainingLanguage, String time, String dateOfEnrollment, String major, String session,
                                 String diplomaLink, String appendixLink) {
        ChaincodeStub stub = ctx.getStub();

        if (DiplomaExists(ctx, serialNumber)) {
            String errorMsg = String.format("Diploma  %s already exists", serialNumber);
            System.out.println(errorMsg);
            throw new ChaincodeException(errorMsg, DiplomaTransferErrors.DIPLOMA_ALREADY_EXISTS.toString());
        }

        Diploma diploma = new Diploma(serialNumber, userId, firstName, lastName,
                dateOfBirth, gender, placeOfBirth, grade, level,
                rank, modeOfStudy, speciality, graduation, dateOfGraduation,
                refNumber, status, GPA, totalCredits,
                trainingLanguage, time, dateOfEnrollment, major, session, diplomaLink, appendixLink);

        String json = genson.serialize(diploma);
        stub.putStringState(serialNumber, json);

        return diploma;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String searchBySerialNumber(final Context ctx, final String serialNumber) {
        ChaincodeStub stub = ctx.getStub();
        String diplomaJson = stub.getStringState(serialNumber);

        if (diplomaJson == null && diplomaJson.isEmpty()) {
            String errorMsg = String.format("Diploma %s does not exist", serialNumber);
            System.out.println(errorMsg);
            throw new ChaincodeException(errorMsg, DiplomaTransferErrors.DIPLOMA_NOT_FOUND.toString());
        }

        DiplomaQueryResult response = new DiplomaQueryResult(genson.deserialize(diplomaJson, Diploma.class), stub.getTxId());
        System.out.println(response);
        return genson.serialize(response);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String getAllDiplomas(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        List<Diploma> queryResult = new ArrayList<>();
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");

        for (KeyValue key : results) {
            Diploma diploma = genson.deserialize(key.getStringValue(), Diploma.class);
            queryResult.add(diploma);
        }

        String response = genson.serialize(queryResult);
        return response;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String searchByPersonalInfo(final Context ctx, String firstName, String lastName, String dateOfBirth) {
        ChaincodeStub stub = ctx.getStub();

        List<Diploma> queryResult = new ArrayList<>();
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");

        for (KeyValue key : results) {
            Diploma diploma = genson.deserialize(key.getStringValue(), Diploma.class);
            if (diploma.getFirstName().equals(firstName)
                    && diploma.getLastName().equals(lastName)
                    && diploma.getDateOfBirth().equals(dateOfBirth)) {
                queryResult.add(diploma);
            }
        }

        String response = genson.serialize(queryResult);
        return response;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void deleteDiploma(final Context ctx, String diplomaId) {
        ChaincodeStub stub = ctx.getStub();

        if (!DiplomaExists(ctx, diplomaId)) {
            String errorMsg = String.format("Diploma %s does not exists", diplomaId);
            System.out.println(errorMsg);
            throw new ChaincodeException(errorMsg, DiplomaTransferErrors.DIPLOMA_NOT_FOUND.toString());
        }

        stub.delState(diplomaId);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean DiplomaExists(Context ctx, String serialNumber) {
        ChaincodeStub stub = ctx.getStub();
        String diplomaJson = stub.getStringState(serialNumber);

        return (diplomaJson != null && !diplomaJson.isEmpty());
    }

    private enum DiplomaTransferErrors {
        DIPLOMA_NOT_FOUND,
        DIPLOMA_ALREADY_EXISTS
    }
}
