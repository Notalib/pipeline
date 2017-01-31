import java.util.Iterator;
import java.util.ServiceLoader;

import org.daisy.pipeline.datatypes.DatatypeService;
import org.daisy.pipeline.script.XProcScriptService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SPITest {
	
	@Test
	public void testDatatype() {
		Iterator<DatatypeService> datatypes = ServiceLoader.load(DatatypeService.class).iterator();
		assertTrue(datatypes.hasNext());
		DatatypeService d = datatypes.next();
		assertEquals("px:script-option-1", d.getId());
		assertFalse(datatypes.hasNext());
	}
	
	@Test
	public void testScript() {
		Iterator<XProcScriptService> scripts = ServiceLoader.load(XProcScriptService.class).iterator();
		assertTrue(scripts.hasNext());
		XProcScriptService s = scripts.next();
		assertEquals("script", s.getId());
		assertFalse(scripts.hasNext());
	}
}
