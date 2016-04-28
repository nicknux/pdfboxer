package com.nickdsantos.pdfboxer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class Boxer {
	PDDocument doc;
	File destFile;
	Map<String, PDRectangle> changeMap;

	public Boxer(String sourceFile, String destinationFile) throws IOException {
		doc = PDDocument.load(new File(sourceFile));
		destFile = new File(destinationFile);
		changeMap = new HashMap<String, PDRectangle>();
	}

	static public class DocumentSize {
		float width;
		float height;
	}

	public DocumentSize getPageDimensions() {
		PDRectangle rect = doc.getPage(0).getMediaBox();
		return new DocumentSize() {
			{
				width = rect.getWidth();
				height = rect.getHeight();
			}
		};
	}

	public void setTrimBox(float x, float y, float w, float h) {
		changeMap.put("TrimBox", new PDRectangle(x, y, w, h));
	}

	public void setArtBox(float x, float y, float w, float h) {
		changeMap.put("ArtBox", new PDRectangle(x, y, w, h));
	}

	public void setCropBox(float x, float y, float w, float h) {
		changeMap.put("CropBox", new PDRectangle(x, y, w, h));
	}

	public void setBleedBox(float x, float y, float w, float h) {
		changeMap.put("BleedBox", new PDRectangle(x, y, w, h));
	}

	public void save() throws IOException {
		PDPageTree pages = doc.getPages();
		for (int i = 0; i < pages.getCount(); i++) {
			PDPage p = pages.get(i);
			for (String key : changeMap.keySet()) {
				PDRectangle rect = changeMap.get(key);
				System.out.printf("Page %d: Setting %s [%f, %f, %f, %f]\n", i,
						key,
						rect.getLowerLeftX(), rect.getLowerLeftY(),
						rect.getWidth(), rect.getHeight());

				switch (key) {
				case "TrimBox":
					p.setTrimBox(rect);
					break;
				case "ArtBox":
					p.setArtBox(rect);
					break;
				case "CropBox":
					p.setCropBox(rect);
					break;
				case "BleedBox":
					p.setBleedBox(rect);
					break;
				}
			}
		}
		
		doc.save(destFile);
		doc.close();
	}
}
