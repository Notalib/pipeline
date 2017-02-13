import org.daisy.pipeline.junit.AbstractXSpecAndXProcSpecTest;

import static org.daisy.pipeline.pax.exam.Options.thisPlatform;

public class XProcSpecTest extends AbstractXSpecAndXProcSpecTest {
	
	@Override
	public String[] testDependencies() {
		return new String[]{
			brailleModule("xml-to-pef"),
			brailleModule("common-utils"),
			brailleModule("pef-utils"),
			brailleModule("liblouis-utils"),
			brailleModule("dotify-utils"),
			brailleModule("liblouis-tables"),
			brailleModule("liblouis-formatter"),
			brailleModule("dotify-formatter"),
			pipelineModule("file-utils"),
			pipelineModule("fileset-utils"),
			pipelineModule("common-utils"),
			pipelineModule("zip-utils"),
			pipelineModule("mediatype-utils"),
			"org.daisy.pipeline.modules.braille:liblouis-native:jar:" + thisPlatform() + ":?"
		};
	}
}
