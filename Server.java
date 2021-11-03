
/*
 * This is absolutely NOT FINISHED....far from it. Development has only recently begun. Working on this in the very 
 * short free time I have.
 * 
 * Need to actually set up the network functionality:
 * To do:
 * 	Basic socket connection with Client
 *  Send a basic MD5 hash to the client, get the password back...
 *  go from here
 * */
public class Server {
	private final int port;
	private final String ip;

	public Server(String ip, int port){
		this.port = port;
		this.ip = ip;
	}

	public int getPort() {
		return this.port;
	}

	public String getIp() {
		return this.ip;
	}

}