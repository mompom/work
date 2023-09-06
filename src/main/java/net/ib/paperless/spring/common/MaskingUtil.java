package net.ib.paperless.spring.common;

/**
 * 기존 MYSQL 프로시저에서 사용하는 로직 그대로 사용
 */
public class MaskingUtil {


    public static String maskAccountHolderName(String accountHolderName) {
        if (accountHolderName == null || accountHolderName.length() < 2) {
            return "*";
        }
        return accountHolderName.substring(0, 2) + "*";
    }

    public static String maskAccountHolderNumber(String accountHolderNumber) {
        if (accountHolderNumber == null || accountHolderNumber.length() < 5) {
            return "*";
        }
        return accountHolderNumber.substring(0, 5) + "*";
    }

    public static String maskAccountBankName(String accountBankName) {
        if (accountBankName == null || accountBankName.isEmpty()) {
            return "*";
        }
        return accountBankName.charAt(0) + "*";
    }

    public static String maskAccountSSNNumber(String accountSSNNumber) {
        if (accountSSNNumber == null || accountSSNNumber.length() < 2) {
            return "*";
        }
        return accountSSNNumber.substring(0, 2) + "*";
    }

    public static void main(String[] args) {
        System.out.println(maskAccountHolderName("박유진")); // 박유*
        System.out.println(maskAccountHolderNumber("59350201238928")); // 59350*
        System.out.println(maskAccountBankName("국민은행")); // 국민*
        System.out.println(maskAccountSSNNumber("910710")); // 91*
    }
}
