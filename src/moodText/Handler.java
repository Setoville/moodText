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
		
		String query=t.getRequestURI().getQuery();
		
		MoodText.start(query);

		String response = "hello";
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		
		//if (query.contains("butts")){
		os.write(response.getBytes());
		
		

		os.close();
		
		
		 
		}
}