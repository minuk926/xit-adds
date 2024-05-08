package cokr.xit.adds.inf.iros.model;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.iros.model
 * fileName    : DrugPrdtMcpnDtlDto
 * author      : limju
 * date        : 2024-03-20
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-20    limju       최초 생성
 *
 * </pre>
 */
@Schema(name = "DrugPrdtMcpnDtlReq", description = "의약 제품 주성분 상세 정보 조회 request")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugPrdtMcpnDtlReq {
	@Schema(requiredMode = REQUIRED, title = "서비스 키", description = "서비스 키", example = "0fTkYnZU6XjaAPAp6GzKW9Q6fYq8iaoSH9wKUZwz2PBjRXGM04aUgtO3a61xYGjL8nFZn4fjLGS9XErhMSXq%2Bw%3D%3D")
	@NotEmpty(message = "서비스 키는 필수 입니다")
	private String serviceKey;

	@Schema(requiredMode = REQUIRED, title = "페이지 번호", description = "페이지 번호", example = "1")
	@Pattern(regexp = "[0-9]{1,}", message = "페이지 번호는 필수 입니다")
	private String pageNo;

	@Schema(requiredMode = REQUIRED, title = "페이지당 갯수", description = "페이지당 갯수", example = "10")
	@Pattern(regexp = "[0-9]{1,}", message = "페이지당 갯수는 필수 입니다")
	private String numOfRows;

	@Schema(requiredMode = REQUIRED, title = "타입", description = "타입(json | xml)", allowableValues = {"json", "xml"}, example = "json")
	@NotEmpty(message = "타입(json | xml)은 필수 입니다")
	private String type;

	@Schema(title = "업체허가번호", description = "업체허가번호", example = " ")
	private String entrpsPrmisnNo;

	@Schema(title = "제품명", description = "제품명", example = "포도당주사액")
	private String prduct;

	@Schema(title = "업체명", description = "업체명", example = " ")
	private String entrps;

	@Schema(title = "사업자등록번호", description = "사업자등록번호", example = " ")
	private String bizrno;

	// public DrugPrdtMcpnDtlRequest(
	// 	String serviceKey,
	// 	String pageNo,
	// 	String numOfRows,
	// 	String type,
	// 	String entrpsPrmisnNo,
	// 	String prduct,
	// 	String entrps,
	// 	String bizrno
	// ) {
	// 	this.serviceKey = serviceKey;
	// 	this.pageNo = pageNo;
	// 	this.numOfRows = numOfRows;
	// 	this.type = type;
	// 	this.entrpsPrmisnNo = ApiUtil.getUtf8UrlEncoding(entrpsPrmisnNo);
	// 	this.prduct = ApiUtil.getUtf8UrlEncoding(prduct);
	// 	this.entrps = ApiUtil.getUtf8UrlEncoding(entrps);
	// 	this.bizrno = bizrno;
	// }
}
