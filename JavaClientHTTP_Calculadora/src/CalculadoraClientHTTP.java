import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//Cliente HTTP para exemplificar o modelo requisi��o-resposta
public class CalculadoraClientHTTP {
	
	//M�todo que faz uma requisi��o para o servidor PHP
	//Recebe os operadores e a opera��o desejada para passar como par�metro
	//Imprime o resultado da opera��o que vem como resposta do servidor PHP
	public static void req(int op1, int op2, int op) {
		String result="";
	    try {
	       //Cria um objeto URL a partir de uma string (endere�o do servidor PHP)
	       URL url = new URL("https://double-nirvana-273602.appspot.com/?hl=pt-BR");
	       
	       //Cria uma inst�ncia de uma conex�o que representa o objeto remoto referenciado pela URL
	       HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	        
	        //Seta o m�ximo de tempo (em ms) de espera pela resposta do servidor, ap�s isso lan�a SocketTimeoutException
	        conn.setReadTimeout(10000);
	        //Seta o m�ximo de tempo (em ms) de espera pelo estabelecimento da conex�o com o servidor, ap�s isso lan�a SocketTimeoutException
	        conn.setConnectTimeout(15000);
	        //Seta o m�todo para POST
	        conn.setRequestMethod("POST");
	        //Seta que a conex�o ser� usada tanto para input quanto output
	        conn.setDoInput(true);
	        conn.setDoOutput(true);

	        //ENVIO DOS PARAMETROS
	        //Cria um objeto OutputStream para a conex�o
	        OutputStream os = conn.getOutputStream();
	        //Cria um objeto BufferedWriter a partir do output da conex�o para escrever os par�metros da requisi��o
	        //Criado passando o outputstream da conex�o e o charset suportado
	        BufferedWriter writer = new BufferedWriter(
	                new OutputStreamWriter(os, "UTF-8"));
	        //Escreve os par�metros no objeto writer. No caso, o operador 1, o operador 2 e a opera��o da requisi��o, conforme implementado e esperado pelo servidor remoto PHP
	        writer.write("oper1="+op1+"&oper2="+op2+"&operacao="+op); //1-somar 2-subtrair 3-multiplicar 4-dividir
	        //Limpa o objeto writer para depois fech�-lo
	        writer.flush();
	        writer.close();
	        //Fecha o outputstream da conex�o
	        os.close();

	        //Retorna o status de resposta da requisi��o
	        int responseCode=conn.getResponseCode();
	        //Se a resposta da requisi��o foi sucesso
	        if (responseCode == HttpsURLConnection.HTTP_OK) {

	            //RECBIMENTO DOS PARAMETROS

	        	//Cria um objeto para ler a resposta dada pelo servidor, a partir do inputstream da conex�o e indicando o charset suportado
	            BufferedReader br = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream(), "utf-8"));
	            StringBuilder response = new StringBuilder();
	            String responseLine = null;
	            //L� resposta e adiciona no objeto response
	            //O m�todo readLine() l� uma linha at� encontrar o caractere '\n'
	            //Quando chegar ao final e a linha estiver nula, j� foram lidas todas as linhas da resposta
	            while ((responseLine = br.readLine()) != null) {
	                response.append(responseLine.trim());
	            }
	            //Converte o valor do StringBuilder em uma string para ser impressa
	            result = response.toString();
	            //Imprime o resultado da opera��o que foi enviado pelo servidor remoto
	            System.out.println("Resposta do Servidor PHP="+result);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		//Chama o m�todo de requisi��o/resposta HTTP para o servidor PHP, passando os operadores e a opera��o desejada
		//1 - Soma; 2 - Subtra��o; 3 - Multiplica��o; 4 - Divis�o
		CalculadoraClientHTTP.req(15, 15, 1);
		CalculadoraClientHTTP.req(15, 15, 2);
		CalculadoraClientHTTP.req(15, 15, 3);
		CalculadoraClientHTTP.req(15, 15, 4);
	}
}
