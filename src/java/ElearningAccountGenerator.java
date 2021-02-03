import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ElearningAccountGenerator {

    /*
        generateElearningAccount used to generate elearning account
        requestNo is the request no received from 3s
        courseName is the course name linked with license type in table licType for this request no
     */
    public static ElearningAccount generateElearningAccount(String requestNo, String courseName){

        //validation and reinitiation of request no
        if(requestNo == null || requestNo.equalsIgnoreCase("") || requestNo.length() == 0){
            requestNo = "123456";
        }

        //validation and reinitiation of courseName
        if(courseName == null || courseName.equalsIgnoreCase("") || courseName.length() == 0){
            courseName = "LV";
        }

        //conversion of request no to long request no
        long requestNoLong = 0;
        try {
            requestNoLong = Long.parseLong(requestNo);
        }catch (Exception ex){
            requestNoLong = 123456;
        }


        String separator = "_";
        String userName;
        String password = "aman@73";
        //encrypt username and password
        String requestAbbrevUsername = encryptNumber(requestNoLong,2);
        String requestAbbrevPassword = encryptNumber(requestNoLong,4);

        //construct username and password
        userName = courseName + separator + requestNo + separator + requestAbbrevUsername;
        password = password + requestAbbrevPassword;

        //filling object with username and password
        ElearningAccount elearningAccount = new ElearningAccount();
        elearningAccount.setUsername(userName);
        elearningAccount.setPassword(password);

        return elearningAccount;

    }

    /*
    encryptNumber it ecrypts the num using the division parameter
     */
    private static String  encryptNumber(long num , int division) {
        String encryptedValue;

        int result = 0;
        while(num > 0) {
            result += num % 10;
            num /= 10;
        }
        Double dResult = new Double(result)/division;

        DecimalFormat formatter = new DecimalFormat("#.0");
        formatter.setRoundingMode(RoundingMode.DOWN);
        encryptedValue = formatter.format(dResult);
        encryptedValue = encryptedValue.replace(".0","").replace(".","");

        return encryptedValue;
    }


}
