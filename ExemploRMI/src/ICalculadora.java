//Java possui uma implementa��o para RMI, na qual suas principais classes e interfaces est�o no pacote java.rmi 
import java.rmi.Remote;
import java.rmi.RemoteException;

//O primeiro passo para implementar o RMI � definir uma interface remota, para isso basta estender a interface Remote do Java 
public interface ICalculadora extends Remote{
	//Cada m�todo lan�ar� uma RemoteException para os casos em que os objetos remotos caiam, problemas de rede, etc.
	public int soma(int a, int b) throws RemoteException;
	public int subtracao(int a, int b) throws RemoteException;
	public int multiplicacao(int a, int b) throws RemoteException;
	public int divisao(int a, int b) throws RemoteException;
}
