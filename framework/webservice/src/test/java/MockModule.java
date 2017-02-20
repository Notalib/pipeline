import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.daisy.pipeline.modules.JarModuleBuilder;
import org.daisy.pipeline.modules.Module;
import org.daisy.pipeline.modules.ModuleRef;
import org.daisy.pipeline.xmlcatalog.XmlCatalog;
import org.daisy.pipeline.xmlcatalog.XmlCatalogParser;

public class MockModule implements ModuleRef {
    
    private Module instance;
    private XmlCatalogParser catalogParser;
    
    public Module get() {
        if (instance == null) {
            File jarFile;
            XmlCatalog catalog; {
                try {
                    URI jarFileURI = MockModule.class.getProtectionDomain().getCodeSource().getLocation().toURI();
                    jarFile = new File(jarFileURI);
                    catalog = catalogParser.parse(
                        jarFile.isDirectory() ?
                            new File(jarFile, "/META-INF/catalog.xml").toURI() :
                            URI.create("jar:" + jarFileURI.toASCIIString() + "!/META-INF/catalog.xml")
                    );
                } catch(URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
            instance = new JarModuleBuilder()
                .withName("mock-module")
                .withVersion("1.0.0")
                .withTitle("mock-module")
                .withJarFile(jarFile)
                .withCatalog(catalog)
                .build();
        }
        return instance;
    }
    
    public void setParser(XmlCatalogParser parser) {
        catalogParser = parser;
    }
}
