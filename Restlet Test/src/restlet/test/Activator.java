package restlet.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
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
		
		Component component = new TestComponent();
		
		Server s = new Server(Protocol.HTTP, 8888, component);
		s.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	public static class TestComponent extends Component {
		public TestComponent() {
			getDefaultHost().attach("/app", new TestApplication());
		}
	}
	
	
	public static class TestApplication extends Application {
		
		@Override
		public Restlet createInboundRoot() {
			Router router = new Router(getContext());
			router.attach("/test/{id}", TestResource.class);
			return router;
		}
		
	}
	

	public static class TestResource extends ServerResource {
		
		private Integer id;
		
		@Override
		protected void doInit() throws ResourceException {
			try {
				id = Integer.valueOf((String)getRequestAttributes().get("id"));
			} catch (Throwable t) {
				throw new ResourceException(400, t);
			}
		}
		
		@Get("txt")
		public String get(Representation rep) {
			System.out.println(id);
			return "hello world!";
		}
	}

}
