package org.daisy.pipeline.junit;

import java.io.File;
import java.util.Properties;

import org.daisy.pipeline.pax.exam.Options;
import static org.daisy.pipeline.pax.exam.Options.domTraversalPackage;
import static org.daisy.pipeline.pax.exam.Options.felixDeclarativeServices;
import static org.daisy.pipeline.pax.exam.Options.logbackClassic;
import org.daisy.pipeline.pax.exam.Options.MavenBundleOption;
import static org.daisy.pipeline.pax.exam.Options.mavenBundle;
import static org.daisy.pipeline.pax.exam.Options.mavenBundles;
import static org.daisy.pipeline.pax.exam.Options.mavenBundlesWithDependencies;
import static org.daisy.pipeline.pax.exam.Options.thisBundle;

import org.junit.runner.RunWith;

import org.ops4j.pax.exam.Configuration;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.systemPackage;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.util.PathUtils;

@RunWith(TestRunner.class)
public abstract class AbstractTest {
	
	protected Properties systemProperties() {
		return null;
	}
	
	protected Properties calabashConfiguration() {
		File baseDir = new File(PathUtils.getBaseDir());
		Properties p = new Properties();
		p.setProperty("com.xmlcalabash.config.user", "");
		File file = new File(baseDir, "/src/test/resources/config-calabash.xml");
		if (file.exists())
			p.setProperty("org.daisy.pipeline.xproc.configuration", file.getAbsolutePath());
		return p;
	}
	
	protected Properties logbackConfiguration() {
		File baseDir = new File(PathUtils.getBaseDir());
		File file = new File(baseDir, "/src/test/resources/logback.xml");
		if (!file.exists())
			return null;
		Properties p = new Properties();
		p.setProperty("logback.configurationFile", file.toURI().toString());
		return p;
	}
	
	protected Properties allSystemProperties() {
		return mergeProperties(
			systemProperties(),
			logbackConfiguration());
	}
	
	Properties mergeProperties(Properties... properties) {
		Properties merged = new Properties();
		for (Properties props : properties)
			if (props != null)
				for (String key : props.stringPropertyNames())
					merged.setProperty(key, props.getProperty(key));
		return merged;
	}
	
	@OSGiLessConfiguration
	public void osgiLessConfiguration() {
		Properties props = allSystemProperties();
		for (String key : props.stringPropertyNames())
			System.setProperty(key, props.getProperty(key));
	}
	
	/* ------------- */
	/* For OSGi only */
	/* ------------- */
	
	protected String[] testDependencies() {
		return null;
	}
	
	protected String pipelineModule(String module) {
		return "org.daisy.pipeline.modules:" + module + ":?";
	}
	
	protected String brailleModule(String module) {
		return "org.daisy.pipeline.modules.braille:" + module + ":?";
	}
	
	@Configuration
	public Option[] config() {
		return _.config(
			Options.systemProperties(allSystemProperties()),
			mavenBundles(testDependencies()));
	}
	
	// wrapped in class to avoid ClassNotFoundException
	protected static abstract class _ {
		public static Option[] config(Option systemProperties, MavenBundleOption testDependencies) {
			return options(
				systemProperties,
				domTraversalPackage(),
				systemPackage("javax.xml.stream;version=\"1.0.1\""),
				felixDeclarativeServices(),
				thisBundle(),
				junitBundles(),
				mavenBundle("org.daisy.pipeline.build:modules-test-helper:?"),
				mavenBundlesWithDependencies(
					testDependencies,
					// logging
					logbackClassic())
			);
		}
	}
}
