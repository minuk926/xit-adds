package cokr.xit.adds.core;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;

import cokr.xit.adds.core.spring.exception.ApiCustomException;
import lombok.Getter;

/**
 * <pre>
 * description : 상수 정의
 * author      : julim
 * date        : 2023-04-28
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2023-04-28    julim       최초 생성
 *
 * </pre>
 */
public class Constants {
    public static final String API_URL_PATTERNS = "/*";
    public static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    public static final String DATE_REGX = "^[12]\\d{3}((0[1-9])|(1[0-2]))(0[1-9]|[12]\\d|3[01])$";
    public static final String CUR_DATE_REGX = "^20([2-9][0-9])((0[1-9])|(1[0-2]))(0[1-9]|[12][0-9]|3[01])$";
    public static final String CUR_DTM_REGX = "^20([2-9]\\d)((0[1-9])|(1[0-2]))(0[1-9]|[12]\\d|3[01])(0[1-9]|1\\d|2[0-3])([0-5]\\d)([0-5]\\d)$";
    public static final String PHONE_REGX = "^01([0|1|6|7|8|9])-?(\\d{3,4})-?(\\d{4})$";
    public static final String TEL_REGX = "^\\d{2,3}-?\\d{3,4}-?\\d{4}$";
    public static final String EMAIL_REGX = "^[a-zA-Z0-9]([._%+-]?[a-zA-Z0-9])*@([._%+-]?[a-zA-Z0-9])*\\.[a-zA-Z]{2,6}$";
    public static final String NIMS_API_USER_ID = "nims-api";

    /**
     * <pre>
     * 보고 유형 코드 - ADDS02
     * 0:신규, 1:취소, 2:변경
     * </pre>
     */
    @Getter
    public enum RPT_TY_CD {
        NEW("0", "신규"),	//신규
        CANCEL("1", "취소"),	//취소
        MODIFY("2", "변경")	//변경
        ;

        private final String code;
        private final String nm;

        RPT_TY_CD(String code, String nm) {
            this.code = code;
            this.nm = nm;
        }

        public static String getName(final String code){
            return Arrays.stream(RPT_TY_CD.values())
                .filter(ssc -> ssc.getCode().equals(code))
                .findFirst()
                .map(RPT_TY_CD::getNm)
                .orElseThrow(() -> ApiCustomException.create(String.format("미 정의된 보고 유형 코드[%s]", code)));
            //return eNum.getNm();
        }
    }

    /**
     * <pre>
     * 폐기 구분 코드 - ADDS03
     * 1:보건소폐기, 2:공무원입회, 4:사고마약류
     * </pre>
     */
    @Getter
    public enum DSUSE_SE_CD {
        PHC("1", "보건소폐기"),
        GOV("2", "공무원입회"),
        ACDT("4", "사고마약류")
        ;

        private final String code;
        private final String nm;

        DSUSE_SE_CD(String code, String nm) {
            this.code = code;
            this.nm = nm;
        }

        public static String getName(final String code){
            return Arrays.stream(DSUSE_SE_CD.values())
                .filter(ssc -> ssc.getCode().equals(code))
                .findFirst()
                .map(DSUSE_SE_CD::getNm)
                .orElseThrow(() -> ApiCustomException.create(String.format("미 정의된 폐기 보고 코드[%s]", code)));
            //return eNum.getNm();
        }
    }

    /**
     * <pre>
     * 폐기 사유 코드 - ADDS04
     * 01:사고마약류(파손), 02:사고마약류(변질,부패), 03:유효기한경과, 04:유효기한임박,
     * 05:사용중단, 07:폐업, 08:환자반납, 09:기타, 12:제조공정중폐기물
     * </pre>
     */
    @Getter
    public enum DSUSE_PRV_CD {
        DMG("01", "사고마약류(파손)"),
        SPOIL("02", "사고마약류(변질,부패)"),
        EXPIRED("03", "유효기한경과"),
        EXPIRED_IMMINENT("04", "유효기한임박"),
        STOP("05", "사용중단"),
        CLOSURE("07", "폐업"),
        REFUND("08", "환자반납"),
        ETC("09", "기타"),
        DISUSE("12", "제조공정중폐기물")
        ;

        private final String code;
        private final String nm;

        DSUSE_PRV_CD(String code, String nm) {
            this.code = code;
            this.nm = nm;
        }

        public static String getName(final String code){
            return Arrays.stream(DSUSE_PRV_CD.values())
                .filter(ssc -> ssc.getCode().equals(code))
                .findFirst()
                .map(DSUSE_PRV_CD::getNm)
                .orElseThrow(() -> ApiCustomException.create(String.format("미 정의된 폐기 사유 코드[%s]", code)));
            //return eNum.getNm();
        }
    }

    /**
     * <pre>
     * 폐기 방법 코드 - ADDS05
     * 1:소각, 2:중화, 3:가수분해, 4:산화, 5:환원, 6:희석, 7:매몰, 8:기타, 9:사고
     * </pre>
     */
    @Getter
    public enum DSUSE_MTH_CD {
        INCINERATION("1", "소각"),
        NEUTRALIZATION("2", "중화"),
        HYDROLYSIS("3", "가수분해"),
        OXIDATION("4", "산화"),
        RETURN("5", "환원"),
        DILUTION("6", "희석"),
        BURIAL("7", "매몰"),
        ETC("8", "기타"),
        ACCIDENT("9", "사고")
        ;

        private final String code;
        private final String nm;

        DSUSE_MTH_CD(String code, String nm) {
            this.code = code;
            this.nm = nm;
        }

        public static String getName(final String code){
            return Arrays.stream(DSUSE_MTH_CD.values())
                .filter(ssc -> ssc.getCode().equals(code))
                .findFirst()
                .map(DSUSE_MTH_CD::getNm)
                .orElseThrow(() -> ApiCustomException.create(String.format("미 정의된 폐기 방법 코드[%s]", code)));
            //return eNum.getNm();
        }
    }

    /**
     * <pre>
     * 이동 유형 코드 - ADDS06
     * 1102:폐기출고, 1170:폐기재고미차감
     * </pre>
     */
    @Getter
    public enum MVMN_TY_CD {
        DSUSE_OUT("1102", "폐기출고"),
        NOT_DSUSE_OUT("1170", "폐기재고미차감")
        ;

        private final String code;
        private final String nm;

        MVMN_TY_CD(String code, String nm) {
            this.code = code;
            this.nm = nm;
        }

        public static String getName(final String code){
            return Arrays.stream(MVMN_TY_CD.values())
                .filter(ssc -> ssc.getCode().equals(code))
                .findFirst()
                .map(MVMN_TY_CD::getNm)
                .orElseThrow(() -> ApiCustomException.create(String.format("미 정의된 이동 유형 코드[%s]", code)));
            //return eNum.getNm();
        }
    }

    /**
     * 인증정보 저장(세션)
     */
    public enum AuthSaveSession {
        LOGIN_VO("LoginVO")
        ;

        private final String code;

        AuthSaveSession(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

    /**
     * 인증정보 저장 방식
     *
     */
    public enum AuthSaveType {
        SECURITY("security"),	// SessionCreationPolicy.STATELESS인 경우는 SecurityContext 사용불가
        SESSION("session"),   // TOKEN도 사용 가능은 하지만...
        HEADER("header");     // TOKEN

        private final String code;

        AuthSaveType(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

    public enum JwtToken {
        // 토큰헤더명,
        HEADER_NAME("Authorization"),
        GRANT_TYPE("Bearer"),
        ACCESS_TOKEN_NAME("accessToken"),
        REFRESH_TOKEN_NAME("refreshToken"),
        AUTHORITIES_KEY("role"),
        TOKEN_USER_NAME("userName"),
        TOKEN_USER_MAIL("userEmail"),
        TOKEN_USER_ID("userId");

        private final String code;

        JwtToken(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

    /**
     * JWT Token 통신 방식 지정
     * COOKIE : accessToken - header, refreshToken - cookie
     * HEADER : accessToken - header, refreshToken - DTO
     * DTO : accessToken - header, refreshToken - DTO
     */
    public enum JwtTokenParamType {
        COOKIE,
        HEADER,
        DTO
    }

    /**
     * 카카오페이 전자문서 요청 헤더 필드명
     */
    public enum HeaderName {
        TOKEN(HttpHeaders.AUTHORIZATION),	 //Token
        UUID("Contract-Uuid"),   //Contract-Uuid
        ;     // TOKEN

        private final String code;

        HeaderName(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }
}
