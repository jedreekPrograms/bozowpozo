import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    public List<City> load(Path path) throws IOException {
        List<City> cities = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null &&
                    !line.equals("NODE_COORD_SECTION")) {}

            while ((line = reader.readLine()) != null &&
                    !line.equals("EOF")) {

                String[] parts = line.trim().split("\\s+");

                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);

                cities.add(new City(x, y));
            }
        }

        return cities;
    }
}