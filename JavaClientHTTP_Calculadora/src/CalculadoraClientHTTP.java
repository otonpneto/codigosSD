import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//Cliente HTTP para exemplificar o modelo requisição-resposta
public class CalculadoraClientHTTP {
	
	//Método que faz uma requisição para o servidor PHP
	//Recebe os operadores e a operação desejada para passar como parâmetro
	//Imprime o resultado da operação que vem como resposta do servidor PHP
	public static void req(int op1, int op2, int op) {
		String result="";
	    try {
	       //Cria um objeto URL a partir de uma string (endereço do servidor PHP)
	       URL url = new URL("https://double-nirvana-273602.appspot.com/?hl=pt-BR");
	       
	       //Cria uma instância de uma conexão que representa o objeto remoto referenciado pela URL
	       HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	        
	        //Seta o máximo de tempo (em ms) de espera pela resposta do servidor, após isso lança SocketTimeoutException
	        conn.setReadTimeout(10000);
	        //Seta o máximo de tempo (em ms) de espera pelo estabelecimento da conexão com o servidor, após isso lança SocketTimeoutException
	        conn.setConnectTimeout(15000);
	        //Seta o método para POST
	        conn.setRequestMethod("POST");
	        //Seta que a conexão será usada tanto para input quanto output
	        conn.setDoInput(true);
	        conn.setDoOutput(true);

	        //ENVIO DOS PARAMETROS
	        //Cria um objeto OutputStream para a conexão
	        OutputStream os = conn.getOutputStream();
	        //Cria um objeto BufferedWriter a partir do output da conexão para escrever os parâmetros da requisição
	        //Criado passando o outputstream da conexão e o charset suportado
	        BufferedWriter writer = new BufferedWriter(
	                new OutputStreamWriter(os, "UTF-8"));
	        //Escreve os parâmetros no objeto writer. No caso, o operador 1, o operador 2 e a operação da requisição, conforme implementado e esperado pelo servidor remoto PHP
	        writer.write("oper1="+op1+"&oper2="+op2+"&operacao="+op); //1-somar 2-subtrair 3-multiplicar 4-dividir
	        //Limpa o objeto writer para depois fechá-lo
	        writer.flush();
	        writer.close();
	        //Fecha o outputstream da conexão
	        os.close();

	        //Retorna o status de resposta da requisição
	        int responseCode=conn.getResponseCode();
	        //Se a resposta da requisição foi sucesso
	        if (responseCode == HttpsURLConnection.HTTP_OK) {

	            //RECBIMENTO DOS PARAMETROS

	        	//Cria um objeto para ler a resposta dada pelo servidor, a partir do inputstream da conexão e indicando o charset suportado
	            BufferedReader br = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream(), "utf-8"));
	            StringBuilder response = new StringBuilder();
	            String responseLine = null;
	            //Lê resposta e adiciona no objeto response
	            //O método readLine() lê uma linha até encontrar o caractere '\n'
	            //Quando chegar ao final e a linha estiver nula, já foram lidas todas as linhas da resposta
	            while ((responseLine = br.readLine()) != null) {
	                response.append(responseLine.trim());
	            }
	            //Converte o valor do StringBuilder em uma string para ser impressa
	            result = response.toString();
	            //Imprime o resultado da operação que foi enviado pelo servidor remoto
	            System.out.println("Resposta do Servidor PHP="+result);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		//Chama o método de requisição/resposta HTTP para o servidor PHP, passando os operadores e a operação desejada
		//1 - Soma; 2 - Subtração; 3 - Multiplicação; 4 - Divisão
		CalculadoraClientHTTP.req(15, 15, 1);
		CalculadoraClientHTTP.req(15, 15, 2);
		CalculadoraClientHTTP.req(15, 15, 3);
		CalculadoraClientHTTP.req(15, 15, 4);
	}
}
