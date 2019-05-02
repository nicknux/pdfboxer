# pdfboxer
Add PDF Boxes (Trim, Art, Bleed, and Crop) to existing PDFs

## Build
```
$ mvn clean package
```

## Show Usage
```
$ java -jar bin/pdfboxer-0.0.1.jar
usage: boxer
 -trimBox <arg>      Sets the TrimBox. Must be passed as x,y,width,height
 -artBox <arg>       Sets the ArtBox. Must be passed as x,y,width,height
 -bleedBox <arg>     Sets the BleedBox. Must be passed as x,y,width,height
 -cropBox <arg>      Sets the CropBox. Must be passed as x,y,width,height
 -mediaBox <arg>     Sets the MediaBox. Must be passed as x,y,width,height
 -sourceFile <arg>   The source pdf
 -destFile <arg>     The destination pdf
```

## Example
```
$ java -jar bin/pdfboxer-0.0.1.jar \
-trimBox 7.200000,7.200000,504.000031,720.000000 \
-artBox 7.200000,7.200000,504.000031,720.000000 \
-sourceFile src/test/pdfs/UNTRIMMED_PDF.pdf \
-destFile src/test/pdfs/TRIMMED_PDF.pdf
```
