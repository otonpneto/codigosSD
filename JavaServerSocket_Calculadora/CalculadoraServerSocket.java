import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Um serviço de calculadora implementado através de sockets. Ele é extremamente
 * simples e capaz de realizar apenas as quatro operações fundamentais sobre
 * valores double.
 * 
 * Para usá-lo, o cliente deve se conectar à porta 9090 no IP em um dos
 * endereços IP do servidor via socket, e conteúdo de sua requisição deve ser
 * operador, operando 1 e operando 2, separados por uma quebra de linha (\n), em
 * que o operador pode ser um dos seguintes valores: 1-Soma; 2-Subtração;
 * 3-Multiplicação; 4-Divisão.
 */
public class CalculadoraServerSocket {

	public static void main(String[] args) {
		// Criação de objetos que serão utilizados mais adiante
		ServerSocket welcomeSocket;
		DataOutputStream socketOutput;
		BufferedReader socketEntrada;
		Calculadora calc = new Calculadora();

		try {
			// Criação do socket servidor, no endereço local e na porta 9090
			welcomeSocket = new ServerSocket(9090);
			int i = 0; // número de clientes
			System.out.println("Servidor no ar");

			// O programa aguardará conexões eternamente...
			while (true) {

				/*
				 * Aguarda a próxima conexão de um cliente, bloqueando a execução. Quando houver
				 * uma conexão, aceitará os dados e a execução prosseguirá
				 */
				Socket connectionSocket = welcomeSocket.accept();

				/*
				 * incrementa o contador de conexões de clientes, pois se passou da chamada
				 * acima é porque chegou uma nova conexão. Em seguida, imprime uma informação de
				 * quantas conexões houve até então
				 */
				i++;
				System.out.println("Nova requisição. Total de requisições até o momento: " + i);

				// Interpretando dados do servidor
				/*
				 * Trata os dados recebidos pelo socket. Os dados (operação, operando1 e
				 * operando2) encontram-se separados por uma quebra de linha (\n), portanto é
				 * conveniente instanciar um BufferedReader a partir da InputStream do socket
				 * para, logo em seguida, utilizar o método readLine para obter cada um desses
				 * dados separadamente.
				 */
				socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				String operacao = socketEntrada.readLine();
				String oper1 = socketEntrada.readLine();
				String oper2 = socketEntrada.readLine();

				// O resultado da operação será armazenado em result
				String result = "";

				// Chamando a calculadora

				/*
				 * Para cada operação, há um método disponibilizados pelo serviço no objeto calc
				 * (instância da classe Calculadora). Tanto as operações como os operandos estão
				 * ainda como String, da forma como chegaram via socket, então é necessário
				 * convertê-los para os tipos corretos antes de chamar os métodos
				 * correspondentes. O resultado da operação será armazenado na variável result.
				 * 
				 * Operações: 1:Soma; 2:Subtração; 3:Multiplicação; 4:Divisão
				 * 
				 * Se por acaso tentar utilizar uma operação diferente de 1..4, o resultado será
				 * "ERRO:OP_INVALIDA".
				 */
				switch (Integer.parseInt(operacao)) {
				case 1:
					result = "" + calc.soma(Double.parseDouble(oper1), Double.parseDouble(oper2));
					break;
				case 2:
					result = "" + calc.subtrai(Double.parseDouble(oper1), Double.parseDouble(oper2));
					break;
				case 3:
					result = "" + calc.multiplica(Double.parseDouble(oper1), Double.parseDouble(oper2));
					break;
				case 4:
					result = "" + calc.divide(Double.parseDouble(oper1), Double.parseDouble(oper2));
					break;
				default:
					result = "ERRO:OP_INVALIDA";
				}

				// Enviando dados para o servidor
				/*
				 * O envio do resultado para o cliente será feito através de um OutputStream do
				 * socket. A stream enviada o resultado seguido de uma quebra de linha. O
				 * resultado será impresso na tela, para acompanhamento da execução.
				 */
				socketOutput = new DataOutputStream(connectionSocket.getOutputStream());
				socketOutput.writeBytes(result + '\n');
				System.out.println(result);
				/*
				 * O buffer será descarregado e os dados, se ainda não o foram, enviados ao
				 * socket cliente
				 */
				socketOutput.flush();
				/*
				 * Por fim, o outputStream é encerrado para devolver os recursos utilizados ao
				 * sistema.
				 */
				socketOutput.close();

				// Na próxima iteração, aguardará a próxima conexão...
			}
		} catch (IOException e) {
			/*
			 * Qualquer IOException que ocorrer cairá aqui. Nesse caso, o cliente não
			 * receberá nenhuma resposta.
			 */
			e.printStackTrace();
		}

	}

}
