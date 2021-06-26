//Java possui uma implementação para RMI, na qual suas principais classes e interfaces estão no pacote java.rmi
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

//O segundo passo é construir e implementar o servidor, para isso basta implementar a interface remota criada
public class Calculadora  implements ICalculadora {

	private static final long serialVersionUID = 1L;
	
	private static int chamadas_soma = 0;
	private static int chamadas_subtracao = 0;
	private static int chamadas_multiplicacao = 0;
	private static int chamadas_divisao = 0;

	//Ao implementar a interface, faz-se necessário implementar todos os seus métodos
	public int soma(int a, int b) throws RemoteException {
		System.out.println("Método soma chamado " + chamadas_soma++);
		return a + b;
	}
	
	public int subtracao(int a, int b) throws RemoteException {
		System.out.println("Método subtração chamado " + chamadas_subtracao++);
		return a - b;
	}

	public int multiplicacao(int a, int b) throws RemoteException {
		System.out.println("Método multiplicação chamado " + chamadas_multiplicacao++);
		return a * b;
	}

	public int divisao(int a, int b) throws RemoteException {
		System.out.println("Método divisão chamado " + chamadas_divisao++);
		return a / b;
	}
	
	//O método main do servidor é responsável por colocar um objeto servidor no ar
	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException  {
		//cria um novo objeto
		Calculadora calculadora = new Calculadora();		
		//java.rmi.registry é responsável por fornecer os insumos necessários para termos um Serviço de Nomes ao implementar o RMI em java
		//a ideia é ter uma independência espacial entre cliente e servidor
		//um objeto servidor registra sua referência em um serviço de nomes
		//um objeto cliente encontra a referência do objeto remoto no serviço de nomes para que possa utilizar seus serviços
		
		Registry reg = null;
		//criação do skeleton ou stub do servidor
		//a classe UnicastRemoteObject implementa um objeto servidor
		//o método estático exportObject() é reponsável por subir o stub do servidor (skeleton) associado ao objeto na porta 1100
		//com isso, o stub poderá ser registrado no serviço de nomes, para que os clientes possam encontrá-lo e utilizá-lo
		ICalculadora stub = (ICalculadora) UnicastRemoteObject.
				exportObject(calculadora, 1100);
		try {
			//criação do serviço de nomes na porta 1099
			System.out.println("Creating registry...");
			reg = LocateRegistry.createRegistry(1099);
		} catch (Exception e) {
			try {
				//caso o serviço de nomes já tenha sido criado
				reg = LocateRegistry.getRegistry(1099);
			} catch (Exception e1) {
				System.exit(0);
			}
		}
		//o serviço de nomes associa o id "calculadora" ao stub do servidor (skeleton) para ser descoberto e utilizado pelos clientes
		reg.rebind("calculadora", stub);
	}

}
