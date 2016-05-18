package restlet.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Status;
import org.restlet.routing.Router;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("Restlet Test Start");
		
		Component component = new Component();
		
		Server s = new Server(Protocol.HTTP, 8888, TestResource.class);
		s.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	public static class TestApplication extends Application {
		
		@Override
		public Restlet createInboundRoot() {
			Router router = new Router(getContext());

			router.attach("/app/test", TestResource.class);

			return router;
		}
		
	}
	

	public static class TestResource extends ServerResource {
		
		@Get("txt")
		public String doGet() {
			return "hello world!";
		}
	}

}
