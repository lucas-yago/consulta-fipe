package br.com.onealura.TabelaFipe.main;

import br.com.onealura.TabelaFipe.model.Vehicle;
import br.com.onealura.TabelaFipe.model.Data;
import br.com.onealura.TabelaFipe.model.VehicleModel;
import br.com.onealura.TabelaFipe.service.ApiClient;
import br.com.onealura.TabelaFipe.service.ApiDataMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final ApiClient apiClient = new ApiClient();
    private final ApiDataMapper dataMapper = new ApiDataMapper();
    private final String BASE_URL = "https://parallelum.com.br/fipe/api/v1";

    private String vehicleType;
    private String vehicleBrand;
    private String vehicleModel;

    public void displayMenu() {
        System.out.println("""
                ***** OPÇÕES *****
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar valores:""");


        vehicleType = scanner.nextLine().toLowerCase();

        if (vehicleType.toLowerCase().contains("car")) {
            vehicleType = "carros";
        } else if (vehicleType.toLowerCase().contains("mot")) {
            vehicleType = "motos";
        } else if (vehicleType.toLowerCase().contains("cam")) {
            vehicleType = "caminhoes";
        } else {
            System.out.println("O tipo de veículo inserido é desconhecido.");
        }


        var jsonBrands = apiClient.fetchData("%s/%s/marcas".formatted(BASE_URL, vehicleType));
        List<Data> brands = dataMapper.convertFromJson(jsonBrands, new TypeReference<List<Data>>() {});

        brands.stream()
                .sorted(Comparator.comparing(Data::code))
                .forEach(b -> System.out.println("Código: %s   Nome: %s".formatted(b.code(), b.name())));


        System.out.println("\nInforme o código da marca desejada: ");
        vehicleBrand = scanner.nextLine();

        var jsonModels = apiClient.fetchData("%s/%s/marcas/%s/modelos".formatted(BASE_URL, vehicleType, vehicleBrand));
        VehicleModel models = dataMapper.convertFromJson(jsonModels, VehicleModel.class);
        models.models().stream()
                .sorted(Comparator.comparing(Data::code))
                .forEach(m -> System.out.println("Code: %s   Name: %s".formatted(m.code(), m.name())));


        System.out.println("\nDigite um trechodo nome do carro a ser buscado: ");
        var vehicleName = scanner.nextLine();

        List<Data> filterModels = models.models().stream()
                .filter(m -> m.name().toLowerCase().contains(vehicleName))
                .collect(Collectors.toList());

        filterModels.forEach(m -> System.out.println("Code: %s   Name: %s".formatted(m.code(), m.name())));


        System.out.println("\nDigite o código do modelo para buscar a avaliação");
        vehicleModel = scanner.nextLine();

        var jsonYears = apiClient.fetchData("%s/%s/marcas/%s/modelos/%s/anos"
                .formatted(BASE_URL, vehicleType, vehicleBrand, vehicleModel));
        List<Data> years = dataMapper.convertFromJson(jsonYears, new TypeReference<List<Data>>() {});

        List<Vehicle> fipeVehicleModel = new ArrayList<>();

        for(Data year : years ){
            var jsonfipe = apiClient.fetchData("%s/%s/marcas/%s/modelos/%s/anos/%s"
                    .formatted(BASE_URL, vehicleType, vehicleBrand, vehicleModel, year.code()));
            Vehicle vehicleFipe = dataMapper.convertFromJson(jsonfipe, Vehicle.class);
            fipeVehicleModel.add(vehicleFipe);
        }

        fipeVehicleModel.stream().forEach(System.out::println);
    }
}
