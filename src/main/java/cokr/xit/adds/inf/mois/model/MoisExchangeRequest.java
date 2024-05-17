package cokr.xit.adds.inf.mois.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.mois.model
 * fileName    : MoisApiRequest
 * author      : limju
 * date        : 2024-04-21
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-21    limju       최초 생성
 *
 * </pre>
 */
@Schema(name = "MoisExchangeRequest", description = "전자 결재 연계 request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoisExchangeRequest {

    /**
     * 사용자 ID
     */
    @Schema(requiredMode = REQUIRED, title = "사용자ID", description = "사용자ID", example = "admin")
    @NotEmpty(message = "사용자 ID는 필수 입니다")
    private String userId;

    /**
     * 폐기관리 ID - 연계시 administrativeNum(COMMON > ADMINISTRATIVE_NUM으로 사용)
     */
    @Schema(requiredMode = REQUIRED, title = "폐기관리ID", description = "폐기관리ID-행정정보처리번호로 사용", example = "admin")
    @NotEmpty(message = "폐기관리ID ID는 필수 입니다")
    private String dscdmngId;

    @Schema(requiredMode = REQUIRED, title = "마약류취급자식별번호", description = "마약류취급자식별번호", example = "D04343033")
    @NotEmpty(message = "마약류취급자식별번호는 필수 입니다")
    private String bsshCd;

    private List<MultipartFile> files;
}
