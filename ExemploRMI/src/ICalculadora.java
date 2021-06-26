//Java possui uma implementação para RMI, na qual suas principais classes e interfaces estão no pacote java.rmi 
import java.rmi.Remote;
import java.rmi.RemoteException;

//O primeiro passo para implementar o RMI é definir uma interface remota, para isso basta estender a interface Remote do Java 
public interface ICalculadora extends Remote{
	//Cada método lançará uma RemoteException para os casos em que os objetos remotos caiam, problemas de rede, etc.
	public int soma(int a, int b) throws RemoteException;
	public int subtracao(int a, int b) throws RemoteException;
	public int multiplicacao(int a, int b) throws RemoteException;
	public int divisao(int a, int b) throws RemoteException;
}
