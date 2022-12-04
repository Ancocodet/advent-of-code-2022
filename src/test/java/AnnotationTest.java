import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Slf4j
@Order(2)
public class AnnotationTest {

    private final int DAYS = 4;

    @Test
    public void testAnnotations(){
        Reflections reflections = new Reflections("de.ancozockt.advent.days");

        assert reflections.getTypesAnnotatedWith(AInputData.class).size() == DAYS;
    }

    @Test
    public void testResponse(){
        Reflections reflections = new Reflections("de.ancozockt.advent.days");

        reflections.getTypesAnnotatedWith(AInputData.class).forEach(aClass -> {
            AInputData inputData = aClass.getAnnotation(AInputData.class);
            IAdventDay adventDay = (IAdventDay) createNewInstanceOfClass(aClass);

            Long[] outputs = null;
            try {
                outputs = readOutputs(inputData.day());
            }catch (Exception ignored){ }

            assert outputs != null;

            String part1 = adventDay.part1(readFromFile("input/day" + inputData.day() + "-input"));
            try {
                log.info(inputData.day() + " Part 1");
                assert Long.parseLong(part1) == outputs[0];
            }catch (NumberFormatException ignored){}

            String part2 = adventDay.part2(readFromFile("input/day" + inputData.day() + "-input"));
            try {
                log.info(inputData.day() + " Part 2");
                assert Long.parseLong(part2) == outputs[1];
            }catch (NumberFormatException ignored){}
        });
    }

    private Long[] readOutputs(int day){
        ArrayList<Long> outputs = new ArrayList<>();

        BufferedReader reader = readFromFile("output/day" + day + "-output");
        String line;
        try {
            while ((line = reader.readLine()) != null){
                outputs.add(Long.parseLong(line));
            }
        }catch (IOException ignored) { }

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
