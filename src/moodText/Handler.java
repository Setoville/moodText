package moodText;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class Handler implements HttpHandler {
	
	public void start(){
		
	    HttpServer server=null;
		try {
			server = HttpServer.create(new InetSocketAddress(8000), 0);
			System.out.println("server live..");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    server.createContext("/", new Handler());
	    server.setExecutor(null); // creates a default executor
	    server.start();
	    
	   // return null;
	}

	public void handle(HttpExchange t) throws IOException {
		
		String query=t.getRequestURI().getQuery().toString();
		//System.out.println(query);
		MOOD response = MoodText.getEmotion(MoodText.getWordsFromWeb(query));
		
		
		
		t.sendResponseHeaders(200, response.toString().length());
		OutputStream os = t.getResponseBody();
		
		os.write(response.toString().getBytes());
		
		

		os.close();
		
		
		 
		}
}