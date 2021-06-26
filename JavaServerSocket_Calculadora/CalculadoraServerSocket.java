import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Um servi�o de calculadora implementado atrav�s de sockets. Ele � extremamente
 * simples e capaz de realizar apenas as quatro opera��es fundamentais sobre
 * valores double.
 * 
 * Para us�-lo, o cliente deve se conectar � porta 9090 no IP em um dos
 * endere�os IP do servidor via socket, e conte�do de sua requisi��o deve ser
 * operador, operando 1 e operando 2, separados por uma quebra de linha (\n), em
 * que o operador pode ser um dos seguintes valores: 1-Soma; 2-Subtra��o;
 * 3-Multiplica��o; 4-Divis�o.
 */
public class CalculadoraServerSocket {

	public static void main(String[] args) {
		// Cria��o de objetos que ser�o utilizados mais adiante
		ServerSocket welcomeSocket;
		DataOutputStream socketOutput;
		BufferedReader socketEntrada;
		Calculadora calc = new Calculadora();

		try {
			// Cria��o do socket servidor, no endere�o local e na porta 9090
			welcomeSocket = new ServerSocket(9090);
			int i = 0; // n�mero de clientes
			System.out.println("Servidor no ar");

			// O programa aguardar� conex�es eternamente...
			while (true) {

				/*
				 * Aguarda a pr�xima conex�o de um cliente, bloqueando a execu��o. Quando houver
				 * uma conex�o, aceitar� os dados e a execu��o prosseguir�
				 */
				Socket connectionSocket = welcomeSocket.accept();

				/*
				 * incrementa o contador de conex�es de clientes, pois se passou da chamada
				 * acima � porque chegou uma nova conex�o. Em seguida, imprime uma informa��o de
				 * quantas conex�es houve at� ent�o
				 */
				i++;
				System.out.println("Nova requisi��o. Total de requisi��es at� o momento: " + i);

				// Interpretando dados do servidor
				/*
				 * Trata os dados recebidos pelo socket. Os dados (opera��o, operando1 e
				 * operando2) encontram-se separados por uma quebra de linha (\n), portanto �
				 * conveniente instanciar um BufferedReader a partir da InputStream do socket
				 * para, logo em seguida, utilizar o m�todo readLine para obter cada um desses
				 * dados separadamente.
				 */
				socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				String operacao = socketEntrada.readLine();
				String oper1 = socketEntrada.readLine();
				String oper2 = socketEntrada.readLine();

				// O resultado da opera��o ser� armazenado em result
				String result = "";

				// Chamando a calculadora

				/*
				 * Para cada opera��o, h� um m�todo disponibilizados pelo servi�o no objeto calc
				 * (inst�ncia da classe Calculadora). Tanto as opera��es como os operandos est�o
				 * ainda como String, da forma como chegaram via socket, ent�o � necess�rio
				 * convert�-los para os tipos corretos antes de chamar os m�todos
				 * correspondentes. O resultado da opera��o ser� armazenado na vari�vel result.
				 * 
				 * Opera��es: 1:Soma; 2:Subtra��o; 3:Multiplica��o; 4:Divis�o
				 * 
				 * Se por acaso tentar utilizar uma opera��o diferente de 1..4, o resultado ser�
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
				 * O envio do resultado para o cliente ser� feito atrav�s de um OutputStream do
				 * socket. A stream enviada o resultado seguido de uma quebra de linha. O
				 * resultado ser� impresso na tela, para acompanhamento da execu��o.
				 */
				socketOutput = new DataOutputStream(connectionSocket.getOutputStream());
				socketOutput.writeBytes(result + '\n');
				System.out.println(result);
				/*
				 * O buffer ser� descarregado e os dados, se ainda n�o o foram, enviados ao
				 * socket cliente
				 */
				socketOutput.flush();
				/*
				 * Por fim, o outputStream � encerrado para devolver os recursos utilizados ao
				 * sistema.
				 */
				socketOutput.close();

				// Na pr�xima itera��o, aguardar� a pr�xima conex�o...
			}
		} catch (IOException e) {
			/*
			 * Qualquer IOException que ocorrer cair� aqui. Nesse caso, o cliente n�o
			 * receber� nenhuma resposta.
			 */
			e.printStackTrace();
		}

	}

}
