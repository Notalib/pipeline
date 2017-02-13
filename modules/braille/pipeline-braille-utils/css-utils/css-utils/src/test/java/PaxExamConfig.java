import org.daisy.pipeline.junit.AbstractXSpecAndXProcSpecTest;

import org.ops4j.pax.exam.Configuration;
import static org.ops4j.pax.exam.CoreOptions.bundle;
import static org.ops4j.pax.exam.CoreOptions.composite;
import static org.ops4j.pax.exam.CoreOptions.options;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.util.PathUtils;

public class PaxExamConfig extends AbstractXSpecAndXProcSpecTest {
	
	protected static boolean onWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
	
	@Override
	protected String[] testDependencies() {
		return new String[] {
			brailleModule("common-utils"),
			brailleModule("css-core"),
			"org.daisy.libs:io.bit3.jsass:?",
			"com.google.guava:guava:?",
			"org.daisy.libs:com.xmlcalabash:?",
			"org.daisy.libs:saxon-he:?",
			"org.daisy.libs:jstyleparser:?",
			"org.daisy.pipeline:calabash-adapter:?"
		};
	}
	
	@Override @Configuration
	public Option[] config() {
		return options(
			composite(super.config()),
			bundle("reference:file:" + PathUtils.getBaseDir() + "/target/test-classes/css-module/"));
	}
}

