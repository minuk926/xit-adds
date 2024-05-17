package cokr.xit.adds.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang3.ObjectUtils;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.type.TypeReference;

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

	public static <T> T toObjByObj(final Object obj, final TypeReference<T> typeRef) {
		try {
			return ObjectUtils.isNotEmpty(obj)? new JSON().getObjectMapper().convertValue(obj, typeRef) : null;
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

	public static boolean validateXml(final String xmlStr, final String xsdFilePathName)  {
		try {
			FileInputStream fis = new FileInputStream(xsdFilePathName);
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new StreamSource(fis));

			javax.xml.validation.Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(xmlStr)));
			return true;

		} catch (SAXException | IOException e) {
			throw ApiCustomException.create(e.getMessage());
		}
	}

	public static boolean validateXmlFromXmlStr(final String xmlStr, final String xsdFilePath)  {
		try {
			FileInputStream fis = new FileInputStream(xsdFilePath);
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new StreamSource(fis));

			javax.xml.validation.Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(xmlStr)));
			return true;

		} catch (SAXException | IOException e) {
			throw ApiCustomException.create(e.getMessage());
		}
	}

	public static boolean validateXmlFromFile(String xmlFilePath, final String xsdFilePath)  {
		try (FileInputStream fileInputStream = new FileInputStream(xmlFilePath)) {
			byte[] bytes = fileInputStream.readAllBytes();
			return validateXmlFromXmlStr(new String(bytes), xsdFilePath);
		}catch (IOException e) {
			throw ApiCustomException.create(e.getMessage());
		}
	}
}
