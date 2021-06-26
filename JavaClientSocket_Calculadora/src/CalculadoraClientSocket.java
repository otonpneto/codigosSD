import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Cliente para o servi�o de calculadora disponibilizado via sockets.
 */
public class CalculadoraClientSocket {

	public static void main(String[] args) {

		// Os dois operandos
		double oper1 = 10, oper2 = 20;
		// O indicador da opera��o
		int operacao = 1; // 1-somar 2-subtrair 3-dividir 4-multiplicar
		// Para armazenar o resultado da opera��o, recebido via socket
		String result = "";
		try {

			// Conex�o com o Servidor
			/*
			 * Vamos criar uma conex�o via socket na porta 9090 do servidor no endere�o
			 * 127.0.0.1 (localhost)
			 */
			Socket clientSocket = new Socket("127.0.0.1", 9090);

			/*
			 * O envio dos dados para o servidor ser� feito atrav�s de um outputStream do
			 * socket. Para isso, ser� utilizada uma inst�ncia da classe concreta
			 * DataOutputStream
			 */
			DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());

			// Enviando os dados
			/*
			 * A stream enviada ser� composta por operador, operando 1 e operando 2, nesta
			 * ordem. Ap�s cada um deles, ser� inclu�da uma quebra de linha ("\n"), afim de
			 * delimitar os dados.
			 */
			socketSaidaServer.writeBytes(operacao + "\n");
			socketSaidaServer.writeBytes(oper1 + "\n");
			socketSaidaServer.writeBytes(oper2 + "\n");

			/*
			 * descarrega o buffer na stream, se ainda houver, que ser� ent�o encaminhado ao
			 * servidor.
			 */
			socketSaidaServer.flush();

			// Recebendo a resposta
			/*
			 * O recebimento do resultado do servidor ser� feito atrav�s de um InputStream
			 * do socket. A stream recebida � formada pelo resultado seguido de uma quebra
			 * de linha. Como o resultado encontra-se delimitado por quebra de linha ("\n"),
			 * � conveniente instanciar um BufferedReader a partir da InputStream do socket
			 * para, logo em seguida, utilizar o m�todo readLine para obt�-lo.
			 */
			BufferedReader messageFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			result = messageFromServer.readLine();

			// Imprime o resultado na tela
			System.out.println("resultado=" + result);

			// Fecha o socket.
			clientSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
