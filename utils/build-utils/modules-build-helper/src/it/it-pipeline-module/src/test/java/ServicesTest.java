import javax.inject.Inject;

import org.daisy.pipeline.datatypes.DatatypeService;
import org.daisy.pipeline.script.XProcScriptService;

import org.daisy.pipeline.junit.TestRunner;

import static org.daisy.pipeline.pax.exam.Options.felixDeclarativeServices;
import static org.daisy.pipeline.pax.exam.Options.logbackClassic;
import static org.daisy.pipeline.pax.exam.Options.logbackConfigFile;
import static org.daisy.pipeline.pax.exam.Options.mavenBundle;
import static org.daisy.pipeline.pax.exam.Options.mavenBundlesWithDependencies;
import static org.daisy.pipeline.pax.exam.Options.thisBundle;

import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.ops4j.pax.exam.Configuration;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.options;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;

@RunWith(TestRunner.class)
public class ServicesTest {
	
	@Inject
	public DatatypeService datatype;
	
	@Test
	public void testDatatype() {
		assertEquals("px:script-option-1", datatype.getId());
	}
	
	@Inject
	public XProcScriptService script;
	
	@Test
	public void testScript() {
		assertEquals("script", script.getId());
	}
	
	/* ------------- */
	/* For OSGi only */
	/* ------------- */
	
	@ProbeBuilder
	public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
		probe.setHeader("Service-Component", "OSGI-INF/resolver-mock.xml");
		return probe;
	}
	
	@Configuration
	public Option[] config() {
		return options(
			logbackConfigFile(),
			felixDeclarativeServices(),
			thisBundle(),
			junitBundles(),
			mavenBundlesWithDependencies(
				// logging
				logbackClassic(),
				mavenBundle("org.daisy.pipeline:framework-core:?"))
		);
	}
}
