package cokr.xit.adds.inf.iros.model;

import java.util.List;

/**
 * <pre>
 * description :
 *
 * packageName : cokr.xit.adds.inf.iros.model
 * fileName    : IrosResponse
 * author      : limju
 * date        : 2024-03-20
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-03-20    limju       최초 생성
 *
 * </pre>
 */
public record IrosResponse<T>(
    Header header,
    Body<T> body
) {
    public static record Header(
        String resultCode,
        String resultMsg
    ) {
    }

    public static record Body<T>(
        String pageNo,
        String numOfRows,
        String totalCount,
        List<T> items
    ) {
    }
}
