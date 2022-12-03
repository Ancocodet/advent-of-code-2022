import de.ancozockt.advent.interfaces.ADay;
import de.ancozockt.advent.interfaces.AdventDay;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Slf4j
public class AnnotationTest {

    private final int DAYS = 3;

    @Test
    public void testAnnotations(){
        Reflections reflections = new Reflections("de.ancozockt.advent.days");

        assert reflections.getTypesAnnotatedWith(ADay.class).size() == DAYS;
    }

    @Test
    public void testResponse(){
        Reflections reflections = new Reflections("de.ancozockt.advent.days");

        reflections.getTypesAnnotatedWith(ADay.class).forEach(aClass -> {
            ADay aDay = aClass.getAnnotation(ADay.class);
            AdventDay adventDay = (AdventDay) createNewInstanceOfClass(aClass);

            Long[] outputs = null;
            try {
                outputs = readOutputs(aDay.day());
            }catch (Exception ignored){ }

            assert outputs != null;

            String part1 = adventDay.part1(readFromFile("input/" + aDay.day() + "-input"));
            try {
                log.info(aDay.day() + " Part 1");
                assert Long.parseLong(part1) == outputs[0];
            }catch (NumberFormatException ignored){}

            String part2 = adventDay.part2(readFromFile("input/" + aDay.day() + "-input"));
            try {
                log.info(aDay.day() + " Part 2");
                assert Long.parseLong(part2) == outputs[1];
            }catch (NumberFormatException ignored){}
        });
    }

    private Long[] readOutputs(String day){
        ArrayList<Long> outputs = new ArrayList<>();

        BufferedReader reader = readFromFile("output/" + day + "-output");
        String line;
        try {
            while ((line = reader.readLine()) != null){
                outputs.add(Long.parseLong(line));
            }
        }catch (IOException ignored) {}

        return outputs.toArray(new Long[]{});
    }

    private BufferedReader readFromFile(String fileName){
        InputStream ioStream = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }

        return new BufferedReader(new InputStreamReader(ioStream));
    }

    private static <T> T createNewInstanceOfClass(Class<T> someClass) {
        try {
            return someClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
