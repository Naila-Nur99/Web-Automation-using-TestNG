package page;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegDataSet {

    @DataProvider(name = "RegCSVData")
    public Object[][] getCSVData() throws IOException {
        String csvFilePath="./src/test/resources/userReg.csv";
        List<Object[]> data=new ArrayList<>();
        CSVParser csvParser= new CSVParser(new FileReader(csvFilePath), CSVFormat.DEFAULT.withFirstRecordAsHeader());

        for(CSVRecord csvRecord: csvParser){
            String firstName=csvRecord.get("firstName");
            String lastName=csvRecord.get("lastName");
            String email=csvRecord.get("email");
            String password=csvRecord.get("password");
            String phoneNumber=csvRecord.get("phoneNumber");
            String address=csvRecord.get("address");
            data.add(new Object[]{firstName,lastName,email,password,phoneNumber,address});
        }
        return data.toArray(new Object[0][]);
    }

}
