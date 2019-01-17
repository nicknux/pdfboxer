package com.nickdsantos.pdfboxer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BoxerTest extends TestCase {
	public BoxerTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(BoxerTest.class);
	}

	public void testAddingBoxes() {
		Boxer boxer = null;
		float bleed = 10.0f;
		String currDir = System.getProperty("user.dir");
		String sourcePdf = currDir + "/src/test/pdfs/UNTRIMMED_PDF.pdf";
		String destPdf = currDir + "/src/test/pdfs/TRIMMED_PDF.pdf";

		try {
			Files.deleteIfExists(Paths.get(destPdf));
			boxer = new Boxer(sourcePdf, destPdf);
		} catch (IOException e) {
			fail(e.getMessage());
		}

		Boxer.DocumentSize docSize = boxer.getPageDimensions();
		assertNotNull(docSize);
		assertTrue(docSize.width > 0);
		assertTrue(docSize.height > 0);

		boxer.setTrimBox(bleed, bleed, docSize.width - 2 * bleed,
				docSize.height - 2 * bleed);
		boxer.setArtBox(bleed, bleed, docSize.width - 2 * bleed, docSize.height
				- 2 * bleed);
		boxer.setBleedBox(bleed, bleed, docSize.width - 2 * bleed,
				docSize.height - 2 * bleed);

		try {
			boxer.save();
			assertTrue(Files.exists(Paths.get(destPdf)));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
