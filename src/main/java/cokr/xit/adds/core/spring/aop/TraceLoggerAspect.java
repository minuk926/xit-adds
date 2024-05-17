package cokr.xit.adds.core.spring.aop;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.simple.JSONObject;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.type.TypeReference;

import cokr.xit.adds.core.auth.model.LoggingDTO;
import cokr.xit.adds.core.auth.service.IApiLoggingService;
import cokr.xit.adds.core.model.ApiBaseResponse;
import cokr.xit.adds.core.spring.exception.ErrorParse;
import cokr.xit.foundation.data.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * description : logging trace aspect
 *               공통 core 모듈의 LoggerAspect 상속 -> traceLogging / traceLoggingError / traceLoggingResult 구현
 *
 *               Logging trace 구현시
 *               - MDC(Mapped Diagnostic Context : logback, log4j에 포함) 사용 로깅
 *               - ThreadLocal 사용
 *               - nginx : proxy_set_header X-RequestID $request_id;
 *               - logback log pattern : [traceId=%X{request_id}]
 *
 *               app.slack-webhook.enabled: true인 경우 slack push
 *               Slack webhook : SlackWebhookPush
 *
 * author      : julim
 * date        : 2024-04-08
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-08    julim       최초 생성
 * </pre>
 */

@Slf4j
@Aspect
@Component
public class TraceLoggerAspect {

    @Value("${app.log.parameter-enabled:false}")
    private boolean isParameterLogEnabled;

    // 응답결과 로그
    @Value("${app.log.response-enabled:false}")
    private boolean isResLogEnabled;

    @Value("${app.log.mdc.enabled:true}")
    private boolean isMdcLogEnabled;

    @Value("#{'${app.log.mdc.exclude-patterns}'.split(',')}")
    private String[] excludes;

    private final IApiLoggingService apiLoggingService;
    private static final String REQUEST_TRACE_ID = "request_trace_id";

    private static JSON json = new JSON();

    public TraceLoggerAspect(@Lazy IApiLoggingService apiLoggingService) {
        this.apiLoggingService = apiLoggingService;
    }

    @Pointcut("execution(public * egovframework..web.*.*(..)) || execution(public * cokr.xit..web.*.*(..))")
    public void requestPointCut() {
    }

    @Pointcut("execution(public * egovframework..*.*(..)) || execution(public * cokr.xit..*.*(..))")
    public void errorPointCut() {
    }

    @Pointcut("execution(public * egovframework..web.*.*(..)) || execution(public * cokr.xit..web.*.*(..))")
    public void resultPointCut() {
    }

    @Before(value = "requestPointCut()")
    public void logBefore(JoinPoint joinPoint) {
        if(!isParameterLogEnabled)    return;
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null? attributes.getRequest(): null;
        assert request != null;
        requestLog(request, getParams(request));
    }

    @Around(value = "@annotation(cokr.xit.adds.core.spring.annotation.TraceLogging)")
    public Object serviceTraceLogging(final ProceedingJoinPoint pjp) throws Throwable {
        if(!isMdcLogEnabled)    return pjp.proceed();

        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null? attributes.getRequest(): null;

        //traceApiLogging(JsonUtils.toObjByObj(pjp.getArgs()[0], JSONObject.class), request);
        traceApiLogging(json.parse(json.stringify(pjp.getArgs()[0]), new TypeReference<JSONObject>() {}), request);
        Object result = pjp.proceed();

        //noinspection rawtypes
        if(result instanceof CompletableFuture future){

            while(true) {
                if (future.isDone()) break;
            }
            traceApiLoggingResult(future.get());
        }else{
            traceApiLoggingResult(result);
        }
        return result;
    }

    @AfterReturning(pointcut = "resultPointCut()", returning = "result")
    public void logAfterReturning(final JoinPoint jp, final Object result) {
        if(isResLogEnabled)  {
            log.info("{}{}{}",
                "\n//============================= Http Response ==============================",
                //LogUtils.toString(result),
                jsonEnterConvert(json.stringify(result)),
                "\n=========================================================================//"
            );
        }
    }

    @AfterThrowing(value = "errorPointCut()", throwing="error")
    public void afterThrowingProceed(final JoinPoint jp, final Throwable error) {
        traceApiLoggingError(jp, error);
    }

    /**
     * 배치 실행시 여기에서 set한 MDC 값이 batch 모듈에서 reading이 불가하여, 배치 tasklet에서 set한 trace_id로 set
     * @param json JSONObject
     * @param request HttpServletRequest
     */
    protected void traceApiLogging(final JSONObject json, final HttpServletRequest request) {
        String uri = "";
        if(request != null) {
            uri = request.getRequestURI();
//            if(Arrays.stream(excludes).anyMatch(uri::matches))     return;

            MDC.put(REQUEST_TRACE_ID,
                StringUtils.defaultString(MDC.get("request_trace_batch_id"), UUID.randomUUID().toString().replaceAll("/-/g", "")));
            MDC.put("method", request.getMethod());
            MDC.put("uri", uri);
            MDC.put("ip", request.getRemoteAddr());
            MDC.put("sessionId", request.getSession().getId());

        // batch로 실행되는 경우 task에서 설정 : uri / method
        }else{
            // request_id는 중복 처리 되면 않되므로 여기에서 생성
            MDC.put(REQUEST_TRACE_ID, StringUtils.defaultString(MDC.get("request_trace_batch_id"), UUID.randomUUID().toString().replaceAll("-", "")));
        }
//TODO::systemId, reqSystemId 설정 필요
log.info("@@@@@@@@@@@@@@@@@로깅 start : [\n{}\n]",MDC.getCopyOfContextMap());
        String params = resetJsonMasking(json);
        String systemId = ObjectUtils.isNotEmpty(uri)? ApiSystemId.compareByUri(uri).getCode(): ApiSystemId.valueOf("NONE").getCode();
        MDC.put("systemId", "INF");
        MDC.put("reqSystemId", systemId);
        MDC.put("param", params);
        MDC.put("accessToken", "");

        LoggingDTO loggingDTO = LoggingDTO.builder()
            .requestId(MDC.getCopyOfContextMap().get(REQUEST_TRACE_ID))
            .systemId("INF")
            .reqSystemId(systemId)
            .method(MDC.getCopyOfContextMap().get("method"))
            .uri(MDC.getCopyOfContextMap().get("uri"))
            .param(params)
            .ip(MDC.getCopyOfContextMap().get("ip"))
            .accessToken("")
            .sessionId(MDC.getCopyOfContextMap().get("sessionId"))
            .build();
        apiLoggingService.saveApiLogging(loggingDTO);

    }

    protected void traceApiLoggingResult(final Object result) {
        String success = "true";

        LoggingDTO reqDTO = LoggingDTO
            .builder()
            .requestId(MDC.get(REQUEST_TRACE_ID))
            .success(success)
            .response(getResult(result))
            .message(HttpStatus.OK.name())
            .build();
        //}
        apiLoggingService.modifyApiLogging(reqDTO);
        //loggingService.saveLogging(reqDTO);

        log.info("@@@@@@@@@@@@@@로깅 end[\n{}\n]", MDC.getCopyOfContextMap());

        //if(RequestContextHolder.getRequestAttributes() != null)
        MDC.clear();
        //}
    }

    private String getResult(final Object o){
        //noinspection rawtypes
        // if(o instanceof Future future) {
        //     try {
        //         return JsonUtils.toJson(future.get());
        //     } catch (InterruptedException ie){
        //         // thread pool에 에러 상태 전송
        //         Thread.currentThread().interrupt();
        //         throw BizRuntimeException.create(ie);
        //
        //     } catch (ExecutionException ee) {
        //         throw BizRuntimeException.create(ee);
        //     }
        // }else{
        //     return JsonUtils.toJson(o);
        // }
        return json.stringify(o);
    }

    protected void traceApiLoggingError(final JoinPoint jp, final Throwable e) {
        log.info("MDC request_trace_id :: {}", MDC.get(REQUEST_TRACE_ID));
        if(ObjectUtils.isEmpty(MDC.get(REQUEST_TRACE_ID)))    return;

        @SuppressWarnings("rawtypes")
        ApiBaseResponse dto = ErrorParse.extractError(e);

        apiLoggingService.modifyApiLogging(
            LoggingDTO
                .builder()
                .requestId(MDC.get(REQUEST_TRACE_ID))
                .success("false")
                .response(json.stringify(dto))
                .message(dto.getMessage())
                .build());
        log.info("@@@@@@@@@@@@ 로깅 end[\n{}\n]", MDC.getCopyOfContextMap());
        //if(RequestContextHolder.getRequestAttributes() != null)
        MDC.clear();
    }


    private void requestLog(HttpServletRequest request, JSONObject params) {

        if (log.isDebugEnabled()) {
            if(request == null) return;
            Map<String, Object> map = new LinkedHashMap<>();
            //sb.append("Ajax Call : " + "XMLHttpRequest".equals(request.getHeader(Globals.AJAX_HEADER))).append("\n");
            map.put("URI", request.getRequestURI());
            map.put("URL", request.getRequestURL());
            map.put("IP", request.getRemoteAddr());
            map.put("Referer URI", request.getHeader("referer"));
            map.put("Method", request.getMethod());
            map.put("User Agent", request.getHeader("User-Agent"));
            map.put("Session", request.getSession().getId());
            map.put("Locale", request.getLocale().getCountry());
            map.put("ContentType", request.getContentType());
            map.put("Parameters", params);

            log.info("{}{}{}",
                "\n//============================= Http Request ==============================",
                jsonEnterConvert(json.stringify(map)),
                "\n=========================================================================//"
            );
            map.clear();
        }
    }

    @SuppressWarnings("unchecked")
    private JSONObject getParams(HttpServletRequest request) {
        if(request == null) return new JSONObject();
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, maskingParam(replaceParam, request.getParameter(param)));
        }
        return jsonObject;
    }

    private String maskingParam(final String key, final String value){
        if(ObjectUtils.isEmpty(value))   return value;
        if("juminId".equals(key)) {
            if(value.length() == 14) {
                return value.replaceAll("([0-9]{6})-([1-4]{1})([0-9]{6})", "$1-$2******");
            }
            return value.replaceAll("([0-9]{6})([1-4]{1})([0-9]{6})", "$1$2******");
        }
        if("telNo".equals(key)) {
            if(value.contains("-")){
                return value.replaceAll("-[0-9]{3,4}-", "-****-");
            };
            return value.replaceAll("([0-9]{3})([0-9]{4})([0-9]{4})", "$1$2****");
        }
        if("email".equals(key)) {
            return value.replaceAll("[a-z,A-Z,0-9]+@", "******@");
        }
        if("name".equals(key)) {
            return value.replaceAll("(?<=.{1}).", "*");
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    private String resetJsonMasking(final JSONObject json){
        for (Object key : json.keySet()) {
            if(ObjectUtils.isEmpty(json.get(key))) {
                json.put(key, json.get(key));
            }else {
                json.put(key, maskingParam((String) key, String.valueOf(json.get(key))));
            }
        }
        return json.toJSONString();
    }

    @Getter
    private enum ApiSystemId {
        KAKAO("NIMS", "마약류통합관리", "/nims/"),
        KT_BC("MOIS", "온나라", "/mois/"),
        PPLUS("IROS", "공공데이타포탈", "/iros/"),
        BATCH("NONE", "미정의", "/미정의/"),
        ;

        private final String code;
        private final String desc;
        private final String uri;

        ApiSystemId(final String code, final String desc, final String uri) {
            this.code = code;
            this.desc = desc;
            this.uri = uri;
        }

        public static ApiSystemId compareByUri(final String uri){
            return Arrays.stream(ApiSystemId.values())
                .filter(ssc -> uri.contains(ssc.getUri()))
                .findFirst()
                .orElseGet(() -> ApiSystemId.valueOf("NONE"));
        }
    }

    /**
     * Json 데이터 보기 좋게 변환.
     * @param json Object	json
     * @return String
     */
    private String jsonEnterConvert(String json) {

        if( json == null || json.length() < 2 )
            return json;

        final int len = json.length();
        final StringBuilder sb = new StringBuilder();
        char c;
        String tab = "";
        boolean beginEnd = true;
        for( int i=0 ; i<len ; i++ ) {
            c = json.charAt(i);
            switch( c ) {
                case '{': case '[':{
                    sb.append( c );
                    if( beginEnd ){
                        tab += "\t";
                        sb.append("\n");
                        sb.append( tab );
                    }
                    break;
                }
                case '}': case ']':{
                    if( beginEnd ){
                        tab = tab.substring(0, tab.length()-1);
                        sb.append("\n");
                        sb.append( tab );
                    }
                    sb.append( c );
                    break;
                }
                case '"':{
                    if( json.charAt(i-1)!='\\' )
                        beginEnd = ! beginEnd;
                    sb.append( c );
                    break;
                }
                case ',':{
                    sb.append( c );
                    if( beginEnd ){
                        sb.append("\n");
                        sb.append( tab );
                    }
                    break;
                }
                default :{
                    sb.append( c );
                }
            } // switch end

        }
        if(!sb.isEmpty())
            sb.insert(0, '\n');
        return sb.toString();
    }
}
