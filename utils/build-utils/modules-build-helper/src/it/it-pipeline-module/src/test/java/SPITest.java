import javax.inject.Inject;

import org.daisy.pipeline.datatypes.DatatypeService;
import org.daisy.pipeline.script.XProcScriptService;

import org.daisy.pipeline.junit.OSGiLessRunner;

import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(OSGiLessRunner.class)
public class SPITest {
	
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
}
