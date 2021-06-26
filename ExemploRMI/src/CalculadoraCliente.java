import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//classe cliente que utilizará um objeto remoto, invocando seus métodos como se fossem locais, abstraindo toda a comunicação remota
public class CalculadoraCliente {
	
	public static void main(String[] args) {
		Registry reg = null;
		ICalculadora calc;		
		try {
			//acessa o registro de nomes que está rodando na porta 1099
			reg = LocateRegistry.getRegistry(1099);
			//acessa a referência do objeto remoto de id "calculadora"
			calc = (ICalculadora) reg.lookup("calculadora");
			//chama os métodos do objeto remoto como se fosse uma chamada local
			System.out.println(calc.soma(10,2));
			System.out.println(calc.subtracao(10,2));
			System.out.println(calc.multiplicacao(10,2));
			System.out.println(calc.divisao(10,2));
		} catch (RemoteException | NotBoundException e) {
				System.out.println(e);
				System.exit(0);
		}
	}		

}
