package cokr.xit.adds.core.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * description : Method 단위 로깅 annotation(AOP 사용 예정)
 *               Around("@annotation(cokr.xit.adds.core.spring.annotation.TraceLogging)")
 * author      : julim
 * date        : 2024-04-08
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-08    julim       최초 생성
 *
 * </pre>
 */
//@Target({ElementType.TYPE, ElementType.METHOD})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TraceLogging {
}
