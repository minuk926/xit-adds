package cokr.xit.adds.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lombok.val;

/**
 * <pre>
 * description : google zxing 라이브러리를 이용한 QR코드 생성 및 읽기
 *
 * author      : limju
 * date        : 2024-04-01
 * ======================================================================
 * 변경일         변경자        변경 내용
 * ----------------------------------------------------------------------
 * 2024-04-01    limju       최초 생성
 *
 * </pre>
 */
public class XingUtils {

	private static final String QR_CODE_PATH = "d:/temp/qr/";
	private static final Map<EncodeHintType, ErrorCorrectionLevel> hashMap =
		new HashMap<>();

	static {
		hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	}

	public static String readQrcodeFromPath(final String path) throws IOException, NotFoundException {
		val binaryBitmap =
			new BinaryBitmap(
				new HybridBinarizer(
					new BufferedImageLuminanceSource(
						ImageIO.read(
							new FileInputStream(path)
						)
					)
				)
			);

		final Result result = new MultiFormatReader().decode(binaryBitmap);

		return result.getText();
	}

	public static String readQrcodeFromFile(final File file) throws IOException, NotFoundException {
		val binaryBitmap =
			new BinaryBitmap(
				new HybridBinarizer(
					new BufferedImageLuminanceSource(
						ImageIO.read(
							file
						)
					)
				)
			);

		final Result result =
			new MultiFormatReader().decode(binaryBitmap);

		return result.getText();
	}

	/**
	 *     //BarcodeFormat.QR_CODE,
	 *     //BarcodeFormat.CODE_128,
	 *     //BarcodeFormat.EAN_13,
	 */
	public static void createQR(
		final BarcodeFormat format,
		final String data,
		final String path,
		final String charset,
		final Integer height,
		final Integer width
	) throws IOException, WriterException {
		String cs = charset;
		if (StringUtils.isEmpty(charset)) {
			cs = StandardCharsets.UTF_8.name();
		}
		val matrix =
			new MultiFormatWriter().encode(
				new String(data.getBytes(cs), cs),
				format,
				width,
				height
			);

		val filePath = Paths.get(QR_CODE_PATH, path);

		MatrixToImageWriter.writeToPath(
			matrix,
			path.substring(path.lastIndexOf('.') + 1),
			filePath
		);
	}

	public static File convert(final MultipartFile mf) throws IOException {
		val name = mf.getOriginalFilename();

		assert name != null;
		val ext = name.substring(name.lastIndexOf("."));
		val filename = name.substring(0, name.lastIndexOf("."));
		val path = Files.createTempFile(filename, ext);

		mf.transferTo(path);
		return path.toFile();
	}

	public static void main(String[] args) throws IOException, WriterException, NotFoundException {
		var data = "qrcode_1234";

		// The path where the image will get saved
		var path = "/qr_code.png";

		// Encoding charset
		val charset = "UTF-8";

		final Map<EncodeHintType, ErrorCorrectionLevel> map = new HashMap<>();

		map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		// BarcodeFormat.QR_CODE,
		// BarcodeFormat.CODE_128,
		// BarcodeFormat.EAN_13,
		XingUtils.createQR(BarcodeFormat.QR_CODE, data, path, charset, 200, 200);
		System.out.println("QR Code Generated!!! ");

		// Path where the QR code is saved
		val path2 = QR_CODE_PATH + path;
		System.out.println("Data read from QR Code: " + XingUtils.readQrcodeFromPath(path2));
	}
}




