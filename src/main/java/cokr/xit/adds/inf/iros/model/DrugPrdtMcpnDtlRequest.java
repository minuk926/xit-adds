package cokr.xit.adds.inf.iros.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import cokr.xit.adds.core.util.ApiUtil;
import lombok.Builder;

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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
public class DrugPrdtMcpnDtlRequest {
	private String serviceKey;
	private Integer pageNo;
	private Integer numOfRows;
	private String type;
	private String entrpsPrmisnNo;
	private String prduct;
	private String entrps;
	private String bizrno;

    public DrugPrdtMcpnDtlRequest() {
    }
	public DrugPrdtMcpnDtlRequest(
		String serviceKey,
		Integer pageNo,
		Integer numOfRows,
		String type,
		String entrpsPrmisnNo,
		String prduct,
		String entrps,
		String bizrno
	) {
		this.serviceKey = serviceKey;
		this.pageNo = pageNo;
		this.numOfRows = numOfRows;
		this.type = type;
		this.entrpsPrmisnNo = ApiUtil.getUtf8UrlEncoding(entrpsPrmisnNo);
		this.prduct = ApiUtil.getUtf8UrlEncoding(prduct);
		this.entrps = ApiUtil.getUtf8UrlEncoding(entrps);
		this.bizrno = bizrno;
	}

	public String serviceKey() {
		return serviceKey;
	}

	public Integer pageNo() {
		return pageNo;
	}

	public Integer numOfRows() {
		return numOfRows;
	}

	public String type() {
		return type;
	}

	@JsonProperty("Entrps_prmisn_no")
	public String entrpsPrmisnNo() {
		return entrpsPrmisnNo;
	}

	@JsonProperty("Prduct")
	public String prduct() {
		return prduct;
	}

	@JsonProperty("Entrps")
	public String entrps() {
		return entrps;
	}

	@JsonProperty("Bizrno")
	public String bizrno() {
		return bizrno;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		var that = (DrugPrdtMcpnDtlRequest)obj;
		return Objects.equals(this.serviceKey, that.serviceKey) &&
			Objects.equals(this.pageNo, that.pageNo) &&
			Objects.equals(this.numOfRows, that.numOfRows) &&
			Objects.equals(this.type, that.type) &&
			Objects.equals(this.entrpsPrmisnNo, that.entrpsPrmisnNo) &&
			Objects.equals(this.prduct, that.prduct) &&
			Objects.equals(this.entrps, that.entrps) &&
			Objects.equals(this.bizrno, that.bizrno);
	}

	@Override
	public int hashCode() {
		return Objects.hash(serviceKey, pageNo, numOfRows, type, entrpsPrmisnNo, prduct, entrps, bizrno);
	}

	@Override
	public String toString() {
		return "DrugPrdtMcpnDtlRequest[" +
			"serviceKey=" + serviceKey + ", " +
			"pageNo=" + pageNo + ", " +
			"numOfRows=" + numOfRows + ", " +
			"type=" + type + ", " +
			"entrpsPrmisnNo=" + entrpsPrmisnNo + ", " +
			"prduct=" + prduct + ", " +
			"entrps=" + entrps + ", " +
			"bizrno=" + bizrno + ']';
	}
}
