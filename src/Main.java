import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HttpClient client = HttpClient.newHttpClient();
        List<Cep> listaDeCeps = new ArrayList<>();
        String cep = "";
        while (!cep.equalsIgnoreCase("sair")) {
            System.out.println("Digite um cep: ");
            cep = scanner.nextLine();
            if (cep.equalsIgnoreCase("sair")) {
                break;
            }
            String url = "https://viacep.com.br/ws/" + cep + "/json/";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            Cep cep1;
            cep1 = gson.fromJson(json, Cep.class);
            System.out.println(cep1);
            listaDeCeps.add(cep1);
        }
        FileWriter fileWriter = new FileWriter("listaDeCeps.json");
        fileWriter.write(gson.toJson(listaDeCeps));
        fileWriter.close();
    }
}
