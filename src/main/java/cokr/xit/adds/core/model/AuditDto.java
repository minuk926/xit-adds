package cokr.xit.adds.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * <pre>
 * description : 
 *
 * packageName : cokr.xit.adds.core.model
 * fileName    : AuditDto
 * author      : limju
 * date        : 2024-04-11
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-11    limju       최초 생성
 *
 * </pre>
 */
@Schema(name = "AuditDto", description = "AuditDto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuditDto {
	@JsonIgnore
	@Builder.Default
	private String useYn = "Y";

	@JsonIgnore
	private String regDt;

	@JsonIgnore
	@Setter
	private String rgtr;

	@JsonIgnore
	private String mdfcnDt;

	@JsonIgnore
	@Setter
	private String mdfr;
}
