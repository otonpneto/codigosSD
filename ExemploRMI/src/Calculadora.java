//Java possui uma implementa��o para RMI, na qual suas principais classes e interfaces est�o no pacote java.rmi
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

//O segundo passo � construir e implementar o servidor, para isso basta implementar a interface remota criada
public class Calculadora  implements ICalculadora {

	private static final long serialVersionUID = 1L;
	
	private static int chamadas_soma = 0;
	private static int chamadas_subtracao = 0;
	private static int chamadas_multiplicacao = 0;
	private static int chamadas_divisao = 0;

	//Ao implementar a interface, faz-se necess�rio implementar todos os seus m�todos
	public int soma(int a, int b) throws RemoteException {
		System.out.println("M�todo soma chamado " + chamadas_soma++);
		return a + b;
	}
	
	public int subtracao(int a, int b) throws RemoteException {
		System.out.println("M�todo subtra��o chamado " + chamadas_subtracao++);
		return a - b;
	}

	public int multiplicacao(int a, int b) throws RemoteException {
		System.out.println("M�todo multiplica��o chamado " + chamadas_multiplicacao++);
		return a * b;
	}

	public int divisao(int a, int b) throws RemoteException {
		System.out.println("M�todo divis�o chamado " + chamadas_divisao++);
		return a / b;
	}
	
	//O m�todo main do servidor � respons�vel por colocar um objeto servidor no ar
	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException  {
		//cria um novo objeto
		Calculadora calculadora = new Calculadora();		
		//java.rmi.registry � respons�vel por fornecer os insumos necess�rios para termos um Servi�o de Nomes ao implementar o RMI em java
		//a ideia � ter uma independ�ncia espacial entre cliente e servidor
		//um objeto servidor registra sua refer�ncia em um servi�o de nomes
		//um objeto cliente encontra a refer�ncia do objeto remoto no servi�o de nomes para que possa utilizar seus servi�os
		
		Registry reg = null;
		//cria��o do skeleton ou stub do servidor
		//a classe UnicastRemoteObject implementa um objeto servidor
		//o m�todo est�tico exportObject() � repons�vel por subir o stub do servidor (skeleton) associado ao objeto na porta 1100
		//com isso, o stub poder� ser registrado no servi�o de nomes, para que os clientes possam encontr�-lo e utiliz�-lo
		ICalculadora stub = (ICalculadora) UnicastRemoteObject.
				exportObject(calculadora, 1100);
		try {
			//cria��o do servi�o de nomes na porta 1099
			System.out.println("Creating registry...");
			reg = LocateRegistry.createRegistry(1099);
		} catch (Exception e) {
			try {
				//caso o servi�o de nomes j� tenha sido criado
				reg = LocateRegistry.getRegistry(1099);
			} catch (Exception e1) {
				System.exit(0);
			}
		}
		//o servi�o de nomes associa o id "calculadora" ao stub do servidor (skeleton) para ser descoberto e utilizado pelos clientes
		reg.rebind("calculadora", stub);
	}

}
