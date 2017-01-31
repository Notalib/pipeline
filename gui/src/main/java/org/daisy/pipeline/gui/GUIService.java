package org.daisy.pipeline.gui;
import org.daisy.pipeline.datatypes.DatatypeRegistry;
import org.daisy.pipeline.event.EventBusProvider;
import org.daisy.pipeline.job.JobManagerFactory;
import org.daisy.pipeline.script.ScriptRegistry;
import org.daisy.pipeline.webserviceutils.storage.WebserviceStorage;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(
        name = "gui-service",
        immediate = true
)
public class GUIService 
{
        private static final Logger logger = LoggerFactory.getLogger(GUIService.class);
        private BundleContext ctxt;

        @Activate
        public void init(BundleContext ctxt) {
                this.ctxt=ctxt;
                ServiceRegistry.getInstance().setGUIService(this);
                //Otherwise launch will block
                new Thread(){
                        public void run(){
                                javafx.application.Application.launch(PipelineApplication.class);
                                
                        }
                }.start();
                logger.debug("Main Module is loaded!");
        }

        public void stopGUI(){
                try {
                        ((Framework) this.ctxt.getBundle(0)).stop();
                } catch (BundleException e) {
                        logger.error("Error closing the framework ",e);
                        //exit the hard way
                        System.exit(-1);
                }
        }



        @Reference(
                name = "script-registry",
                unbind = "-",
                service = ScriptRegistry.class,
                cardinality = ReferenceCardinality.MANDATORY,
                policy = ReferencePolicy.STATIC
        )
        public void setScriptRegistry(ScriptRegistry scriptRegistry) {
                ServiceRegistry.getInstance().setScriptRegistry(scriptRegistry);
        }
        @Reference(
                name = "job-manager-factory",
                unbind = "-",
                service = JobManagerFactory.class,
                cardinality = ReferenceCardinality.MANDATORY,
                policy = ReferencePolicy.STATIC
        )
        public void setJobManagerFactory(JobManagerFactory jobManagerFactory) {
                ServiceRegistry.getInstance().setJobManagerFactory(jobManagerFactory);
        }
        @Reference(
                name = "event-bus-provider",
                unbind = "-",
                service = EventBusProvider.class,
                cardinality = ReferenceCardinality.MANDATORY,
                policy = ReferencePolicy.STATIC
        )
        public void setEventBusProvider(EventBusProvider eventBusProvider) {
                ServiceRegistry.getInstance().setEventBusProvider(eventBusProvider);
        }
        @Reference(
                name = "webservice-storage",
                unbind = "-",
                service = WebserviceStorage.class,
                cardinality = ReferenceCardinality.MANDATORY,
                policy = ReferencePolicy.STATIC
        )
        public void setWebserviceStorage(WebserviceStorage webserviceStorage) {
                ServiceRegistry.getInstance().setWebserviceStorage(webserviceStorage);
        }
        @Reference(
                name = "datatype-registry",
                unbind = "-",
                service = WebserviceStorage.class,
                cardinality = ReferenceCardinality.MANDATORY,
                policy = ReferencePolicy.STATIC
        )
        public void setDatatypeRegistry(DatatypeRegistry datatypeRegistry) {
                ServiceRegistry.getInstance().setDatatypeRegistry(datatypeRegistry);
        }

}
