package cokr.xit.adds.biz.nims.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.NotFoundException;

import cokr.xit.adds.core.util.XingUtils;

/**
 * <pre>
 * description : 
 *
 * packageName : cokr.xit.adds.biz.web
 * fileName    : QrController
 * author      : limju
 * date        : 2024-04-01
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-01    limju       최초 생성
 *
 * </pre>
 */
@RestController
public class QrController {

	@GetMapping("/biz/nims/v1/qrcodeForm")
	public ModelAndView qrcodeForm() {
		return new ModelAndView("/zxing/index.html");
	}

	@PostMapping("/biz/nims/v1/getQrcode")
	public List<Map<String, Object>> uploadQrCode(
		@RequestParam("uploadFiles")
		final List<MultipartFile> multipartFiles
	) {
		return multipartFiles.stream().map(mf -> {
			System.out.println(mf.getOriginalFilename());
			System.out.println(mf.getSize());
			Map<String, Object> map = new HashMap<>();
			try {
				map.put("qrcode", XingUtils.readQrcodeFromFile(XingUtils.convert(mf)));
				map.put("name", mf.getOriginalFilename());
			} catch (IOException | NotFoundException e) {
				throw new RuntimeException(e);
			}
			return map;
		}).toList();
	}
}
