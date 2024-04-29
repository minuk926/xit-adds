package cokr.xit.adds.core.util;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cokr.xit.adds.core.spring.exception.ApiCustomException;
import cokr.xit.foundation.data.JSON;
import cokr.xit.foundation.web.WebClient;

/**
 * <pre>
 * description : 
 *
 * author      : limju
 * date        : 2024-04-04
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-04    limju       최초 생성
 *
 * </pre>
 */
public class ApiUtil {

	/**
	 * 유효성 검증
	 * errList가 null인 경우 유효성 검증 실패 시 예외 throw ApiCustomException
	 *
	 * @param t         validatable object
	 * @param errList   error list
	 * @param validator validator
	 */
	public static <T> void validate(final T t, final List<String> errList, final Validator validator) {
		final Set<ConstraintViolation<T>> list = validator.validate(t);

		if(!list.isEmpty()) {
			final List<String> errors = list.stream()
				.map(row -> String.format("%s=%s", row.getPropertyPath(), row.getMessageTemplate()))
				.toList();

			// 추가적인 유효성 검증이 필요 없는 경우
			if(errList == null){
				if(!errors.isEmpty())   throw ApiCustomException.create(errors.toString());
				return;
			}
			errList.addAll(errors);
		}
	}

	public static void checkYmdError(String dt, String fieldName) {
		String msg = "유효한 일자가 아닙니다.";
		if(ObjectUtils.isEmpty(fieldName)){
			msg = "ymd=" + msg;
		}else{
			msg = fieldName + "=" + msg;
		}
		try {
			final LocalDate rdt = LocalDate.parse(dt, DateTimeFormatter.ofPattern("yyyyMMdd"));
			if(!dt.equals(DateTimeFormatter.ofPattern("yyyyMMdd").format(rdt))){
				throw ApiCustomException.create(msg);
			}
		} catch (DateTimeParseException e) {
			throw ApiCustomException.create(msg);
		}
	}

	public static void checkDatetimeError(String dt, String fieldName) {
		String msg = "유효한 일시가 아닙니다.";
		if(ObjectUtils.isEmpty(fieldName)){
			msg = "ymd=" + msg;
		}else{
			msg = fieldName + "=" + msg;
		}
		try {
			final LocalDate rdt = LocalDate.parse(dt, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
			if(!dt.equals(DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(rdt))){
				throw ApiCustomException.create(msg);
			}
		} catch (DateTimeParseException e) {
			throw ApiCustomException.create(msg);
		}
	}

	public static String getUtf8UrlEncoding(final String str) {
		if(!org.springframework.util.StringUtils.hasText(str)) return StringUtils.EMPTY;

		try {
			return URLEncoder.encode(
				org.apache.commons.codec.binary.StringUtils.newStringUtf8(
					org.apache.commons.codec.binary.StringUtils.getBytesUtf8(str)),
				StandardCharsets.UTF_8
			);
		} catch (Exception e) {
			throw ApiCustomException.create(e.getLocalizedMessage());
		}
	}

	/**
	 * NimsApi 호출 - x-www-form-urlencoded 방식
	 * @param uri String
	 * @param cls T
	 * @return String
	 */
	public static <T> String callNimsApi(String uri, T cls) {
		HttpResponse<String> rslt = new WebClient().post(request -> {
			request.contentType(WebClient.Request.ContentType.FORM);
			request.uri(uri);
			toData(request, cls);
		});
		return rslt.body();
	}

	/**
	 * irosApi 호출 - x-www-form-urlencoded 방식
	 * @param uri String
	 * @param param String
	 * @return String
	 */
	public static String callIrosApi(String uri, String param) {
		HttpResponse<String> rslt = new WebClient().get(request -> {
			request.header(HttpHeaders.CONTENT_TYPE, "application/json");
			request.contentType(WebClient.Request.ContentType.FORM);
			request.uri(uri + param);
		});
		return rslt.body();
	}

	/**
	 * Object -> class로 변환
	 * @param obj Object
	 * @param cls Class
	 * @return T
	 */
	public static <T> T toObjByObj(final Object obj, final Class<T> cls) {
		try {
			return ObjectUtils.isNotEmpty(obj)? new JSON().getObjectMapper().convertValue(obj, cls) : null;
		} catch (IllegalArgumentException e) {
			throw ApiCustomException.create(e.getLocalizedMessage());
		}
	}

	public static void toData(final WebClient.Request request, final Object obj){
		if(ObjectUtils.isEmpty(obj))	return;
		//MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		JSONObject jsonObj = toObjByObj(obj, JSONObject.class);
		for (Object key : jsonObj.keySet()) {
			request.data((String) key, (String) jsonObj.get(key));
		}
	}

	/**
	 * Object
	 * -> MultiValueMap<String, String> return
	 * @param obj Object
	 * @return MultiValueMap<String, String>
	 */
	public static MultiValueMap<String, String> toMultiValue(final Object obj){
		if(ObjectUtils.isEmpty(obj))	return null;
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		JSONObject jsonObj = toObjByObj(obj, JSONObject.class);
		for (Object key : jsonObj.keySet()) {
			formData.add((String) key, (String) jsonObj.get(key));
		}
		return formData;
	}
}
