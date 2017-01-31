package org.daisy.pipeline.tts.osx;

import java.util.Map;

import org.daisy.pipeline.tts.AbstractTTSService;
import org.daisy.pipeline.tts.TTSEngine;
import org.daisy.pipeline.tts.TTSService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.ComponentContext;

@Component(
	name = "osx-tts-service",
	service = { TTSService.class }
)
public class OSXSpeechService extends AbstractTTSService {
	
	@Activate
	@Override
	protected void loadSSMLadapter(ComponentContext context) {
		super.loadSSMLadapter(context);
	}

	@Override
	public TTSEngine newEngine(Map<String, String> params) throws Throwable {
		// settings
		String prop = "osxspeech.path";
		String sayPath = System.getProperty(prop);
		if (sayPath == null) {
			sayPath = "/usr/bin/say";
		}
		String priority = params.get("osxspeech.priority");
		int intPriority = 2;
		if (priority != null) {
			try {
				intPriority = Integer.valueOf(priority);
			} catch (NumberFormatException e) {

			}
		}

		//allocate the engine
		return new OSXSpeechEngine(this, sayPath, intPriority);
	}

	@Override
	public String getName() {
		return "osx-speech";
	}

	@Override
	public String getVersion() {
		return "cli";
	}
}
