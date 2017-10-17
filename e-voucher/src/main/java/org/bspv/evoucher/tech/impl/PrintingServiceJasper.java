package org.bspv.evoucher.tech.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.tech.PrintingService;
import org.bspv.evoucher.tech.helper.PrintingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.type.PdfaConformanceEnum;

/**
 * 
 *
 */
@Slf4j
@Service("printingServiceJasper")
public class PrintingServiceJasper implements PrintingService {

	/**
	 * 
	 */
//	@Autowired
//	private BarcodeGenerationService barcodeGenerationService;
	/**
	 * 
	 */
	@Value("${voucher.responsible}")
	private String voucherResponsible;
	
	/**
	 * 
	 */
	@Autowired
	@Qualifier("reportMessageSource")
    private MessageSource messageSource;

	/**
	 * 
	 */
	@Autowired
	private ResourceLoader resourceLoader;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bspv.evoucher.tech.PrintingService#printOriginalEVoucher(org.bspv.
	 * evoucher.core.model.EVoucher)
	 */
	@Override
	public ByteArrayOutputStream printOriginalEVoucher(EVoucher evoucher) throws IOException {
		return printEVoucher(evoucher);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bspv.evoucher.tech.PrintingService#printDuplicataEVoucher(org.bspv.
	 * evoucher.core.model.EVoucher)
	 */
	@Override
	public ByteArrayOutputStream printDuplicataEVoucher(EVoucher evoucher) throws IOException {
		return printEVoucher(evoucher);
	}

	/**
	 * 
	 * @param eVoucher
	 * @return
	 * @throws IOException
	 */
	private ByteArrayOutputStream printEVoucher(EVoucher eVoucher) throws IOException {
		try (
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream employeeReportStream = resourceLoader
						.getResource("classpath:templates/voucher/eVoucherA4.jrxml").getInputStream();
			) {
 			// formatted amount
 			StringBuilder amount = new StringBuilder();
 			amount.append(StringUtils.capitalize(
 					PrintingHelper.formatAmountInEurosAndLitteralFrench(eVoucher.getAmount().floatValue())));
 			amount.append(" (");
 			amount.append(PrintingHelper.formatAmountInEurosAndFrench(eVoucher.getAmount().floatValue()));
 			amount.append(")");
 			String requestDate = eVoucher.getRequestDate() == null ? "" : PrintingHelper.formatDateInLitteralFrench(eVoucher.getRequestDate());
			String eVoucherReference = eVoucher.getId().toString();
			eVoucherReference = eVoucherReference.substring(eVoucherReference.length()-12);
 			
			JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
			Locale locale = LocaleContextHolder.getLocale();
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(JRParameter.REPORT_LOCALE, locale);
            parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, new MessageSourceResourceBundle(messageSource, locale));
            
            //make a bean from these following parameters
            parameters.put("voucherDate", messageSource.getMessage("voucher.date", new Object[]{PrintingHelper.formatDateInFrench(eVoucher.getCreatedDate())}, locale));
        	parameters.put("voucherReference", messageSource.getMessage("voucher.reference", new Object[]{eVoucherReference}, locale));
        	parameters.put("voucherDonorName", eVoucher.getName());
        	parameters.put("voucherAssociationName", messageSource.getMessage("voucher.bspv", new Object[]{}, locale));
        	parameters.put("voucherAssociationAddress", messageSource.getMessage("voucher.bspv.address", new Object[]{}, locale));
        	parameters.put("voucherDonationAmount", amount.toString());
        	parameters.put("voucherDonationDate", messageSource.getMessage("voucher.text.date", new Object[]{requestDate}, locale));
        	parameters.put("voucherResponsible", voucherResponsible);

            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
					
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			
			SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
			reportConfig.setSizePageToContent(true);
			reportConfig.setForceLineBreakPolicy(false);
//			PDF metadata
			SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
			exportConfig.setMetadataAuthor(messageSource.getMessage("voucher.metadata.author", new Object[]{}, locale));
			exportConfig.setMetadataTitle(messageSource.getMessage("voucher.metadata.title", new Object[]{String.valueOf(eVoucher.getCreatedDate().getYear())}, locale));
			exportConfig.setMetadataSubject(messageSource.getMessage("voucher.metadata.subject", new Object[]{eVoucher.getId()}, locale));
			exportConfig.setMetadataCreator(messageSource.getMessage("voucher.metadata.creator", new Object[]{}, locale));
			
			exportConfig.setMetadataKeywords("");
			
//			PDF/A conformance
			exportConfig.setPdfaConformance(PdfaConformanceEnum.PDFA_1A);
			String iccProfilePath = resourceLoader.getResource("classpath:assets/icm/sRGB Color Space Profile.icm").getURI().getPath();
			exportConfig.setIccProfilePath(iccProfilePath);

			exporter.setConfiguration(reportConfig);
			exporter.setConfiguration(exportConfig);

			exporter.exportReport();
			
			return baos;
		} catch (JRException e) {
			log.error("Oups !!!!!!!!!!!!", e);
			return null;
		}
	}

}
