import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Cliente para o serviço de calculadora disponibilizado via sockets.
 */
public class CalculadoraClientSocket {

	public static void main(String[] args) {

		// Os dois operandos
		double oper1 = 10, oper2 = 20;
		// O indicador da operação
		int operacao = 1; // 1-somar 2-subtrair 3-dividir 4-multiplicar
		// Para armazenar o resultado da operação, recebido via socket
		String result = "";
		try {

			// Conexão com o Servidor
			/*
			 * Vamos criar uma conexão via socket na porta 9090 do servidor no endereço
			 * 127.0.0.1 (localhost)
			 */
			Socket clientSocket = new Socket("127.0.0.1", 9090);

			/*
			 * O envio dos dados para o servidor será feito através de um outputStream do
			 * socket. Para isso, será utilizada uma instância da classe concreta
			 * DataOutputStream
			 */
			DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());

			// Enviando os dados
			/*
			 * A stream enviada será composta por operador, operando 1 e operando 2, nesta
			 * ordem. Após cada um deles, será incluída uma quebra de linha ("\n"), afim de
			 * delimitar os dados.
			 */
			socketSaidaServer.writeBytes(operacao + "\n");
			socketSaidaServer.writeBytes(oper1 + "\n");
			socketSaidaServer.writeBytes(oper2 + "\n");

			/*
			 * descarrega o buffer na stream, se ainda houver, que será então encaminhado ao
			 * servidor.
			 */
			socketSaidaServer.flush();

			// Recebendo a resposta
			/*
			 * O recebimento do resultado do servidor será feito através de um InputStream
			 * do socket. A stream recebida é formada pelo resultado seguido de uma quebra
			 * de linha. Como o resultado encontra-se delimitado por quebra de linha ("\n"),
			 * é conveniente instanciar um BufferedReader a partir da InputStream do socket
			 * para, logo em seguida, utilizar o método readLine para obtê-lo.
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
