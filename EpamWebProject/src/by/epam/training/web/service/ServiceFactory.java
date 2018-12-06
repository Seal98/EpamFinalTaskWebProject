package by.epam.training.web.service;

public final class ServiceFactory {
	
	private final ClientService clientService = new ClientService();
	
	private ServiceFactory() {
	}
	
	private static class SingletonHandler{
		private static final ServiceFactory instance = new ServiceFactory();
	}
	
	public static ServiceFactory getInstance() {
		return SingletonHandler.instance;
	}

	public ClientService getClientService() {
		return clientService;
	}
	
}
