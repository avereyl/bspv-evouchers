/**
 * 
 */
package org.bspv.evoucher.tech.impl;

import java.awt.image.BufferedImage;
import java.util.Locale;

import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.tech.BarcodeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Service
public class BarcodeGenerationServiceDefault implements BarcodeGenerationService {

	/**
	 * 
	 */
	@Autowired
	@Qualifier("reportMessageSource")
	private MessageSource messageSource;

	/**
	 * 
	 */
	@Value("${barcode.size}")
	private int defaultBarcodeSize;

	/**
	 * 
	 */
	@Value("${mail.answer}")
	private String mailAnswerAddress;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bspv.evoucher.tech.BarcodeGenerationService#generateBarcode(org.bspv.
	 * evoucher.core.model.EVoucher)
	 */
	@Override
	public BufferedImage generateBarcode(EVoucher eVoucher) {
		int size = this.defaultBarcodeSize;
		BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		String data = computeBarcodeData(eVoucher);
		try {
			BitMatrix bitMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size);
			MatrixToImageConfig conf = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
			bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, conf);
		} catch (WriterException e) {
			log.error("Error when generating the barcode.", e);
		}
		return bufferedImage;
	}

	private String computeBarcodeData(EVoucher eVoucher) {
		return messageSource.getMessage("voucher.barcode.template",
				new Object[] { this.mailAnswerAddress, eVoucher.getId() }, Locale.FRANCE);
	}

}
