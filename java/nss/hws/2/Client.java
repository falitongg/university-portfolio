package nss.cviceni2.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import nss.cviceni2.compute.DBRecord;
import nss.cviceni2.compute.ServerInterface;
import nss.cviceni2.server.CsvReader;

public class Client {

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		String host;
		int PORT;
		String conf_file;
		if (args.length != 3) {
			// <IP_adresa> <cislo_portu> <konf_soubor>
			host = "localhost"; //host = "127.0.0.1";
			PORT = 7777;
			conf_file = "./data/conf.csv"; // konfig. soubor
		} else {
			host = args[0];
			PORT = Integer.valueOf(args[1]);
			conf_file = args[2];
		}

		try {
			// vyhledani vzdaleneho objektu
			ServerInterface serveri;
			String name = "muj_server";
			Registry registry = LocateRegistry.getRegistry(host, PORT);

			 serveri = (ServerInterface) registry.lookup(name);

			 //TODO HERE continue.. :-)

			CsvReader reader = new CsvReader(conf_file, ';');
			while (reader.readRecord()){
				String prikaz = reader.get(0);
				try {
					switch (prikaz) {
						case "listdb" -> {
							String[] dbs = serveri.listDB();
							for (String db : dbs) {
								System.out.println(db);
							}
							break;
						}
						case "createdb" -> {
							String parametr = reader.get(1);
							serveri.createDB(parametr);
							System.out.println("DB '" + parametr + "' created.");
							break;
						}
						case "insert" -> {
							String db = reader.get(1);
							Integer key = Integer.parseInt(reader.get(2));
							String message = reader.get(3);
							System.out.println("INSERTING to " + db + ": " + key + ": " + message);
							serveri.insert(db, key, message);
							System.out.println("Inserted: " + db + ": " + key + ": " + message);
							break;
						}
						case "update" -> {
							String db = reader.get(1);
							Integer key = Integer.parseInt(reader.get(2));
							String message = reader.get(3);
							System.out.println("UPDATING " + db + ": " + key + ": " + message);
							serveri.update(db, key, message);
							System.out.println("Updated: " + db + ": " + key + ": " + message);
							break;
						}
						case "get" -> {
							String db = reader.get(1);
							Integer key = Integer.parseInt(reader.get(2));
							System.out.println("GETTING " + db + ": " + key);
							DBRecord record = serveri.get(db, key);
							System.out.println("RECORD: " + record.getKey() + ": " + record.getMessage());
							break;
						}
						case "geta" -> {
							String db = reader.get(1);
							Integer[] keys = IntStream.range(2, reader.getColumnCount())
									.mapToObj(i -> {
										try {
											return Integer.parseInt(reader.get(i));
										} catch (IOException e) {
											throw new RuntimeException(e);
										}
									})
									.toArray(n -> new Integer[n]);

							DBRecord[] records = serveri.getA(db, keys);
							for (DBRecord record : records) {
								System.out.println("RECORD: " + record.getKey() + ": " + record.getMessage());
							}
							break;
						}
						case "flush" -> {
							System.out.println("FLUSHING");
							serveri.flush();
							System.out.println("FLUSHED");
							break;
						}
						default -> {
							System.out.println("ERROR - command" + prikaz + "not implemented");
						}
					}
                }
				catch (Exception e) {
					System.out.println("ERROR - " + e.getMessage());
				}
			}

		} catch (Exception e) {
			System.err.println("Data exception: " + e.getMessage());
		}
	}
}
